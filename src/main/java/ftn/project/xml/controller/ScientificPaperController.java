package ftn.project.xml.controller;

import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.service.ScientificPaperService;
import ftn.project.xml.util.AuthenticationUtilities;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/scientificPaper")
public class ScientificPaperController {
    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private ScientificPaperService scientificPaperService;

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveScientificPaper() throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        String xmlRes = FileUtils.readFileToString(new File("data\\test\\test_paper.xml"), StandardCharsets.UTF_8);
        return new ResponseEntity<>(scientificPaperService.save(conn, "2", xmlRes), HttpStatus.OK);
    }

    @GetMapping("/findByDocumentId/{id}")
    @ResponseBody
    public ResponseEntity<ScientificPaper> getScientificPaperById(@PathVariable("id") String id) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(scientificPaperService.getScientificPaperById(conn, id), HttpStatus.OK);
    }

}
