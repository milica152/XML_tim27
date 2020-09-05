package ftn.project.xml.controller;

import com.sun.codemodel.JForEach;
import ftn.project.xml.service.ReviewService;
import ftn.project.xml.util.AuthenticationUtilities;
import org.checkerframework.checker.tainting.qual.PolyTainted;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasAnyAuthority('EDITOR','REVIEWER')")
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

    @PostMapping("/transformToHTML")
    @ResponseBody
    public ResponseEntity<String> transformXMLtoHTML(@RequestBody String xml) throws Exception {
        System.out.println(xml);
        String html = reviewService.transformToHTML(xml);
        System.out.println(html);
        return new ResponseEntity<>(html, HttpStatus.OK);
    }

    @PostMapping("/allHTMLReviews")
    @ResponseBody
    public ResponseEntity<String> getAllHTMLReviews(@RequestBody List<String> reviews) throws Exception {
        String finalHTML = "";
        for(String review:  reviews){
            String html = reviewService.transformToHTML(review);
            finalHTML = finalHTML.concat(html);
            finalHTML = finalHTML.concat("<br/>");
        }
        return new ResponseEntity<String>(finalHTML, HttpStatus.OK);
    }

    @PostMapping("/allHtmlReviewsReviewerless")
    @ResponseBody
    public ResponseEntity<String> getAllHTMLReviewerlessReviews(@RequestBody List<String> reviews) throws Exception {
        String finalHTML = "";
        for(String review:  reviews){
            org.jsoup.nodes.Document doc = Jsoup.parse(review);
            Elements selector = doc.select("reviewer");

            for (Element element : selector) {
                element.remove();
            }

            String html = reviewService.transformToHTML(review);
            finalHTML = finalHTML.concat(html);
            finalHTML = finalHTML.concat("<br/>");
        }
        return new ResponseEntity<String>(finalHTML, HttpStatus.OK);
    }



    @PostMapping("/rejectReview")
    @PreAuthorize("hasAnyAuthority('EDITOR','REVIEWER')")
    public ResponseEntity<String> rejectReview(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(reviewService.rejectReview(AuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }

}
