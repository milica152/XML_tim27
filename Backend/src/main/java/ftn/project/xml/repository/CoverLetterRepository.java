package ftn.project.xml.repository;

import ftn.project.xml.model.CoverLetter;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;

@Repository
public class CoverLetterRepository {


    private static String cLettersCollectionPathInDB = "/db/xml/cLetters";
    private static String cLetterDocumentID = "cLetter.xml";

    @Autowired
    private DBUtils dbUtils;

    public String save(AuthenticationUtilities.ConnectionProperties conn, String xmlRes, String reviewID) throws Exception {
        Collection col = null;
        dbUtils.initilizeDBserver(conn);

        try {
            col = dbUtils.getOrCreateCollection(conn, cLettersCollectionPathInDB);
            col.setProperty("indent", "yes");
            dbUtils.storeDocument(cLetterDocumentID + reviewID, xmlRes, col);
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
        CoverLetter result = null;
        dbUtils.initilizeDBserver(conn);

        Collection col = null;
        XMLResource res = null;

        try {
            col = DatabaseManager.getCollection(conn.uri + cLettersCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = (XMLResource)col.getResource(cLetterDocumentID + id);


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
            System.out.println("[WARNING] Document '" + cLetterDocumentID + id + "' can not be found!");
        }
        return (String) res.getContent();
    }


    public String delete(AuthenticationUtilities.ConnectionProperties conn, String title) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        dbUtils.initilizeDBserver(conn);

        Collection col = null;
        Resource res = null;
        try {
            col = DatabaseManager.getCollection(conn.uri + cLettersCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = col.getResource(cLetterDocumentID + title);

            if(res == null) {
                System.out.println("[WARNING] Document '" + cLetterDocumentID+title + "' can not be found!");
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
}
