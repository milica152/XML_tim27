package ftn.project.xml.service;

import ftn.project.xml.dto.MetadataDTO;
import ftn.project.xml.model.*;
import ftn.project.xml.repository.ScientificPaperRepository;
import ftn.project.xml.repository.UserRepository;
import ftn.project.xml.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.apache.commons.io.IOUtils;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ScientificPaperService {
    private static String schemaPath = "backend\\src\\main\\resources\\static\\schemas\\scientificPaper.xsd";

    Logger logger = LoggerFactory.getLogger(ScientificPaperService.class);

    @Autowired
    public DOMParser domParser;

    @Autowired
    private ScientificPaperRepository scientificPaperRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessProcessService businessProcessService;

    @Autowired
    private MetadataExtractor metadataExtractor;

    @Autowired
    private Convert convert;

    @Value("${scientificPaper.XSLPath}")
    private String xslPath;

    @Value("${scientificPaper.XHTMLPath}")
    private String htmlPath;

    public String save(AuthenticationUtilities.ConnectionProperties conn, String xmlRes, boolean revision, String titleOld) throws Exception {
        try{
            DOMParser parser = new DOMParser();
        Document d = parser.buildDocument(xmlRes, schemaPath);


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        NodeList nl = d.getElementsByTagName("title");
        NodeList n2 = d.getElementsByTagName("status");
        NodeList n3 = d.getElementsByTagName("datePublished");

        String title = nl.item(0).getTextContent();
        title = title.replaceAll("\\s","");
        if(revision){
           scientificPaperRepository.delete(conn, titleOld);
        }
        if(scientificPaperRepository.getByTitle(conn, title)!=""){
            return "Paper with the title " + title + " already exists!";
        }
        // pokreni bussiness process

        // popuni sp metapodacima
        NodeList metadata = d.getElementsByTagName("metadata");

        // list of authors and keywords
        NodeList authors = d.getElementsByTagName("contact");
        NodeList keywords = d.getElementsByTagName("keywords");
        ArrayList<TUser> usersAuthors = new ArrayList<>();
        for(int i=0; i<authors.getLength();i++){
            String email = authors.item(i).getTextContent();
            TUser user1 = userRepository.getUserByEmail(conn, email);
            usersAuthors.add(user1);

        }

        if (usersAuthors.contains(null)) {
            return "Some of authors don't exist!";
        }
        User logged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean found = false;
        for(TUser u: usersAuthors){
            if(u.getEmail().equals(logged.getEmail())){
                found = true;
                break;
            }
        }
        if(!found){
            return "You must be one of the authors of the paper!";
        }

        for(TUser u: usersAuthors){
            if(u.getMyPapers()==null){
                u.setMyPapers(new TUser.MyPapers());
            }
            u.getMyPapers().getMyScientificPaperID().add(title);
            userRepository.save(conn, u);
        }
        // date published

        n3.item(0).setTextContent(df.format(new Date()));
        // dodati rdf podatak na property

        // status
//        Element status = d.createElement("status");
//        status.setAttribute("property", "spStatus");      // dodati rdf podatak na property

        if(!revision){
            n2.item(0).setTextContent("submitted");
        }else{
            n2.item(0).setTextContent("revised");
        }
        // about
        Element about = d.createElement("about");
        d.getDocumentElement().setAttribute("about", title);


        ByteArrayOutputStream metadataStream = new ByteArrayOutputStream();
        String newSciPap = domParser.DOMToXML(d);

        metadataExtractor.extractMetadata(new ByteArrayInputStream(newSciPap.getBytes()), metadataStream);
        String extractedMetadata = new String(metadataStream.toByteArray());

        // saving to RDF store
        scientificPaperRepository.saveMetadata(extractedMetadata);
        scientificPaperRepository.save(conn, title, newSciPap);

        // start a business process
        businessProcessService.createBusinessProcess(title);

        logger.info("New Scientific paper published under the title: " + title);

        return "Scientific Paper published!";

    }catch (Exception e){
        logger.warn("Invalid document type! Must be ScientificPaper. Or the paths are wrong.");
    }
        return "Error saving scientific paper!";
    }

    public String getByTitle(AuthenticationUtilities.ConnectionProperties conn, String s) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return scientificPaperRepository.getByTitle(conn, s);
    }

    public String getByTitleNoMetadata(AuthenticationUtilities.ConnectionProperties conn, String title) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, SAXException, TransformerException, ParserConfigurationException, IOException {
        String result = scientificPaperRepository.getByTitle(conn, title);
        result = scientificPaperRepository.removeMetadata(result);
        return result;
    }

    public List<String> search(AuthenticationUtilities.ConnectionProperties loadProperties, String author, String text) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        if(author.equals("my")){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return scientificPaperRepository.search(loadProperties,  user.getEmail(),  text);
        }else{
            return scientificPaperRepository.search(loadProperties,  null,  text);
        }
    }

    public List<String> findMyPapers(AuthenticationUtilities.ConnectionProperties loadProperties) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return scientificPaperRepository.getMyPapers(loadProperties, user.getEmail());
    }

    public String delete(String title, AuthenticationUtilities.ConnectionProperties loadProperties) throws Exception {
        return scientificPaperRepository.delete(loadProperties, title);
    }

    public List<MetadataDTO> getMetadata(RDFAuthenticationUtilities.RDFConnectionProperties properties, String title) {
        return scientificPaperRepository.getMetadata(properties, title);
    }


    public String transformToHTML(String xml) throws TransformerException, IOException {
        //TODO: dodaj proveru koji tip korisnika zeli da uradi transformaciju (da se ukloni autor ako treba itd)

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslStream = new StreamSource(new File(xslPath));
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer(xslStream);
        } catch (TransformerConfigurationException e) {
            System.out.println("Error while creating XSLT transformer object.");
        }

        int number = Objects.requireNonNull(new File(htmlPath).list()).length;
        String outputFile = htmlPath + "scientificPaperHTML" + number +".html";

        StreamSource in = new StreamSource(new StringReader(xml));
        StreamResult out = new StreamResult(new File(outputFile));
        assert transformer != null;
        transformer.transform(in, out);
        BufferedReader br = new BufferedReader(new FileReader(outputFile));
        String html = IOUtils.toString(br);
        return html;

    }

    public String exportMetadataToRDF(AuthenticationUtilities.ConnectionProperties loadProperties   ,  String title, String filePath) throws IOException {
        BufferedWriter out = null;

        File f = new File(filePath);
        if(f.isFile()){
            return "error";
        }
        try {
            FileWriter fstream = new FileWriter(filePath + "/" + title + ".txt", false); //true tells to append data.
            out = new BufferedWriter(fstream);
            ByteArrayOutputStream metadataStream = new ByteArrayOutputStream();
            String sp = getByTitle(loadProperties, title);
            //System.out.println(sp);
            metadataExtractor.extractMetadata(new ByteArrayInputStream(sp.getBytes()), metadataStream);
            String extractedMetadata = new String(metadataStream.toByteArray());
            //System.out.println(extractedMetadata);
            out.write(extractedMetadata);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (XMLDBException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                out.close();
            }
        }
        return "ok";
    }

    public String exportMetadataToJSON(RDFAuthenticationUtilities.RDFConnectionProperties loadProperties, String title, String filePath) throws IOException {
        List<MetadataDTO> metadataDTOS = getMetadata(loadProperties, title);
        String result = convert.metadataToJSONFormat(metadataDTOS);

        BufferedWriter out = null;

        File f = new File(filePath);
        if(f.isFile()){
            return "error";
        }
        try {
            FileWriter fstream = new FileWriter(filePath + "/" + title + ".txt", false); //true tells to append data.
            out = new BufferedWriter(fstream);
            out.write(result);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if(out != null) {
                out.close();
            }
        }
        return "ok";
    }

    public String withdraw( AuthenticationUtilities.ConnectionProperties loadXMLProperties, String title) throws Exception {
        String xmlRes = scientificPaperRepository.getByTitle(loadXMLProperties, title);
        Document d = domParser.buildDocument(xmlRes, schemaPath);

        Node oldStatus = d.getElementsByTagName("status").item(0);

        if(!oldStatus.getTextContent().equalsIgnoreCase("submitted")){
            return "error: scientific paper must be submitted to be withdrawn";
        }
        scientificPaperRepository.deleteMetadata(xmlRes);
        oldStatus.setTextContent("withdrawn");

        xmlRes = domParser.DOMToXML(d);
        //System.out.println(xmlRes);

        ByteArrayOutputStream metadataStream = new ByteArrayOutputStream();
        metadataExtractor.extractMetadata(new ByteArrayInputStream(xmlRes.getBytes()), metadataStream);
        String extractedMetadata = new String(metadataStream.toByteArray());
        scientificPaperRepository.save(loadXMLProperties, title, xmlRes);
        scientificPaperRepository.saveMetadata(extractedMetadata);
        BusinessProcess businessProcess = businessProcessService.findByScientificPaperTitle(title);
        businessProcess.setStatus(TStatusS.WITHDRAWN);
        businessProcessService.save(businessProcess);
        return "ok";
    }

    public String accept(AuthenticationUtilities.ConnectionProperties loadXMLProperties, String title) throws Exception {
        String xmlRes = scientificPaperRepository.getByTitle(loadXMLProperties, title);
        Document d = domParser.buildDocument(xmlRes, schemaPath);

        Node oldStatus = d.getElementsByTagName("status").item(0);

        scientificPaperRepository.deleteMetadata(xmlRes);
        oldStatus.setTextContent("published");

        xmlRes = domParser.DOMToXML(d);

        ByteArrayOutputStream metadataStream = new ByteArrayOutputStream();
        metadataExtractor.extractMetadata(new ByteArrayInputStream(xmlRes.getBytes()), metadataStream);
        String extractedMetadata = new String(metadataStream.toByteArray());
        scientificPaperRepository.save(loadXMLProperties, title, xmlRes);
        scientificPaperRepository.saveMetadata(extractedMetadata);
        BusinessProcess businessProcess = businessProcessService.findByScientificPaperTitle(title);
        businessProcess.setStatus(TStatusS.PUBLISHED);
        businessProcessService.save(businessProcess);
        return "ok";
    }

    public String reject(AuthenticationUtilities.ConnectionProperties loadXMLProperties, String title) throws Exception {
        String xmlRes = scientificPaperRepository.getByTitle(loadXMLProperties, title);
        Document d = domParser.buildDocument(xmlRes, schemaPath);

        Node oldStatus = d.getElementsByTagName("status").item(0);

        scientificPaperRepository.deleteMetadata(xmlRes);
        oldStatus.setTextContent("rejected");

        xmlRes = domParser.DOMToXML(d);

        ByteArrayOutputStream metadataStream = new ByteArrayOutputStream();
        metadataExtractor.extractMetadata(new ByteArrayInputStream(xmlRes.getBytes()), metadataStream);
        String extractedMetadata = new String(metadataStream.toByteArray());
        scientificPaperRepository.save(loadXMLProperties, title, xmlRes);
        scientificPaperRepository.saveMetadata(extractedMetadata);
        BusinessProcess businessProcess = businessProcessService.findByScientificPaperTitle(title);
        businessProcess.setStatus(TStatusS.REJECTED);
        businessProcessService.save(businessProcess);
        return "ok";
    }

    public String requestRevision(AuthenticationUtilities.ConnectionProperties loadXMLProperties, String title) throws Exception {
        String xmlRes = scientificPaperRepository.getByTitle(loadXMLProperties, title);
        Document d = domParser.buildDocument(xmlRes, schemaPath);

        Node oldStatus = d.getElementsByTagName("status").item(0);

        scientificPaperRepository.deleteMetadata(xmlRes);
        oldStatus.setTextContent("on_revision");

        xmlRes = domParser.DOMToXML(d);

        ByteArrayOutputStream metadataStream = new ByteArrayOutputStream();
        metadataExtractor.extractMetadata(new ByteArrayInputStream(xmlRes.getBytes()), metadataStream);
        String extractedMetadata = new String(metadataStream.toByteArray());
        scientificPaperRepository.save(loadXMLProperties, title, xmlRes);
        scientificPaperRepository.saveMetadata(extractedMetadata);
        BusinessProcess businessProcess = businessProcessService.findByScientificPaperTitle(title);
        businessProcess.setStatus(TStatusS.ON_REVISION);
        businessProcessService.save(businessProcess);
        return "ok";
    }

    public String getStatus(AuthenticationUtilities.ConnectionProperties conn, String title) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException, IOException, SAXException, ParserConfigurationException {
        String doc = getByTitle(conn, title);
        Document d = domParser.buildDocument(doc, schemaPath);
        NodeList statusList = d.getElementsByTagName("status");
        return statusList.item(0).getTextContent();
    }

    public String getByTitleWithoutAuthors(AuthenticationUtilities.ConnectionProperties loadProperties, String title) throws SAXException, TransformerException, ParserConfigurationException, IOException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String result = scientificPaperRepository.getByTitle(loadProperties, title);
        result = scientificPaperRepository.removeAuthors(result);
        return result;
    }

    public List<String> getAllPapers(AuthenticationUtilities.ConnectionProperties loadProperties) {
        return scientificPaperRepository.getAllPapers(loadProperties);
    }

    public Boolean checkIfReviewed(AuthenticationUtilities.ConnectionProperties loadXMLProperties, String title) throws Exception {
        // if one reviewer isnt done, paper is not considered reviewed
        Users allUsers = userRepository.getAll(loadXMLProperties);
        for(TUser user : allUsers.getUser()){
            for(String pendingPaper : user.getPendingPapersToReview().getPaperToReviewID()){
                if(pendingPaper.equalsIgnoreCase(title)){
                    return false;
                }
            }
        }
        return true;
    }

    public List<String> advancedSearch(AuthenticationUtilities.ConnectionProperties loadProperties, String status,
                                       String title, String published, String authorsName,
                                       String keywords, String accepted, String author) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException, ParseException {
        if(author.equals("my")){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return scientificPaperRepository.advancedSearch(loadProperties,  user.getEmail(),  status, title,
                    published, authorsName, keywords, accepted);
        }else{
            return scientificPaperRepository.advancedSearch(loadProperties,  null,  status, title,
                    published, authorsName, keywords, accepted);
        }

    }


}
