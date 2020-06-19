package ftn.project.xml.repository;

import ftn.project.xml.dto.ScientificPaperDTO;
import ftn.project.xml.model.Review;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import ftn.project.xml.util.DOMParser;
import org.exist.xmldb.EXistResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import org.w3c.dom.Document;

import javax.xml.bind.Element;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ftn.project.xml.templates.XUpdateTemplate.TARGET_NAMESPACE;

@Repository
public class ReviewRepository {
    private static String reviewsCollectionPathInDB = "/db/xml/reviews";
    private static String reviewDocumentID = "review.xml";
    Logger logger = LoggerFactory.getLogger(ReviewRepository.class);
    private static String schemaPath = "src\\main\\resources\\static\\schemas\\Review.xsd";

    @Autowired
    private DBUtils dbUtils;

    @Autowired
    public DOMParser domParser;


    public String save(AuthenticationUtilities.ConnectionProperties conn, String xmlRes, String reviewID) throws Exception {
        Collection col = null;
        dbUtils.initilizeDBserver(conn);

        try {
            col = dbUtils.getOrCreateCollection(conn, reviewsCollectionPathInDB);
            col.setProperty("indent", "yes");
            dbUtils.storeDocument(reviewDocumentID + reviewID, xmlRes, col);
            return xmlRes;

        } finally {

            // don't forget to cleanup
            if(col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }

    public String getByDocumentId(AuthenticationUtilities.ConnectionProperties conn, String id) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        dbUtils.initilizeDBserver(conn);

        Collection col = null;
        XMLResource res = null;

        try {
            col = DatabaseManager.getCollection(conn.uri + reviewsCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = (XMLResource)col.getResource(reviewDocumentID + id);
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
            System.out.println("[WARNING] Document '" + reviewDocumentID + id + "' can not be found!");
            return "error";
        }
        return (String)res.getContent();
    }

    public String delete(AuthenticationUtilities.ConnectionProperties conn, String title) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        dbUtils.initilizeDBserver(conn);

        Collection col = null;
        Resource res = null;
        try {
            col = DatabaseManager.getCollection(conn.uri + reviewsCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = col.getResource(reviewDocumentID + title);

            if(res == null) {
                System.out.println("[WARNING] Document '" + reviewDocumentID+title + "' can not be found!");
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

    public ArrayList<String> findAllBySPTitle(AuthenticationUtilities.ConnectionProperties conn, String title) throws SAXException, TransformerException, ParserConfigurationException, IOException {
        ArrayList<String> result = new ArrayList<>();
        Collection col = null;

        try {
            dbUtils.initilizeDBserver(conn);
        } catch (ClassNotFoundException | XMLDBException | InstantiationException | IllegalAccessException e) {
            logger.error("Problem sa inicijalizovanjem baze");
            e.printStackTrace();
        }
        try {
            col = dbUtils.getOrCreateCollection(conn, conn.uri + reviewsCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");
            String[] resources = col.listResources();
            if(resources.length!=0){
                result = doSearch(col, conn, resources, title);
            }

        } catch (XMLDBException e) {
            logger.error("Problem prilikom dobavljanj dokumenata.");
            e.printStackTrace();
        }

        return result;
    }

    private ArrayList<String> doSearch(Collection col, AuthenticationUtilities.ConnectionProperties conn, String[] resources, String title) throws XMLDBException {
        ArrayList<String> result = new ArrayList<>();
        String xqueryExp = "";

        XQueryService xqueryService = (XQueryService) col.getService("XQueryService", "1.0");
        xqueryService.setProperty("indent", "yes");
        xqueryService.setNamespace("", TARGET_NAMESPACE);
        for (String res : resources) {
            try {
                xqueryExp = "doc(\"" + res + "\")//review[title[. = \"" + title.trim() + "\"]]";
                ResourceSet resultSet = xqueryService.query(xqueryExp);
                ResourceIterator i = resultSet.getIterator();
                Resource resource = null;
                while(i.hasMoreResources()) {
                    try {
                        resource = i.nextResource();
                        result.add(resource.getContent().toString());
                    } finally {

                        // don't forget to cleanup resources
                        try {
                            ((EXistResource)resource).freeResources();
                        } catch (XMLDBException xe) {
                            xe.printStackTrace();
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
}
