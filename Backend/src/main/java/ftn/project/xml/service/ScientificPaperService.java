package ftn.project.xml.service;

import ftn.project.xml.dto.MetadataDTO;
import ftn.project.xml.dto.ScientificPaperDTO;
import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.repository.ScientificPaperRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DOMParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ftn.project.xml.util.MetadataExtractor;
import ftn.project.xml.util.RDFAuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.XMLDBException;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ScientificPaperService {
    private static String schemaPath = "src\\main\\resources\\static\\schemas\\scientificPaper.xsd";


    Logger logger = LoggerFactory.getLogger(ScientificPaperService.class);

    @Autowired
    public DOMParser domParser;

    @Autowired
    private ScientificPaperRepository scientificPaperRepository;

    @Autowired
    private MetadataExtractor metadataExtractor;

    @Value("${scientificPaper.XSLPath}")
    private String xslPath;

    @Value("${scientificPaper.XHTMLPath}")
    private String htmlPath;

    public String save(AuthenticationUtilities.ConnectionProperties conn, String xmlRes) throws Exception {
        try{
            DOMParser parser = new DOMParser();
            Document d = parser.buildDocument(xmlRes, schemaPath);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            NodeList nl = d.getElementsByTagName("title");
            String title = nl.item(0).getTextContent();
            logger.info("New Scientific paper published under the title: " + title);

            //System.out.println(title);
            // pokreni bussiness process

            // popuni sp metapodacima
            NodeList metadata = d.getElementsByTagName("metadata");

            // list of authors and keywords
            NodeList authors = d.getElementsByTagName("authors");
            NodeList keywords = d.getElementsByTagName("keywords");

            // date published
            Element datePublished = d.createElement("datePublished");
            datePublished.setTextContent(df.format(new Date()));
            datePublished.setAttribute("property", "published");    // dodati rdf podatak na property

            // status
            Element status = d.createElement("status");
            status.setAttribute("property", "spStatus");      // dodati rdf podatak na property
            status.setTextContent("in process");

            // about
            Element about = d.createElement("about");
            d.getDocumentElement().setAttribute("about", title);


            metadata.item(0).insertBefore(datePublished, keywords.item(0));
            metadata.item(0).insertBefore(status, datePublished);

            ByteArrayOutputStream metadataStream = new ByteArrayOutputStream();
            String newSciPap = domParser.DOMToXML(d);

            metadataExtractor.extractMetadata(new ByteArrayInputStream(newSciPap.getBytes()), metadataStream);
            String extractedMetadata = new String(metadataStream.toByteArray());
            System.out.println(extractedMetadata);

            // saving to RDF store
            scientificPaperRepository.saveMetadata(extractedMetadata);
            scientificPaperRepository.save(conn, title, newSciPap);

            return "ok";

        }catch (Exception e){
            logger.warn("Ivalid document type! Must be ScientificPaper");
        }
        return "error";
    }

    public String getByTitle(AuthenticationUtilities.ConnectionProperties conn, String s) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return scientificPaperRepository.getByTitle(conn, s);
    }

    public List<ScientificPaperDTO> search(AuthenticationUtilities.ConnectionProperties loadProperties, String author, String title, String keyword) {
        return scientificPaperRepository.search(loadProperties,  author,  title,  keyword);
    }

    public String delete(String title, AuthenticationUtilities.ConnectionProperties loadProperties) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return scientificPaperRepository.delete(loadProperties, title);
    }

    public List<MetadataDTO> getMetadata(RDFAuthenticationUtilities.RDFConnectionProperties properties, String title) {
        return scientificPaperRepository.getMetadata(properties, title);
    }

    public void transformToHTML(String xml) throws TransformerException {
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
        System.out.println("The generated HTML file is:" + outputFile);

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
            System.out.println(sp);
            metadataExtractor.extractMetadata(new ByteArrayInputStream(sp.getBytes()), metadataStream);
            String extractedMetadata = new String(metadataStream.toByteArray());
            System.out.println(extractedMetadata);
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

    public String exportMetadataToJSON(RDFAuthenticationUtilities.RDFConnectionProperties loadProperties, String title, String filePath) {


        return "ok";
    }
}
