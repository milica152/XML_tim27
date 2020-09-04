package ftn.project.xml.controller;

import ftn.project.xml.service.ReviewService;
import ftn.project.xml.util.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("(hasAuthority('REVIEWER'))")
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> saveReview(@RequestBody String reviewXML) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(reviewService.save(conn, reviewXML), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('EDITOR'))")
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deleteReview(@RequestBody String title) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(reviewService.delete(conn, title), HttpStatus.OK);
    }

    @PostMapping("/findByTitle")
    @ResponseBody
    public ResponseEntity<String> getByDocumentId(@RequestBody String title) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(reviewService.getByDocumentId(conn, title), HttpStatus.OK);
    }

    @GetMapping("/findByTitleToHTML")
    @PreAuthorize("(hasAuthority('AUTHOR', 'EDITOR'))")
    @ResponseBody
    public ResponseEntity<String> getReviewByIdHTML(@RequestParam("title") String title) throws Exception {
        return new ResponseEntity<>(reviewService.transformToHTML(reviewService.getByDocumentId(AuthenticationUtilities.loadProperties(), title)), HttpStatus.OK);
    }

    @PostMapping("/findAllBySPTitle")
    @ResponseBody
    public ResponseEntity<List<String>> findAllBySPTitle(@RequestBody String title) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(reviewService.findAllBySPTitle(conn, title), HttpStatus.OK);
    }

    @PutMapping("/transformHTML")
    @ResponseBody
    public ResponseEntity transformToHtml(@RequestBody String xml) throws Exception {
        reviewService.transformToHTML(xml);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
