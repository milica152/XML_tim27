package ftn.project.xml.repository;

import ftn.project.xml.dto.ScientificPaperDTO;
import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.service.ScientificPaperService;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.DBUtils;
import ftn.project.xml.util.DOMParser;
import org.apache.commons.io.FileUtils;
import org.exist.xmldb.EXistResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static ftn.project.xml.templates.XUpdateTemplate.TARGET_NAMESPACE;

@Repository
public class ScientificPaperRepository {
    private static String papersCollectionPathInDB = "/db/xml/scientificPapers";
    private static String papersDocumentID = "paper.xml";

    Logger logger = LoggerFactory.getLogger(ScientificPaperRepository.class);

    @Autowired
    private DBUtils dbUtils;

    public String save(AuthenticationUtilities.ConnectionProperties conn, String paperID, String xmlRes) throws Exception {
        Collection col = null;
        dbUtils.initilizeDBserver(conn);

        try {
            col = dbUtils.getOrCreateCollection(conn, papersCollectionPathInDB);
            col.setProperty("indent", "yes");
            dbUtils.storeDocument(papersDocumentID + paperID, xmlRes, col);
            return xmlRes;

        } finally {

            // don't forget to cleanup
            if(col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                    return "Greska pri cuvanju dokumenta.";
                }
            }
        }
    }

    public ScientificPaper getByTitle(AuthenticationUtilities.ConnectionProperties conn, String paperID) throws XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ScientificPaper sp = null;
        dbUtils.initilizeDBserver(conn);

        Collection col = null;
        XMLResource res = null;

        try {
            col = DatabaseManager.getCollection(conn.uri + papersCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = (XMLResource)col.getResource(papersDocumentID + paperID);

            if(res == null) {
                System.out.println("[WARNING] Document '" + papersDocumentID+paperID + "' can not be found!");
            } else {

                JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");

                Unmarshaller unmarshaller = context.createUnmarshaller();

                ScientificPaper sPaper = (ScientificPaper) unmarshaller.unmarshal(res.getContentAsDOM());
                sp = sPaper;
            }
        } catch (XMLDBException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
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
        return sp;
    }

    public List<ScientificPaperDTO> search(AuthenticationUtilities.ConnectionProperties conn, String author, String title, String keyword) {
        List<ScientificPaperDTO> sps = new ArrayList<>();
        Collection col = null;

        try {
            dbUtils.initilizeDBserver(conn);
        } catch (ClassNotFoundException | XMLDBException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Problem sa inicijalizovanjem baze");
        }
        try {
            col = DatabaseManager.getCollection(conn.uri + papersCollectionPathInDB);
            col.setProperty(OutputKeys.INDENT, "yes");
            String[] resources = col.listResources();
            if(resources.length!=0){
                sps = doSearch(col, conn, resources, author, title, keyword);
            }

        } catch (XMLDBException e) {
            e.printStackTrace();
            System.out.println("Problem prilikom dobavljanj dokumenata.");
        }
        return sps;
    }

    private List<ScientificPaperDTO> doSearch(Collection col, AuthenticationUtilities.ConnectionProperties conn, String[] resources, String author, String title, String keyword) throws XMLDBException {
        List<ScientificPaperDTO> result = new ArrayList<>();
        XMLResource document = null;
        String xqueryExp = "";

        XQueryService xqueryService = (XQueryService) col.getService("XQueryService", "1.0");
        xqueryService.setProperty("indent", "yes");
        xqueryService.setNamespace("", TARGET_NAMESPACE);   // promeni ako ne radi sa sp!!
        for(String res : resources){

            try {
                xqueryExp = "doc(\"" + res + "\")//scientificPaper/title[contains(.,\"" + title.trim() + "\")]/text()";
                ResourceSet resultSet = xqueryService.query(xqueryExp);
                if(resultSet.getSize()>0){
                    xqueryExp = "doc(\"" + res + "\")//scientificPaper/authors/author/name[contains(.,\"" + author.trim() + "\")]/text() | //scientificPaper/authors/author/surname[contains(.,\"" + author.trim() + "\")]/text()";
                    resultSet = xqueryService.query(xqueryExp);
                    if(resultSet.getSize()>0) {
                        xqueryExp = "doc(\"" + res + "\")//scientificPaper/abstract/keywords/keyword[contains(.,\"" + keyword.trim() + "\")]/text()";
                        resultSet = xqueryService.query(xqueryExp);
                        if(resultSet.getSize()>0) {
                            ScientificPaperDTO r = getTitleAuthorsAndKeywords(res, xqueryService);
                            System.out.println(r.getTitle());
                            result.add(r);
                        }
                    }
                }

            } catch (XMLDBException e) {
                e.printStackTrace();
                System.out.println("Problem sa pretragom");
            }


        }
        return result;
    }

    private ScientificPaperDTO getTitleAuthorsAndKeywords(String res, XQueryService xQueryService) throws XMLDBException {
        ScientificPaperDTO r = new ScientificPaperDTO();

        // title
        String xQueryExp = "doc(\"" + res + "\")//scientificPaper/title/text()";
        ResourceSet resultSet = xQueryService.query(xQueryExp);
        ResourceIterator i = resultSet.getIterator();
        r.setTitle(i.nextResource().getContent().toString());

        // authors
        xQueryExp = "for $author in doc(\"" + res + "\")//scientificPaper/authors/author " +
                "return fn:concat($author/name/text(), \" \",  $author/surname/text())";
        resultSet = xQueryService.query(xQueryExp);
        i = resultSet.getIterator();
        Resource resource = null;
        System.out.println(resultSet.getSize());
        while(i.hasMoreResources()) {
            try {
                resource = i.nextResource();
                r.getAuthors().add(String.valueOf(resource.getContent()));

                System.out.println(resource.getContent());

            } finally {

                // don't forget to cleanup resources
                try {
                    ((EXistResource)resource).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }


        // keywords
        xQueryExp = "doc(\"" + res + "\")//scientificPaper/abstract/keywords/keyword/text()";
        resultSet = xQueryService.query(xQueryExp);
        i = resultSet.getIterator();
        resource = null;
        System.out.println(resultSet.getSize());

        while(i.hasMoreResources()) {
            try {
                resource = i.nextResource();
                r.getKeywords().add(String.valueOf(resource.getContent()));

                System.out.println(resource.getContent());

            } finally {

                // don't forget to cleanup resources
                try {
                    ((EXistResource)resource).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return r;
    }


}
