package ftn.project.xml.controller;

import ftn.project.xml.model.Review;
import ftn.project.xml.service.ReviewService;
import ftn.project.xml.util.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> saveReview(@RequestBody String reviewXML) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(reviewService.save(conn, reviewXML, "1"), HttpStatus.OK);
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateReview(@RequestBody String reviewXML) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        reviewService.update(conn, reviewXML);
    }


    @PostMapping("/delete/{id}")
    @ResponseBody
    public void deleteReview(@PathVariable("id") String id) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        reviewService.update(conn, id);
    }

    @PostMapping("/getByDocumentId/{id}")
    @ResponseBody
    public ResponseEntity<Review> getByDocumentId(@PathVariable("id") String id) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<Review>(reviewService.getByDocumentId(conn, id), HttpStatus.OK);
    }

    @PutMapping("/transformHTML")
    @ResponseBody
    public ResponseEntity transformToHtml(@RequestBody String xml) throws Exception {
        reviewService.transformToHTML(xml);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
