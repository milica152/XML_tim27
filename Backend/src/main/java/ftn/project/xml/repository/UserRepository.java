package ftn.project.xml.repository;

import ftn.project.xml.model.TRole;
import ftn.project.xml.model.TUser;
import ftn.project.xml.model.User;
import ftn.project.xml.model.Users;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static ftn.project.xml.templates.XUpdateTemplate.*;

@Repository
public class UserRepository {
    private static String usersCollectionPathInDB = "/db/xml/users";
    private static String usersDocumentID = "users.xml";

    Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    DBUtils dbUtils;

    public TUser save(AuthenticationUtilities.ConnectionProperties conn, TUser user) throws Exception {
        dbUtils.initilizeDBserver(conn);
        Collection col = null;
        logger.info("Retrieving the collection: " + usersCollectionPathInDB);
        try {
            col = dbUtils.getOrCreateCollection(conn, usersCollectionPathInDB);
            col.setProperty("indent", "yes");
        } catch (XMLDBException e) {
            e.printStackTrace();
            return null;
        }
        XMLResource res = (XMLResource)col.getResource(usersDocumentID );
        if(res == null){
            String xmlResource = FileUtils.readFileToString(new File("src\\main\\resources\\static\\other\\test_users_emprz.xml"), StandardCharsets.UTF_8);
            dbUtils.storeDocument(usersDocumentID, xmlResource, col);
        }
        Users users = XML2Users(res.getContent().toString());
        User logged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for(int i = 0; i < users.getUser().size(); i++){
            if(users.getUser().get(i).getEmail().equalsIgnoreCase(user.getEmail())){
                users.getUser().remove(i);
                users.getUser().add(user);
                break;
            }
        }
        String newXMLRes = users2XML(users);
        dbUtils.storeDocument(usersDocumentID, newXMLRes, col);
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
        if(users.size()==0){
            users = getUsersByRole(conn, "editor");
        }
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
            col = dbUtils.getOrCreateCollection(conn, conn.uri + usersCollectionPathInDB);

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

    public static String users2XML(Users users) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");
        OutputStream os = new ByteArrayOutputStream();

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(users, os);
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

    public static Users XML2Users(String xmlContent) throws JAXBException {
        Users result;
        StringReader reader = new StringReader(xmlContent);

        JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        result = (Users) unmarshaller.unmarshal(reader);

        return result;
    }

    public String delete(AuthenticationUtilities.ConnectionProperties conn, String email) throws Exception {
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
            col = dbUtils.getOrCreateCollection(conn, conn.uri + usersCollectionPathInDB);
            col.setProperty("indent", "yes");
        } catch (XMLDBException e) {
            e.printStackTrace();
            return "Problem dobavljanja kolekcije: " + usersCollectionPathInDB;
        }

        XMLResource res = (XMLResource)col.getResource(usersDocumentID );
        Users users = XML2Users(res.getContent().toString());
        for(int i = 0; i < users.getUser().size(); i++){
            if(users.getUser().get(i).getEmail().equalsIgnoreCase(email)){
                users.getUser().remove(i);
                break;
            }
        }
        String newXMLRes = users2XML(users);
        dbUtils.storeDocument(usersDocumentID, newXMLRes, col);
        return "ok";
    }

    public TUser.MyReviews getMyReviews(AuthenticationUtilities.ConnectionProperties conn, String email) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        TUser user = getUserByEmail(conn, email);
        System.out.println(user.getPendingPapersToReview());
        return user.getMyReviews();
    }

    public TUser.PendingPapersToReview getMyPendingReviews(AuthenticationUtilities.ConnectionProperties conn, String email) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        TUser user = getUserByEmail(conn, email);
        return user.getPendingPapersToReview();
    }

    public void addMyReview(String title, AuthenticationUtilities.ConnectionProperties conn) throws Exception {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TUser user = getUserByEmail(conn, loggedUser.getEmail());
        if(user.getMyReviews() == null){
            user.setMyReviews(new TUser.MyReviews());
        }
        TUser.MyReviews myReviews = user.getMyReviews();
        myReviews.getMyReviewID().add(title);
        user.setMyReviews(myReviews);
        save(conn, user);
    }

    public void addMyScientificPaper(String title, AuthenticationUtilities.ConnectionProperties conn) throws Exception {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TUser user = getUserByEmail(conn, loggedUser.getEmail());
        TUser.MyPapers myPapers = user.getMyPapers();
        myPapers.getMyScientificPaperID().add(title);
        user.setMyPapers(myPapers);
        save(conn, user);
    }

    public Users getAll(AuthenticationUtilities.ConnectionProperties conn) {
        Users allUsers = new Users();
        Collection col = null;
        try {
            dbUtils.initilizeDBserver(conn);
        } catch (ClassNotFoundException | XMLDBException | InstantiationException | IllegalAccessException e) {
            logger.error("Problem sa inicijalizovanjem baze");
            e.printStackTrace();
        }
        try {
            col = dbUtils.getOrCreateCollection(conn, usersCollectionPathInDB);

            col.setProperty(OutputKeys.INDENT, "yes");
            String[] resources = col.listResources();
            if(resources.length!=0){
                allUsers = XML2Users(col.getResource(resources[0]).getContent().toString());
            }

        } catch (XMLDBException e) {
            logger.error("Problem prilikom dobavljanj dokumenata.");
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
}
