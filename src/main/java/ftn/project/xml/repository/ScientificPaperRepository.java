package ftn.project.xml.repository;

import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import ftn.project.xml.util.DOMParser;
import org.apache.commons.io.FileUtils;
import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Repository
public class ScientificPaperRepository {
    private static String papersCollectionPathInDB = "/db/xml/scientificPapers";   //path kolekcije
    private static String papersDocumentID = "paper.xml";

    @Autowired
    private DBUtils dbUtils;

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
                }
            }
        }
    }

    public ScientificPaper getScientificPaperById(AuthenticationUtilities.ConnectionProperties conn, String paperID) throws XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ScientificPaper sp = null;
        dbUtils.initilizeDBserver(conn);

        Collection col = null;
        XMLResource res = null;

        try {
            col = DatabaseManager.getCollection(conn.uri + papersCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = (XMLResource)col.getResource(papersDocumentID + paperID);

            if(res == null) {
                System.out.println("[WARNING] Document '" + papersDocumentID+paperID + "' can not be found!");
            } else {

                JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");

                Unmarshaller unmarshaller = context.createUnmarshaller();

                ScientificPaper sPaper = (ScientificPaper) unmarshaller.unmarshal(res.getContentAsDOM());
                sp = sPaper;
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
        return sp;
    }


}
