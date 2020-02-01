package ftn.project.xml.controller;

import ftn.project.xml.dto.UserLoginDTO;
import ftn.project.xml.model.TRole;
import ftn.project.xml.model.TUser;
import ftn.project.xml.service.UserService;
import ftn.project.xml.util.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<TUser> saveUser() throws Exception {
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
        return new ResponseEntity<>( userService.save(conn, user), HttpStatus.OK);
    }


    @GetMapping("/getByEmail")
    @ResponseBody
    public ResponseEntity<TUser> getUserByEmail(@RequestBody String email) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.getUserByEmail(conn, email), HttpStatus.OK);
    }

    @PostMapping("/getByEmailAndPassword")
    @ResponseBody
    public ResponseEntity<TUser> getUserByEmailAndPassword(@RequestBody UserLoginDTO email) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.getUserByEmailAndPassword(conn, email), HttpStatus.OK);
    }



}
