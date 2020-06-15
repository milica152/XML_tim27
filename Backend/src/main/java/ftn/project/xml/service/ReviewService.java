package ftn.project.xml.service;

import ftn.project.xml.model.Review;
import ftn.project.xml.repository.ReviewRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.XMLDBException;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringReader;
import java.util.Objects;

@Service
public class ReviewService {
    private static String schemaPath = "schemas\\Review.xsd";


    @Autowired
    public DOMParser domParser;

    @Autowired
    private ReviewRepository reviewRepository;

    @Value("${review.XSLPath}")
    private String xslPath;

    @Value("${review.XHTMLPath}")
    private String htmlPath;


    public void update(AuthenticationUtilities.ConnectionProperties conn, String id) {

    }

    public String save(AuthenticationUtilities.ConnectionProperties conn, String reviewXML, String reviewID) throws Exception {

        try{
            Document d = domParser.buildDocument(reviewXML, schemaPath);
        }catch (Exception e){
            e.printStackTrace();
        }

        return reviewRepository.save(conn, reviewXML, reviewID);
    }

    public Review getByDocumentId(AuthenticationUtilities.ConnectionProperties conn, String id) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return reviewRepository.getByDocumentId(conn, id);
    }

    public void transformToHTML(String xml) throws TransformerException {
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
        System.out.println("The generated HTML file is:" + outputFile);

    }
}