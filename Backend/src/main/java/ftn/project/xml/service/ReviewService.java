package ftn.project.xml.service;

import ftn.project.xml.model.*;
import ftn.project.xml.repository.ReviewRepository;
import ftn.project.xml.repository.UserRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import ftn.project.xml.util.DOMParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewService {
    private static String schemaPath = "backend\\src\\main\\resources\\static\\schemas\\Review.xsd";
    private static String reviewsCollectionPathInDB = "/db/xml/reviews";
    private static String reviewDocumentID = "review.xml";

    @Autowired
    public DOMParser domParser;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessProcessService businessProcessService;

    @Autowired
    private DBUtils dbUtils;

    Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Value("${review.XSLPath}")
    private String xslPath;

    @Value("${review.XHTMLPath}")
    private String htmlPath;

    public String save(AuthenticationUtilities.ConnectionProperties conn, String reviewXML) throws Exception {
        Document d = null;
        String title = null;
        try{
            d = domParser.buildDocument(reviewXML, schemaPath);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(d != null){
            NodeList nl = d.getElementsByTagName("title");
            title = nl.item(0).getTextContent();
            nl.item(0).setTextContent(generateReviewID(conn, title));
        }else{
            return "error";
        }
        String newXMLRes = domParser.DOMToXML(d);
        String generatedID = generateReviewID(conn, title);

        String xmlRes = reviewRepository.save(conn, newXMLRes, generatedID.replaceAll(" ", ""), title);

        // change business process
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BusinessProcess businessProcess = businessProcessService.findByScientificPaperTitle(title);
        for(ReviewsGradeType reviewsGradeType: businessProcess.getReviewsGrade()){
            if(reviewsGradeType.getEmail().equalsIgnoreCase(user.getEmail())){
                reviewsGradeType.setGrade(Grade.SUBMITTED);
            }
        }

        businessProcessService.save(businessProcess);
        return xmlRes;
    }

    private String generateReviewID(AuthenticationUtilities.ConnectionProperties conn, String title) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        String id = "";
        int i = 0;
        boolean stop = false;

        dbUtils.initilizeDBserver(conn);
        Collection col = null;
        col = dbUtils.getOrCreateCollection(conn, reviewsCollectionPathInDB);
        col.setProperty(OutputKeys.INDENT, "yes");
        String[] resources = col.listResources();
         while(!stop){
             int counter = 0;
             for(String res : resources){
                 if(!res.equalsIgnoreCase(reviewDocumentID + title + "Review" +  i)){
                     counter++;
                 }
             }
             if(counter == resources.length){
                 id = title+ " Review "  + i;
                 stop = true;
             }

             i ++;
         }

        return id;
    }

    public String getByDocumentId(AuthenticationUtilities.ConnectionProperties conn, String id) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return reviewRepository.getByDocumentId(conn, id);
    }

    public String transformToHTML(String xml) throws TransformerException, IOException {
        //TODO: dodaj proveru koji tip korisnika zeli da uradi transformaciju (da se ukloni autor ako treba itd)

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslStream = new StreamSource(new File(xslPath));
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer(xslStream);
        } catch (TransformerConfigurationException e) {
            System.out.println("Error while creating XSLT transformer object.");
        }

        int number = Objects.requireNonNull(new File(htmlPath).list()).length;
        String outputFile = htmlPath + "reviewHTML" + number +".html";

        StreamSource in = new StreamSource(new StringReader(xml));
        StreamResult out = new StreamResult(new File(outputFile));
        assert transformer != null;
        transformer.transform(in, out);
        BufferedReader br = new BufferedReader(new FileReader(outputFile));
        String html = IOUtils.toString(br);
        return html;

    }

    public String delete(AuthenticationUtilities.ConnectionProperties conn, String title) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return reviewRepository.delete(conn, title);
    }

    public List<String> findAllBySPTitle(AuthenticationUtilities.ConnectionProperties conn, String title) throws SAXException, ParserConfigurationException, IOException, TransformerException {
        return reviewRepository.findAllBySPTitle(conn, title);
    }

    public String rejectReview(AuthenticationUtilities.ConnectionProperties loadProperties, String title) throws Exception {
        BusinessProcess businessProcess = businessProcessService.findByScientificPaperTitle(title);
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TUser tUser = userRepository.getUserByEmail(loadProperties, user.getEmail());
        tUser.getPendingPapersToReview().getPaperToReviewID().remove(title);
        for(ReviewsGradeType reviewsGradeType: businessProcess.getReviewsGrade()){
            if(reviewsGradeType.getEmail().equalsIgnoreCase(user.getEmail())){
                reviewsGradeType.setGrade(Grade.REJECTED);
            }
        }
        businessProcessService.save(businessProcess);  // update
        userRepository.save(loadProperties, tUser);
        return "You successfully rejected reviewing the paper " + title;
    }
}
