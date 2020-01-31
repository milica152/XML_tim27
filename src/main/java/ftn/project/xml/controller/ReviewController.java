package ftn.project.xml.controller;

import ftn.project.xml.model.Review;
import ftn.project.xml.model.TAuthor;
import ftn.project.xml.service.ReviewService;
import ftn.project.xml.util.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    @ResponseBody
    public void saveReview() throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        Review r = new Review();
        r.setAuthors(new Review.Authors());
        r.setComments(new Review.Comments());
        r.setId("review1");
        r.setPaperRating("outstanding");
        r.setQuestionnaire(new Review.Questionnaire());
        r.setRecommendation("accept");
        r.setReviewer(new TAuthor());
        r.setTitle("title1");
        reviewService.save(conn, r);
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



}
