package ftn.project.xml.repository;

import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import ftn.project.xml.util.DOMParser;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class ScientificPaperRepository {
    private static AuthenticationUtilities.ConnectionProperties conn;
    private static String papersCollectionPathInDB = "/db/xml/scientificPapers";   //path kolekcije
    private static String testFilePath = "data\\test\\test_paper.xml";
    private static String schemaPath = "src\\main\\resources\\static\\schemas\\scientificPaper.xsd";
    private static String papersDocumentID = "paper.xml";

    // FOR TESTING PURPOSES (FIRST MAKE save() STATIC, THEN RUN MAIN):
/*
    public static void main(String[] args) throws Exception {
        conn = AuthenticationUtilities.loadProperties();

        File file = new File(testFilePath);
        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);


        ScientificPaperRepository.save(conn, args, content, "1");
    }


*/

    public static String save(AuthenticationUtilities.ConnectionProperties conn, String args[], String paper, String paperID) throws Exception {
        System.out.println(paperID);
        Class<?> cl = Class.forName(conn.driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        Collection col = null;


        //vidi da li ima gresaka
        try{
            DOMParser parser = new DOMParser();
            Document d = parser.buildDocument(testFilePath, schemaPath);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            // get the collection
            System.out.println("[INFO] Retrieving the collection: " + papersCollectionPathInDB);
            col = DBUtils.getOrCreateCollection(conn, papersCollectionPathInDB);
            col.setProperty("indent", "yes");
            System.out.println(papersDocumentID + paperID);
            XMLResource res =  (XMLResource) col.createResource(papersDocumentID + paperID, XMLResource.RESOURCE_TYPE);

            //first to add document
            String filePath = "data\\test\\test_paper.xml";
            File f = new File(filePath);

            if(!f.canRead()) {
                System.out.println("[ERROR] Cannot read the file: " + filePath);
                throw new Exception();
            }

            res.setContent(f);
            System.out.println("[INFO] Storing the document: " + res.getId());

            col.storeResource(res);
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
        return paper;
    }






}
