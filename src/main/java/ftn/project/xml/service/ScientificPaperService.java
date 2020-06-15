package ftn.project.xml.service;

import ftn.project.xml.dto.MetadataDTO;
import ftn.project.xml.dto.ScientificPaperDTO;
import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.repository.ScientificPaperRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DOMParser;
import ftn.project.xml.util.MetadataExtractor;
import ftn.project.xml.util.RDFAuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.XMLDBException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ScientificPaperService {
    private static String schemaPath = "schemas\\scientificPaper.xsd";

    @Autowired
    public DOMParser domParser;

    @Autowired
    private ScientificPaperRepository scientificPaperRepository;

    @Autowired
    private MetadataExtractor metadataExtractor;

    public String save(AuthenticationUtilities.ConnectionProperties conn, String xmlRes) throws Exception {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Document d = domParser.buildDocument(xmlRes, schemaPath);
            NodeList nl = d.getElementsByTagName("title");
            String title = nl.item(0).getTextContent();
            //System.out.println(title);
            // pokreni bussiness process

            // popuni sp metapodacima
            NodeList metadata = d.getElementsByTagName("metadata");

            // list of authors and keywords
            NodeList authors = d.getElementsByTagName("authors");
            NodeList keywords = d.getElementsByTagName("keywords");

            // date published
            Element datePublished = d.createElement("datePublished");
            datePublished.setTextContent(df.format(new Date()));
            datePublished.setAttribute("property", "published");    // dodati rdf podatak na property

            // status
            Element status = d.createElement("status");
            status.setAttribute("property", "spStatus");      // dodati rdf podatak na property
            status.setTextContent("in process");

            // about
            Element about = d.createElement("about");
            d.getDocumentElement().setAttribute("about", title);


            metadata.item(0).insertBefore(datePublished, keywords.item(0));
            metadata.item(0).insertBefore(status, datePublished);

            ByteArrayOutputStream metadataStream = new ByteArrayOutputStream();
            String newSciPap = domParser.DOMToXML(d);

            metadataExtractor.extractMetadata(new ByteArrayInputStream(newSciPap.getBytes()), metadataStream);
            String extractedMetadata = new String(metadataStream.toByteArray());
            System.out.println(extractedMetadata);

            // saving to RDF store
            scientificPaperRepository.saveMetadata(extractedMetadata);
            scientificPaperRepository.save(conn, title, xmlRes);

            return "ok";

        }catch (Exception e){
            e.printStackTrace();
        }
        return "error";
    }

    public ScientificPaper getByTitle(AuthenticationUtilities.ConnectionProperties conn, String s) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return scientificPaperRepository.getByTitle(conn, s);
    }

    public List<ScientificPaperDTO> search(AuthenticationUtilities.ConnectionProperties loadProperties, String author, String title, String keyword) {
        return scientificPaperRepository.search(loadProperties,  author,  title,  keyword);
    }

    public String delete(String title, AuthenticationUtilities.ConnectionProperties loadProperties) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return scientificPaperRepository.delete(loadProperties, title);
    }

    public List<MetadataDTO> getMetadata(RDFAuthenticationUtilities.RDFConnectionProperties properties, String title) {
        return scientificPaperRepository.getMetadata(properties, title);
    }
}
