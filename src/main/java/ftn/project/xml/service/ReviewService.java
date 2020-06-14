package ftn.project.xml.service;

import ftn.project.xml.model.Review;
import ftn.project.xml.repository.ReviewRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.XMLDBException;

@Service
public class ReviewService {
    private static String schemaPath = "schemas\\Review.xsd";


    @Autowired
    public DOMParser domParser;

    @Autowired
    private ReviewRepository reviewRepository;


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
}
