package ftn.project.xml.repository;

import ftn.project.xml.model.TRole;
import ftn.project.xml.model.TUser;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import ftn.project.xml.util.HelpFunctions;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static ftn.project.xml.templates.XUpdateTemplate.*;

@Repository
public class UserRepository {
    private static String usersCollectionPathInDB = "/db/xml/users";   //path kolekcije
    private static String usersDocumentID = "users.xml";

    @Autowired
    DBUtils dbUtils;


    public TUser save(AuthenticationUtilities.ConnectionProperties conn, TUser user) throws Exception {
        Class<?> cl = Class.forName(conn.driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        Collection col = null;

        try {
            // usera pretvori u xml frag.
            String xmlFragment = user2XML(user);

            // get the collection
            System.out.println("[INFO] Retrieving the collection: " + usersCollectionPathInDB);
            col = dbUtils.getOrCreateCollection(conn, usersCollectionPathInDB);
            col.setProperty("indent", "yes");

            // first to add document
            String xmlResource = FileUtils.readFileToString(new File("data/test/test_users.xml"), StandardCharsets.UTF_8);
            dbUtils.storeDocument(usersDocumentID, xmlResource, col);

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
