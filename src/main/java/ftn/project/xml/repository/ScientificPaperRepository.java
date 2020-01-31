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
    private static String testFilePath = "data\\test\\test_paper.xml";
    private static String schemaPath = "schemas\\scientificPaper.xsd";
    private static String papersDocumentID = "paper.xml";

    @Autowired
    private DBUtils dbUtils;

    public String save(AuthenticationUtilities.ConnectionProperties conn, String paperID) throws Exception {
        Class<?> cl = Class.forName(conn.driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        Collection col = null;

        // is document valid
        try{
            DOMParser parser = new DOMParser();
            Document d = parser.buildDocument(testFilePath, schemaPath);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            // get the collection
            System.out.println("[INFO] Retrieving the collection: " + papersCollectionPathInDB);
            col = dbUtils.getOrCreateCollection(conn, papersCollectionPathInDB);
            col.setProperty("indent", "yes");

            String xmlResource = FileUtils.readFileToString(new File("data\\test\\test_paper.xml"), StandardCharsets.UTF_8);
            dbUtils.storeDocument(papersDocumentID + paperID, xmlResource, col);

            return xmlResource;

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
        // initialize collection and document identifiers

        System.out.println("[INFO] Using defaults.");
        System.out.println("\t- collection ID: " + papersCollectionPathInDB);
        System.out.println("\t- document ID: " + papersDocumentID + paperID + "\n");

        // initialize database driver
        System.out.println("[INFO] Loading driver class: " + conn.driver);
        Class<?> cl = Class.forName(conn.driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource res = null;

        try {
            // get the collection
            System.out.println("[INFO] Retrieving the collection: " + papersCollectionPathInDB);
            col = DatabaseManager.getCollection(conn.uri + papersCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            System.out.println("[INFO] Retrieving the document: " + papersDocumentID + paperID);
            res = (XMLResource)col.getResource(papersDocumentID + paperID);

            if(res == null) {
                System.out.println("[WARNING] Document '" + papersDocumentID+paperID + "' can not be found!");
            } else {

                System.out.println("[INFO] Binding XML resouce to an JAXB instance: ");
                JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");

                Unmarshaller unmarshaller = context.createUnmarshaller();

                ScientificPaper sPaper = (ScientificPaper) unmarshaller.unmarshal(res.getContentAsDOM());
                sp = sPaper;
                System.out.println("[INFO] Showing the document as JAXB instance: ");
                System.out.println(sPaper.getTitle().getValue());
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
