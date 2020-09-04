package ftn.project.xml.repository;

import ftn.project.xml.model.BusinessProcess;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import org.exist.xmldb.EXistResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

@Repository
public class BusinessProcessRepository {
    private static String bProcessCollectionPathInDB = "/db/xml/businessProcesses";
    private static String bProcessDocumentID = "bProcess.xml";
    Logger logger = LoggerFactory.getLogger(ScientificPaperRepository.class);

    @Autowired
    private DBUtils dbUtils;


    public String save(BusinessProcess businessProcess, String title) throws JAXBException, IOException, ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        String xmlResource = BProcess2XML(businessProcess);
        AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();
        Collection col = null;
        dbUtils.initilizeDBserver(conn);

        try {
            col = dbUtils.getOrCreateCollection(conn, bProcessCollectionPathInDB);
            col.setProperty("indent", "yes");
            dbUtils.storeDocument(bProcessDocumentID + title, xmlResource, col);
            return "ok";

        } catch (XMLDBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // don't forget to cleanup
            if(col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return "error";
    }

    public BusinessProcess getByScientificPaperTitle(String title) throws XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException, JAXBException, IOException {
        AuthenticationUtilities.ConnectionProperties conn =  AuthenticationUtilities.loadProperties();
        dbUtils.initilizeDBserver(conn);

        Collection col = null;
        XMLResource res = null;

        try {
            col = DatabaseManager.getCollection(conn.uri + bProcessCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = (XMLResource)col.getResource(bProcessDocumentID + title);


        } catch (XMLDBException e) {
            e.printStackTrace();
        } finally {
            //don't forget to clean up!

            if(res != null) {
                try {
                    ((EXistResource)res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }

            if(col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        if(res == null) {
            logger.warn("Document '" + bProcessDocumentID+title + "' can not be found!");
            return null;
        }
        String xmlResource = (String) res.getContent();
        BusinessProcess bp = XML2BProcess(xmlResource);
        return bp;

    }


    public static String BProcess2XML(BusinessProcess businessProcess) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");
        OutputStream os = new ByteArrayOutputStream();

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(businessProcess, os);
        String userXml = os.toString();
        return userXml.substring(userXml.indexOf('\n') + 1);
    }

    private static BusinessProcess XML2BProcess(String xmlContent) throws JAXBException {
        BusinessProcess result;
        StringReader reader = new StringReader(xmlContent);

        JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        result = (BusinessProcess) unmarshaller.unmarshal(reader);

        return result;
    }


}
