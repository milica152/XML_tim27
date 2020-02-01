package ftn.project.xml.controller;

import ftn.project.xml.model.CoverLetter;
import ftn.project.xml.service.CoverLetterService;
import ftn.project.xml.util.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/coverLetter")
public class CoverLetterController {

    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private CoverLetterService coverLetterService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody String reviewXML) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(coverLetterService.save(conn, reviewXML, "1"), HttpStatus.OK);
    }


    @PutMapping("/update")
    @ResponseBody
    public void update(@RequestBody String reviewXML) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        coverLetterService.update(conn, reviewXML);
    }


    @PostMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") String id) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        coverLetterService.update(conn, id);
    }

    @PostMapping("/getByDocumentId/{id}")
    @ResponseBody
    public ResponseEntity<CoverLetter> getByDocumentId(@PathVariable("id") String id) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<CoverLetter>(coverLetterService.getByDocumentId(conn, id), HttpStatus.OK);
    }
}
