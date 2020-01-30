package ftn.project.xml.controller;

import ftn.project.xml.model.TRole;
import ftn.project.xml.model.TUser;
import ftn.project.xml.repository.ScientificPaperRepository;
import ftn.project.xml.repository.UserRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {
    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScientificPaperRepository scientificPaperRepository;

    @GetMapping("saveUser")
    @ResponseBody
    public void saveUser() throws Exception {
        TUser user = new TUser();
        user.setEmail("email");
        user.setMyPapers(new TUser.MyPapers());
        user.setMyReviews(new TUser.MyReviews());
        user.setName("name");
        user.setPassword("pass");
        user.setPendingPapersToReview(new TUser.PendingPapersToReview());
        user.setRole(TRole.AUTHOR);
        user.setSurname("surname");
        user.setUsername("username");

        conn = AuthenticationUtilities.loadProperties();
        TUser tuser = userRepository.save(conn, user);

    }

    @GetMapping("/saveScientificPaper")
    @ResponseBody
    public void saveScientificPaper() throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        scientificPaperRepository.save(conn, "1");
    }

    @GetMapping("/getScientificPaper")
    @ResponseBody
    public void getScientificPaperById() throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        scientificPaperRepository.getScientificPaperById(conn, "1");
    }


    @GetMapping("/getUserByEmail")
    @ResponseBody
    public void getUserByEmail() throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        userRepository.getUserByEmail(conn, "email");
    }


}
