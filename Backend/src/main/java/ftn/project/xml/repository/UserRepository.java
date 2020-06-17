package ftn.project.xml.repository;

import ftn.project.xml.model.TRole;
import ftn.project.xml.model.TUser;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import org.apache.commons.io.FileUtils;
import org.apache.jena.base.Sys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static ftn.project.xml.templates.XUpdateTemplate.*;

@Repository
public class UserRepository {
    private static String usersCollectionPathInDB = "/db/xml/users";   //path kolekcije
    private static String usersDocumentID = "users.xml";


    Logger logger = LoggerFactory.getLogger(UserRepository.class);

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
            logger.info("Retrieving the collection: " + usersCollectionPathInDB);
            col = dbUtils.getOrCreateCollection(conn, usersCollectionPathInDB);
            col.setProperty("indent", "yes");

            // first to add document
            String xmlResource = FileUtils.readFileToString(new File("data/test/test_users.xml"), StandardCharsets.UTF_8);
            dbUtils.storeDocument(usersDocumentID, xmlResource, col);

            // get an instance of xupdate query service
            logger.info("Fetching XUpdate service for the collection.");
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");
            String contextXPath = "/users";

            logger.info("Appending fragments as last child of " + contextXPath + " node.");
            long mods = xupdateService.updateResource(usersDocumentID, String.format(APPEND, contextXPath, xmlFragment));
            logger.info(mods + " modifications processed.");

        } finally {

            // don't forget to cleanup
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                    return new TUser();
                }
            }
        }
        return user;
    }

    public TUser getUserByEmail(AuthenticationUtilities.ConnectionProperties conn, String email) throws ClassNotFoundException, IllegalAccessException, InstantiationException, XMLDBException {
        String xpathExp = "/users/user[email=\"" + email + "\"]";
        ResourceSet result = getByXPathExpr(xpathExp, conn);
        ResourceIterator i = result.getIterator();
        Resource res  = i.nextResource();
        if(res==null){
            return null;
        }
        TUser user = null;
        try {
            user = XML2User(res.getContent().toString());
        } catch (JAXBException e) {
            e.printStackTrace();
            return new TUser();
        }
        return user;
    }

    public List<TUser> getUsersByRole(AuthenticationUtilities.ConnectionProperties conn, String role) throws ClassNotFoundException, IllegalAccessException, InstantiationException, XMLDBException, JAXBException {
        String xpathExp = "/users/user[role=\"" + role + "\"]";
        List<TUser> lista = new ArrayList<>();

        ResourceSet result = getByXPathExpr(xpathExp, conn);
        ResourceIterator i = result.getIterator();
        while (i.hasMoreResources()) {
            Resource res = i.nextResource();
            lista.add(XML2User(res.getContent().toString()));
        }

        return lista;
    }

    public TUser getUserByEmailAndPassword(AuthenticationUtilities.ConnectionProperties conn, String email, String password) throws ClassNotFoundException, IllegalAccessException, InstantiationException, XMLDBException {
        String xpathExp = "/users/user[email=\"" + email + "\" and password=\"" + password + "\"]";

        ResourceSet result = getByXPathExpr(xpathExp, conn);
        ResourceIterator i = result.getIterator();
        Resource res = i.nextResource();

        TUser user = null;
        try {
            user = XML2User(res.getContent().toString());
        } catch (NullPointerException npe) {
            logger.info("User not found!");
            return null;
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return user;
    }

    public TUser getEditor(AuthenticationUtilities.ConnectionProperties conn) throws Exception {
        List<TUser> users = getUsersByRole(conn, "EDITOR");
        if (users.size() == 0) {    // ako nema editora, inicijalizuj
            TUser user = new TUser();
            user.setUsername("editor");
            user.setSurname("editorski");
            user.setRole(TRole.EDITOR);
            user.setPendingPapersToReview(new TUser.PendingPapersToReview());
            user.setMyPapers(new TUser.MyPapers());
            user.setMyReviews(new TUser.MyReviews());
            user.setPassword("editor");
            user.setName("editor");
            user.setEmail("milica.medic@gmail.com");

            this.save(conn, user);
            return user;

        } else {
            return users.get(0);
        }
    }

    public ResourceSet getByXPathExpr(String xpathExp, AuthenticationUtilities.ConnectionProperties conn) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        Collection col = null;
        // initialize database driver
        dbUtils.initilizeDBserver(conn);

        try {
            // get the collection
            col = DatabaseManager.getCollection(conn.uri + usersCollectionPathInDB);

            // get an instance of xpath query service
            XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");

            // make the service aware of namespaces, using the default one
            xpathService.setNamespace("", TARGET_NAMESPACE);

            // execute xpath expression
            return xpathService.query(xpathExp);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {

            // don't forget to cleanup
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return null;

    }

    public static String user2XML(TUser user) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");
        OutputStream os = new ByteArrayOutputStream();

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(user, os);
        String userXml = os.toString();
        return userXml.substring(userXml.indexOf('\n') + 1);
    }

    public static TUser XML2User(String xmlContent) throws JAXBException {
        TUser result;
        StringReader reader = new StringReader(xmlContent);

        JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        result = (TUser) unmarshaller.unmarshal(reader);

        return result;
    }

    public String delete(AuthenticationUtilities.ConnectionProperties conn, String email){
        Collection col = null;

        // initialize database driver
        try {
            dbUtils.initilizeDBserver(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "Problem kod inicijalizovanja baze(IllegalAccessException)";
        } catch (InstantiationException e) {
            e.printStackTrace();
            return "Problem kod inicijalizovanja baze(InstantiationException)";
        } catch (XMLDBException e) {
            e.printStackTrace();
            return "Problem kod inicijalizovanja baze(XMLDBException)";
        }

        // get the collection
        logger.info("Retrieving the collection: " + usersCollectionPathInDB);
        try {
            col = DatabaseManager.getCollection(conn.uri + usersCollectionPathInDB, conn.user, conn.password);
            col.setProperty("indent", "yes");
        } catch (XMLDBException e) {
            e.printStackTrace();
            return "Problem dobavljanja kolekcije: " + usersCollectionPathInDB;
        }

        XUpdateQueryService xupdateService = null;
        try {
            xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");
        } catch (XMLDBException e) {
            e.printStackTrace();
            return "Problem dobavljanja XUpdateQueryService-a";
        }



        String xQuery = String.format(
                "for $user in doc(\"%s\")//user\n" +
                        "where $user/email = \"%s\"\n" +
                        "return (update delete $user)",
                usersCollectionPathInDB + "/" + usersDocumentID,
                email);


        try {
            XPathQueryService xPathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xPathService.setProperty("indent", "yes");
            xPathService.query(xQuery);
        } catch (XMLDBException e) {
            e.printStackTrace();
            return "Problem prilikom rada sa XPathQueryService-om.";
        }
        return "ok";
    }

}
