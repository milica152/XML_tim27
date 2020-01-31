package ftn.project.xml.service;

import ftn.project.xml.model.Review;
import ftn.project.xml.repository.ReviewRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    public void update(AuthenticationUtilities.ConnectionProperties conn, String id) {

    }

    public void save(AuthenticationUtilities.ConnectionProperties conn, Review r) {

    }
}
