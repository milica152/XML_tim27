package ftn.project.xml.util;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.io.File;
import java.io.IOException;

public class DOMParser  implements ErrorHandler {

        public Document buildDocument(String filePath, String schemaPath) throws SAXException, ParserConfigurationException, IOException {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(this);
            Document document;
            document = builder.parse(new File( filePath ));

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
                System.out.println("[INFO] File parsed with no errors.");
            return document;
        }


    @Override
    public void error(SAXParseException err) throws SAXParseException {
        System.out.println("error");
        err.printStackTrace();
        throw err;
    }

    @Override
    public void fatalError(SAXParseException err) throws SAXException {
        System.out.println("big error");
        err.printStackTrace();
        throw err;
    }

    @Override
    public void warning(SAXParseException err) throws SAXParseException {
        System.out.println("warning");
        err.printStackTrace();
    }
}
