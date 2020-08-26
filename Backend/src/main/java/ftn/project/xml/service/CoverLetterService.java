package ftn.project.xml.service;

import ftn.project.xml.model.CoverLetter;
import ftn.project.xml.repository.CoverLetterRepository;
import ftn.project.xml.repository.ScientificPaperRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DOMParser;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.XMLDBException;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Objects;

@Service
public class CoverLetterService {


    @Autowired
    public DOMParser domParser;

    @Autowired
    private CoverLetterRepository coverLetterRepository;
    private static String schemaPath = "Backend\\src\\main\\resources\\static\\schemas\\coverLetter.xsd";

    @Value("${coverLetter.XSLPath}")
    private String xslPath;

    @Value("${coverLetter.XHTMLPath}")
    private String htmlPath;

    @Autowired
    private ScientificPaperRepository scientificPaperRepository;

    public String save(AuthenticationUtilities.ConnectionProperties conn, String reviewXML, String title) throws Exception {

        if(!(title==null || title.isEmpty())){
            String paper = scientificPaperRepository.getByTitle(conn, title);
            if(paper==null || paper.isEmpty()){
                return "You must add scientific paper first!";
            }else{
                coverLetterRepository.save(conn, reviewXML, title);
            }

        }else{
            return "You must add scientific paper first!";
        }
        return "Cover letter added!";

    }

    public String getByDocumentId(AuthenticationUtilities.ConnectionProperties conn, String id) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return coverLetterRepository.getByDocumentId(conn, id);
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
        String outputFile = htmlPath + "coverLetterHTML" + number +".html";

        StreamSource in = new StreamSource(new StringReader(xml));
        StreamResult out = new StreamResult(new File(outputFile));
        assert transformer != null;
        transformer.transform(in, out);
        BufferedReader br = new BufferedReader(new FileReader(outputFile));
        String html = IOUtils.toString(br);
        return html;


    }


    public String delete(AuthenticationUtilities.ConnectionProperties conn, String title) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return coverLetterRepository.delete(conn, title);
    }
}
