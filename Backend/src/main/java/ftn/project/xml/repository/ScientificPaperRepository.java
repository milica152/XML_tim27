package ftn.project.xml.repository;
import ftn.project.xml.dto.MetadataDTO;
import ftn.project.xml.dto.ScientificPaperDTO;
import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.model.TUser;
import ftn.project.xml.model.User;
import ftn.project.xml.model.Users;
import ftn.project.xml.service.ScientificPaperService;
import ftn.project.xml.util.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.checkerframework.checker.units.qual.A;
import org.exist.xmldb.EXistResource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.base.Resource;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ftn.project.xml.templates.XUpdateTemplate.TARGET_NAMESPACE;

@Repository
public class ScientificPaperRepository {
    private static final String papersCollectionPathInDB = "/db/xml/scientificPaper";
    private static final String papersDocumentID = "paper.xml";
    private static final String SPARQL_NAMED_GRAPH_URI = "/sp";
    private static String schemaPath = "Backend\\src\\main\\resources\\static\\schemas\\scientificPaper.xsd";

    Logger logger = LoggerFactory.getLogger(ScientificPaperRepository.class);

    @Autowired
    private MetadataExtractor metadataExtractor;

    @Autowired
    private DBUtils dbUtils;

    @Autowired
    private SparqlUtil sparqlUtil;

    @Autowired
    public DOMParser domParser;

    @Autowired
    public UserRepository userRepository;

