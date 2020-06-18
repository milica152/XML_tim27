package ftn.project.xml.repository;

import ftn.project.xml.dto.MetadataDTO;
import ftn.project.xml.dto.ScientificPaperDTO;
import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.model.TUser;
import ftn.project.xml.service.ScientificPaperService;
import ftn.project.xml.util.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.exist.xmldb.EXistResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.base.Resource;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ftn.project.xml.templates.XUpdateTemplate.TARGET_NAMESPACE;

@Repository
public class ScientificPaperRepository {
    private static final String papersCollectionPathInDB = "/db/xml/scientificPaper";
    private static final String papersDocumentID = "paper.xml";
    private static final String SPARQL_NAMED_GRAPH_URI = "/sp";

    Logger logger = LoggerFactory.getLogger(ScientificPaperRepository.class);

    @Autowired
    private MetadataExtractor metadataExtractor;

    @Autowired
    private DBUtils dbUtils;

    @Autowired
    private SparqlUtil sparqlUtil;

    @Autowired
    private UserRepository userRepository;

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
        ScientificPaper sp = null;
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
        }
        return (String) res.getContent();
    }

    public List<ScientificPaperDTO> search(AuthenticationUtilities.ConnectionProperties conn, String author, String title, String keyword) {
        List<ScientificPaperDTO> sps = new ArrayList<>();
        Collection col = null;

        try {
            dbUtils.initilizeDBserver(conn);
        } catch (ClassNotFoundException | XMLDBException | InstantiationException | IllegalAccessException e) {
            logger.error("Problem sa inicijalizovanjem baze");
            e.printStackTrace();
        }
        try {
            col = dbUtils.getOrCreateCollection(conn, papersCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");
            String[] resources = col.listResources();
            if(resources.length!=0){
                sps = doSearch(col, conn, resources, author, title, keyword);
            }

        } catch (XMLDBException e) {
            logger.error("Problem prilikom dobavljanj dokumenata.");
            e.printStackTrace();
        }
        return sps;
    }

    private List<ScientificPaperDTO> doSearch(Collection col, AuthenticationUtilities.ConnectionProperties conn, String[] resources, String author, String title, String keyword) throws XMLDBException {
        List<ScientificPaperDTO> result = new ArrayList<>();
        String xqueryExp = "";

        XQueryService xqueryService = (XQueryService) col.getService("XQueryService", "1.0");
        xqueryService.setProperty("indent", "yes");
        xqueryService.setNamespace("", TARGET_NAMESPACE);
        for(String res : resources){
            System.out.println(res);
            try {
                xqueryExp = "doc(\"" + res + "\")//scientificPaper/title[contains(.,\"" + title.trim() + "\")]/text()";
                ResourceSet resultSet = xqueryService.query(xqueryExp);
                if(resultSet.getSize()>0){
                    xqueryExp = "doc(\"" + res + "\")//scientificPaper/authors/author/name[contains(.,\"" + author.trim() + "\")]/text() | //scientificPaper/authors/author/surname[contains(.,\"" + author.trim() + "\")]/text()";
                    resultSet = xqueryService.query(xqueryExp);
                    if(resultSet.getSize()>0) {
                        xqueryExp = "doc(\"" + res + "\")//scientificPaper/metadata/keywords/keyword[contains(.,\"" + keyword.trim() + "\")]/text()";
                        resultSet = xqueryService.query(xqueryExp);
                        if(resultSet.getSize()>0) {
                            ScientificPaperDTO r = getTitleAuthorsAndKeywords(res, xqueryService);
                            result.add(r);
                        }
                    }
                }

            } catch (XMLDBException e) {
                logger.error("Problem sa pretragom");
                e.printStackTrace();
            }


        }
        return result;
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

    public String delete(AuthenticationUtilities.ConnectionProperties conn, String title) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        dbUtils.initilizeDBserver(conn);

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
        }
        return "ok";
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


    public List<String> getMyPapers(AuthenticationUtilities.ConnectionProperties loadProperties, String email) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        TUser user = userRepository.getUserByEmail(loadProperties, email);
        return user.getMyPapers().getMyScientificPaperID();
    }
}
