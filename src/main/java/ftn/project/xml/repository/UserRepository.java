package ftn.project.xml.repository;

import ftn.project.xml.model.TRole;
import ftn.project.xml.model.TUser;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XUpdateQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static ftn.project.xml.templates.XUpdateTemplate.*;


public class UserRepository {
    private static AuthenticationUtilities.ConnectionProperties conn;
    private static String usersCollectionPathInDB = "/db/xml/users";   //path kolekcije
    private static String usersDocumentID = "users.xml";

    // FOR TESTING PURPOSES (FIRST MAKE save() STATIC, THEN RUN MAIN):


    public static void main(String[] args) throws Exception {
        TUser user = new TUser();
        user.setEmail("email");
        user.setMyPapers(new TUser.MyPapers());
        user.setMyReviews(new TUser.MyReviews());
        user.setName("name");
        user.setPassword("pass");
        user.setPendingPapersToReview(new TUser.PendingPapersToReview());
        user.setRole(TRole.AUTHOR);
        user.setSurname("surname");
        user.setUsername("username");

        conn = AuthenticationUtilities.loadProperties();
        UserRepository.save(conn, args, user);
    }


    public static TUser save(AuthenticationUtilities.ConnectionProperties conn, String args[], TUser user) throws Exception {
        Class<?> cl = Class.forName(conn.driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        Collection col = null;

        try {
            //usera pretvori u xml frag.
            String xmlFragment = user2XML(user);

            // get the collection
            System.out.println("[INFO] Retrieving the collection: " + usersCollectionPathInDB);
            col = DBUtils.getOrCreateCollection(conn, usersCollectionPathInDB);
            col.setProperty("indent", "yes");
            XMLResource res =  (XMLResource) col.createResource(usersDocumentID, XMLResource.RESOURCE_TYPE);

            //first to add document
            /*String filePath = "data/test/test_users.xml";
            File f = new File(filePath);

            if(!f.canRead()) {
                System.out.println("[ERROR] Cannot read the file: " + filePath);
                throw new Exception();
            }

            res.setContent(f);
            System.out.println("[INFO] Storing the document: " + res.getId());

            col.storeResource(res);
            */
            // get an instance of xupdate query service
            System.out.println("[INFO] Fetching XUpdate service for the collection.");
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");
            String contextXPath = "/users";

            System.out.println("[INFO] Appending fragments as last child of " + contextXPath + " node.");
            long mods = xupdateService.updateResource(usersDocumentID, String.format(APPEND, contextXPath, xmlFragment));
            System.out.println("[INFO] " + mods + " modifications processed.");

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
        return user;
    }

    private static String user2XML(TUser user) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");
        OutputStream os = new ByteArrayOutputStream();

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(user, os);
        String userXml = os.toString();
        String pureUserXml = userXml.substring(userXml.indexOf('\n') + 1);
        return pureUserXml;
    }


}