    public String save(AuthenticationUtilities.ConnectionProperties conn, String paperID, String xmlRes) throws Exception {
        Collection col = null;
        dbUtils.initilizeDBserver(conn);

        try {
            col = dbUtils.getOrCreateCollection(conn, papersCollectionPathInDB);
            col.setProperty("indent", "yes");
            dbUtils.storeDocument(papersDocumentID + paperID, xmlRes, col);
            return xmlRes;

        } finally {

            // don't forget to cleanup
            if(col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                    return "Greska pri cuvanju dokumenta.";
                }
            }
        }
    }

    public String getByTitle(AuthenticationUtilities.ConnectionProperties conn, String paperID) throws XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        dbUtils.initilizeDBserver(conn);

        Collection col = null;
        XMLResource res = null;

        try {
            col = DatabaseManager.getCollection(conn.uri + papersCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = (XMLResource)col.getResource(papersDocumentID + paperID);


        } catch (XMLDBException e) {
            e.printStackTrace();
        } finally {
            //don't forget to clean up!

            if(res != null) {
                try {
                    ((EXistResource)res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }

            if(col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        if(res == null) {
            logger.warn("Document '" + papersDocumentID+paperID + "' can not be found!");
            return "";
        }
        return (String) res.getContent();

    }

    public List<String> search(AuthenticationUtilities.ConnectionProperties conn, String author, String text) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        List<String> sps;
        List<String> found = new ArrayList<>();
        if(author == null || author.isEmpty()){
            sps = getAllPapers(conn);
        }
        else{
            sps = getMyPapers(conn, author);
        }

        for(String paperId: sps){
            String paper = getByTitle(conn, paperId);
            org.jsoup.nodes.Document doc = Jsoup.parse(paper);
            Elements selector = doc.select("concreteImage");
            for (Element element : selector) {
                element.remove();
            }
            selector = doc.select("metadata");
            for (Element element : selector) {
                element.remove();
            }
            if((doc.text()).toLowerCase().contains(text.toLowerCase())){
                found.add(paperId);
            }
        }
        return found;
    }

    public List<String> getMyPapers(AuthenticationUtilities.ConnectionProperties loadProperties, String email) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        TUser user = userRepository.getUserByEmail(loadProperties, email);
        List<String> myPapers = new ArrayList<>();
        try{
            user.getMyPapers().getMyScientificPaperID();
            return user.getMyPapers().getMyScientificPaperID();
        }catch (NullPointerException ex){
            return myPapers;
        }
    }

    public List<String> getAllPapers(AuthenticationUtilities.ConnectionProperties loadProperties) {
        List<String> papers = new ArrayList<>();
        Collection col = null;
        DOMParser parser = new DOMParser();
        User logged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            dbUtils.initilizeDBserver(loadProperties);
        } catch (ClassNotFoundException | XMLDBException | InstantiationException | IllegalAccessException e) {
            logger.error("Problem sa inicijalizovanjem baze");
            e.printStackTrace();
        }
        try {
            col = dbUtils.getOrCreateCollection(loadProperties, papersCollectionPathInDB);

            col.setProperty(OutputKeys.INDENT, "yes");
            String[] resources = col.listResources();
            if(resources.length!=0){
                for(String p: resources){
                    String status = getStatus(loadProperties, p.substring(9));
                    if(status.equals("published")){
                        papers.add(p.substring(9));
                    }else if(status.equals("submitted")){
                        String xmlRes = getByTitle(loadProperties, p.substring(9));
                        Document d = parser.buildDocument(xmlRes, schemaPath);
                        NodeList authors = d.getElementsByTagName("contact");
                        for(int i=0; i<authors.getLength();i++){
                            String email = authors.item(i).getTextContent();
                            if(email.equals(logged.getEmail())){
                                papers.add(p.substring(9));
                                break;
                            }
                        }
                    }
                }
            }
        } catch (XMLDBException e) {
            logger.error("Problem prilikom dobavljanj dokumenata.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return papers;

    }

    public String removeAuthors(String oldResource) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        Document d = domParser.buildDocument(oldResource, schemaPath);
        NodeList authorsList =  d.getDocumentElement().getElementsByTagName("authors");
        Node authors = authorsList.item(0);
        d.getDocumentElement().removeChild(authors);
        return domParser.DOMToXML(d);
    }


    private ScientificPaperDTO getTitleAuthorsAndKeywords(String res, XQueryService xQueryService) throws XMLDBException {
        ScientificPaperDTO r = new ScientificPaperDTO();

        // title
        String xQueryExp = "doc(\"" + res + "\")//scientificPaper/title/text()";
        ResourceSet resultSet = xQueryService.query(xQueryExp);
        ResourceIterator i = resultSet.getIterator();
        r.setTitle(i.nextResource().getContent().toString());

        // authors
        xQueryExp = "for $author in doc(\"" + res + "\")//scientificPaper/authors/author " +
                "return fn:concat($author/name/text(), \" \",  $author/surname/text())";
        resultSet = xQueryService.query(xQueryExp);
        i = resultSet.getIterator();
        Resource resource = null;
        while(i.hasMoreResources()) {
            try {
                resource = i.nextResource();
                r.getAuthors().add(String.valueOf(resource.getContent()));
            } finally {

                // don't forget to cleanup resources
                try {
                    ((EXistResource)resource).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }


        // keywords
        xQueryExp = "doc(\"" + res + "\")//scientificPaper/metadata/keywords/keyword/text()";
        resultSet = xQueryService.query(xQueryExp);
        i = resultSet.getIterator();
        resource = null;

        while(i.hasMoreResources()) {
            try {
                resource = i.nextResource();
                r.getKeywords().add(String.valueOf(resource.getContent()));


            } finally {

                // don't forget to cleanup resources
                try {
                    ((EXistResource)resource).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return r;
    }

    public void saveMetadata(String xmlRes) throws IOException {
        RDFAuthenticationUtilities.RDFConnectionProperties conn = RDFAuthenticationUtilities.loadProperties();
        Model model = ModelFactory.createDefaultModel();
        model.read(new ByteArrayInputStream(xmlRes.getBytes()), null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out, SparqlUtil.NTRIPLES);

        String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI,
                new String(out.toByteArray()));

        UpdateRequest update = UpdateFactory.create(sparqlUpdate);

        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
        processor.execute();
    }


    public void deleteMetadata(String xmlRes) throws IOException, TransformerException {
        ByteArrayOutputStream metadataStream = new ByteArrayOutputStream();
        metadataExtractor.extractMetadata(new ByteArrayInputStream(xmlRes.getBytes()), metadataStream);
        String extractedMetadata = new String(metadataStream.toByteArray());
        RDFAuthenticationUtilities.RDFConnectionProperties conn = RDFAuthenticationUtilities.loadProperties();
        Model model = ModelFactory.createDefaultModel();
        model.read(new ByteArrayInputStream(extractedMetadata.getBytes()), null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out, SparqlUtil.NTRIPLES);

        String sparqlUpdate = SparqlUtil.deleteData(conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI,
                new String(out.toByteArray()));
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);

        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
        processor.execute();
    }

    public String delete(AuthenticationUtilities.ConnectionProperties conn, String title) throws Exception {
        dbUtils.initilizeDBserver(conn);

        DOMParser parser = new DOMParser();
        String xmlRes = getByTitle(conn, title.replaceAll(" ",""));
        Document d = parser.buildDocument(xmlRes, schemaPath);
        NodeList authors = d.getElementsByTagName("contact");
        ArrayList<TUser> usersAuthors = new ArrayList<>();
        for(int i=0; i<authors.getLength();i++){
            String email = authors.item(i).getTextContent();
            TUser user1 = userRepository.getUserByEmail(conn, email);
            usersAuthors.add(user1);
        }
        Collection col = null;
        Resource res = null;
        try {
            col = DatabaseManager.getCollection(conn.uri + papersCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = col.getResource(papersDocumentID + title);

            if(res == null) {
                logger.warn("Document '" + papersDocumentID+title + "' can not be found!");
            } else {
                col.removeResource(res);
            }
        } catch (XMLDBException e) {
            e.printStackTrace();
        } finally {
            //don't forget to clean up!

            if(res != null) {
                try {
                    ((EXistResource)res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }

            if(col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
            for(TUser u: usersAuthors){
                u.getMyPapers().getMyScientificPaperID().remove(title);
                userRepository.save(conn, u);
            }

        }
        return "Scientific paper deleted!";
    }

    public ArrayList<MetadataDTO> getMetadata(RDFAuthenticationUtilities.RDFConnectionProperties conn, String title) {
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI, "?s ?p ?o");
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();
        String varName;
        RDFNode varValue;
        ArrayList<MetadataDTO> metadataDTOs = new ArrayList<MetadataDTO>();

        while(results.hasNext()) {

            // A single answer from a SELECT query
            QuerySolution querySolution = results.next() ;
            Iterator<String> variableBindings = querySolution.varNames();
            MetadataDTO metadataDTO = new MetadataDTO();

            // Retrieve variable bindings
            while (variableBindings.hasNext()) {

                varName = variableBindings.next();
                varValue = querySolution.get(varName);
                if(varName.equalsIgnoreCase("o")){
                    metadataDTO.setObject(varValue.toString());
                } else if(varName.equalsIgnoreCase("p")){
                    metadataDTO.setPredicate(varValue.toString());
                } else if(varName.equalsIgnoreCase("s")){
                    int lastIndex = varValue.toString().lastIndexOf('/');
                    if(varValue.toString().substring(lastIndex + 1).equalsIgnoreCase(title)){
                        metadataDTO.setSubject(varValue.toString());
                    }else{
                        metadataDTO.setSubject(null);
                    }
                } else{
                    logger.warn("No attribute with name: " + varName);
                }
                //System.out.println(varName + ": " + varValue);
            }
            if(metadataDTO.getSubject() != null){
                metadataDTOs.add(metadataDTO);
            }
            //System.out.println();
        }
        return metadataDTOs;

    }

    public String getStatus(AuthenticationUtilities.ConnectionProperties loadProperties, String paperId) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String paper = getByTitle(loadProperties, paperId);
        org.jsoup.nodes.Document doc = Jsoup.parse(paper);
        Elements selector = doc.select("status");
        return selector.text();
    }

    BiFunction<List<String>, Predicate<String>, List<String>> SP_FILTER =
            (sps, fields) -> sps.stream().filter(fields).collect(Collectors.toList());

    public List<String> advancedSearch(AuthenticationUtilities.ConnectionProperties loadProperties, String email,
                                       String status, String title, String published,
                                       String authorsName, String keywords, String accepted) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException, ParseException {
        List<String> sps;
        List<String> foundTemp = new ArrayList<>();
        List<String> found = new ArrayList<>();
        if(email == null || email.isEmpty()||email.trim().equals("null")){
            sps = getAllPapers(loadProperties);
        }
        else{
            sps = getMyPapers(loadProperties, email);
        }

        if(status.trim().equals("")||status==null||status.trim().equals("null")){
            found = sps;
        }else{
            for(String paperId: sps){
                String paper = getByTitle(loadProperties, paperId);
                org.jsoup.nodes.Document doc = Jsoup.parse(paper);
                Elements selector = doc.select("status");
                if(selector.get(0).text().equals(status.trim())){
                    found.add(paperId);
                }
            }
        }

        if(title.trim().equals("")||title==null||title.trim().equals("null")){
            foundTemp = found;
        }else{
            for(String paperId: found){
                String paper = getByTitle(loadProperties, paperId);
                org.jsoup.nodes.Document doc = Jsoup.parse(paper);
                Elements selector = doc.select("title");
                if(selector.get(0).text().toLowerCase().contains(title.trim().toLowerCase())){
                    foundTemp.add(paperId);
                }
            }
        }
        found = new ArrayList<>();

        if(published.trim().equals("")||published==null||published.trim().equals("null")){
            found = foundTemp;
        }else{
            for(String paperId: foundTemp){
                String paper = getByTitle(loadProperties, paperId);
                org.jsoup.nodes.Document doc = Jsoup.parse(paper);
                Elements selector = doc.select("datePublished");
                if(selector.get(0).text().equals(published.trim()+"Z")){
                    found.add(paperId);
                }
            }
        }
        foundTemp= new ArrayList<>();

        if(authorsName.trim().equals("")||authorsName==null||authorsName.trim().equals("null")){
            foundTemp = found;
        }else{
            for(String paperId: found){
                String paper = getByTitle(loadProperties, paperId);
                org.jsoup.nodes.Document doc = Jsoup.parse(paper);
                Elements selector = doc.select("author");
                for (int i=0; i<selector.size(); i++) {
                    String nameAuthor = selector.get(i).select("name").text() +" " + selector.get(i).select("surname").text();
                    if(nameAuthor.toLowerCase().contains(authorsName.trim().toLowerCase())){
                        foundTemp.add(paperId);
                    }
                }
            }
        }
        found = new ArrayList<>();

        if(keywords.trim().equals("")||keywords==null||keywords.trim().equals("null")){
            found = foundTemp;
        }else{
            for(String paperId: foundTemp){
                String paper = getByTitle(loadProperties, paperId);
                org.jsoup.nodes.Document doc = Jsoup.parse(paper);
                Elements selector = doc.select("keyword");
                String[] allKeywords = keywords.trim().split(";");
                int foundKW = 0;
                for (int i=0; i<selector.size(); i++) {
                    if(ArrayUtils.contains( allKeywords, selector.get(i).text())){
                        foundKW++;
                    }
                }
                if(foundKW == allKeywords.length){
                    found.add(paperId);
                }


            }
        }
        foundTemp = new ArrayList<>();

        if(accepted.trim().equals("")||accepted==null||accepted.trim().equals("null")){
            foundTemp = found;
        }else{
            for(String paperId: found){
                String paper = getByTitle(loadProperties, paperId);
                org.jsoup.nodes.Document doc = Jsoup.parse(paper);
                Elements selector = doc.select("dateAccepted");
                if(selector.size() != 0){
                    if(selector.get(0).text().equals(accepted.trim()+"Z")){
                        foundTemp.add(paperId);
                    }
                }
            }
        }


        return foundTemp;

    }

    public String getByTitleNoMetadata(AuthenticationUtilities.ConnectionProperties conn, String title) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String paper = getByTitle(conn, title);
        org.jsoup.nodes.Document doc = Jsoup.parse(paper);
        Elements selector = doc.select("metadata");
        for (Element element : selector) {
            element.remove();
        }
        return doc.text();
    }

    public String removeMetadata(String result) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        Document d = domParser.buildDocument(result, schemaPath);
        NodeList metadataList =  d.getDocumentElement().getElementsByTagName("metadata");
        Node metadata = metadataList.item(0);
        d.getDocumentElement().removeChild(metadata);
        return domParser.DOMToXML(d);
    }

//    public void changeStatus(AuthenticationUtilities.ConnectionProperties loadProperties, String paperId, TStatus) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        String paper = getByTitle(loadProperties, paperId);
//        org.jsoup.nodes.Document doc = Jsoup.parse(paper);
//        Elements selector = doc.select("status");
//        selector.text() = ""
//        return selector.text();
//    }

}
