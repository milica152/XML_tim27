package ftn.project.xml.repository;

import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import ftn.project.xml.util.DOMParser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Repository
public class ScientificPaperRepository {
    private static String papersCollectionPathInDB = "/db/xml/scientificPapers";   //path kolekcije
    private static String testFilePath = "data\\test\\test_paper.xml";
    private static String schemaPath = "src\\main\\resources\\static\\schemas\\scientificPaper.xsd";
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

}
