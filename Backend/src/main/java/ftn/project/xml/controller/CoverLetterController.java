package ftn.project.xml.controller;

import ftn.project.xml.service.CoverLetterService;
import ftn.project.xml.util.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/coverLetter")
public class CoverLetterController {

    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private CoverLetterService coverLetterService;

    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody String reviewXML, @RequestParam("title") String title) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(coverLetterService.save(conn, reviewXML, title), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('EDITOR'))")
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestBody String title) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(coverLetterService.delete(conn, title), HttpStatus.OK);
    }

    @PostMapping("/getByDocumentId")
    @ResponseBody
    public ResponseEntity<String> getByDocumentId(@RequestBody String title) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(coverLetterService.getByDocumentId(conn, title), HttpStatus.OK);
    }

    @GetMapping("/getByDocumentIdToHTML")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @ResponseBody
    public ResponseEntity<String> getByDocumentIdHTML(@RequestParam("title") String title) throws Exception {
        return new ResponseEntity<>(coverLetterService.transformToHTML(coverLetterService.getByDocumentId(AuthenticationUtilities.loadProperties(), title)), HttpStatus.OK);
    }

    @PutMapping("/transformHTML")
    @ResponseBody
    public ResponseEntity transformToHtml(@RequestBody String xml) throws Exception {
        coverLetterService.transformToHTML(xml);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
