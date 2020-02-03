package ftn.project.xml.service;

import ftn.project.xml.dto.ScientificPaperDTO;
import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.repository.ScientificPaperRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.XMLDBException;

import java.util.List;

@Service
public class ScientificPaperService {
    private static String schemaPath = "schemas\\scientificPaper.xsd";

    @Autowired
    private ScientificPaperRepository scientificPaperRepository;


    public String save(AuthenticationUtilities.ConnectionProperties conn, String title, String xmlRes) throws Exception {
        try{
            DOMParser parser = new DOMParser();
            Document d = parser.buildDocument(xmlRes, schemaPath);
        }catch (Exception e){
            e.printStackTrace();
        }

        return scientificPaperRepository.save(conn, title, xmlRes);
    }

    public ScientificPaper getByTitle(AuthenticationUtilities.ConnectionProperties conn, String s) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return scientificPaperRepository.getByTitle(conn, s);
    }

    public List<ScientificPaperDTO> search(AuthenticationUtilities.ConnectionProperties loadProperties, String author, String title, String keyword) {
        return scientificPaperRepository.search(loadProperties,  author,  title,  keyword);
    }
}
