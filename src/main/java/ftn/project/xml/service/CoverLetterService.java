package ftn.project.xml.service;

import ftn.project.xml.model.CoverLetter;
import ftn.project.xml.repository.CoverLetterRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.XMLDBException;

@Service
public class CoverLetterService {

    @Autowired
    private CoverLetterRepository coverLetterRepository;
    private static String schemaPath = "schemas\\coverLetter.xsd";


    public void update(AuthenticationUtilities.ConnectionProperties conn, String id) {

    }

    public String save(AuthenticationUtilities.ConnectionProperties conn, String reviewXML, String reviewID) throws Exception {

        try{
            DOMParser parser = new DOMParser();
            Document d = parser.buildDocument(reviewXML, schemaPath);
        }catch (Exception e){
            e.printStackTrace();
        }

        return coverLetterRepository.save(conn, reviewXML, reviewID);
    }

    public CoverLetter getByDocumentId(AuthenticationUtilities.ConnectionProperties conn, String id) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return coverLetterRepository.getByDocumentId(conn, id);
    }


}
