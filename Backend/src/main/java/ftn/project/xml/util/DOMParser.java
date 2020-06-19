package ftn.project.xml.util;


import ftn.project.xml.repository.ScientificPaperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.io.*;


@Component
public class DOMParser  implements ErrorHandler {
    Logger logger = LoggerFactory.getLogger(DOMParser.class);


    public Document buildDocument(String xmlResource, String schemaPath) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(this);
        Document document;
        InputStream targetStream = new ByteArrayInputStream(xmlResource.getBytes());
        document = builder.parse(targetStream);


        Schema schema = null;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory sFactory = SchemaFactory.newInstance(language);
            schema = sFactory.newSchema(new File(schemaPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Validator validator = schema.newValidator();
        validator.setErrorHandler(this);


        validator.validate(new DOMSource(document));
        if (document != null)
            logger.info("File parsed with no errors.");
        return document;
    }

    @Override
    public void error(SAXParseException err) throws SAXParseException {
        logger.error("Error with parsing.");
        logger.trace(err.toString());
        throw err;
    }

    @Override
    public void fatalError(SAXParseException err) throws SAXException {
        logger.error("Fatal Error with parsing.");
        logger.trace(err.toString());
        throw err;
    }

    @Override
    public void warning(SAXParseException err) throws SAXParseException {
        logger.error("Error with parsing.");
        logger.trace(err.toString());
    }


    public String DOMToXML(Document document) throws TransformerException {
        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(document), new StreamResult(sw));
        return sw.toString();


    }
}
