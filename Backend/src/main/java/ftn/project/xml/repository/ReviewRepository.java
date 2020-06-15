package ftn.project.xml.repository;

import ftn.project.xml.model.Review;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;

@Repository
public class ReviewRepository {
    private static String reviewsCollectionPathInDB = "/db/xml/reviews";
    private static String reviewDocumentID = "review.xml";

    @Autowired
    private DBUtils dbUtils;

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

    public Review getByDocumentId(AuthenticationUtilities.ConnectionProperties conn, String id) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        Review result = null;
        dbUtils.initilizeDBserver(conn);

        Collection col = null;
        XMLResource res = null;

        try {
            col = DatabaseManager.getCollection(conn.uri + reviewsCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = (XMLResource)col.getResource(reviewDocumentID + id);

            if(res == null) {
                System.out.println("[WARNING] Document '" + reviewDocumentID + id + "' can not be found!");
            } else {

                JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");

                Unmarshaller unmarshaller = context.createUnmarshaller();

                Review review = (Review) unmarshaller.unmarshal(res.getContentAsDOM());
                result = review;
            }
        } catch (XMLDBException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
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
        return result;
    }
}
