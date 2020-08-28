package ftn.project.xml.controller;

import ftn.project.xml.dto.UserLoginDTO;
import ftn.project.xml.dto.UserRegisterDTO;
import ftn.project.xml.exceptions.EntityAlreadyExistsException;
import ftn.project.xml.model.TUser;
import ftn.project.xml.service.UserService;
import ftn.project.xml.util.AuthenticationUtilities;
import org.apache.jena.base.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {
    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @PreAuthorize("!(hasAuthority('AUTHOR') or hasAuthority('REVIEWER') or hasAuthority('EDITOR'))")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO, HttpServletRequest request) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        try{
            return new ResponseEntity<>(userService.save(conn, userRegisterDTO, request), HttpStatus.OK);
        }catch(EntityAlreadyExistsException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        try {
            return new ResponseEntity<>(userService.login(conn, userLoginDTO), HttpStatus.OK);
        }
        catch(UsernameNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/getByEmail")
    @PreAuthorize("!(hasAuthority('AUTHOR') or hasAuthority('REVIEWER') or hasAuthority('EDITOR'))")
    public ResponseEntity<TUser> getUserByEmail(@RequestBody String email) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.getUserByEmail(conn, email), HttpStatus.OK);
    }

    @PostMapping("/getByEmailAndPassword")
    @PreAuthorize("!(hasAuthority('AUTHOR') or hasAuthority('REVIEWER') or hasAuthority('EDITOR'))")
    public ResponseEntity<TUser> getUserByEmailAndPassword(@RequestBody UserLoginDTO email) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.getUserByEmailAndPassword(conn, email), HttpStatus.OK);
    }

    @GetMapping("/getEditor")
    @ResponseBody
    public ResponseEntity<TUser> getEditor() throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.getEditor(conn), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('REVIEWER'))")
    @PostMapping("/getMyReviews")
    @ResponseBody
    public ResponseEntity<TUser.MyReviews> getMyReviews() throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.getMyReviews(conn), HttpStatus.OK);
    }


    @PreAuthorize("(hasAuthority('AUTHOR') or hasAuthority('REVIEWER') or hasAuthority('EDITOR'))")
    @PostMapping("/getMyPendingReviews")
    @ResponseBody
    public ResponseEntity<TUser.PendingPapersToReview> getMyPendingReviews() throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.getMyPendingReviews(conn), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('EDITOR'))")
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestBody String email) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.delete(conn, email), HttpStatus.OK);
    }



    @PreAuthorize("(hasAuthority('REVIEWER'))")
    @PostMapping("/deletePendingSP")
    @ResponseBody
    public ResponseEntity<String> deletePendingSP(@RequestBody String title) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.deletePendingSP(conn, title), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('EDITOR'))")
    @PostMapping("/findReviewerForSP")
    @ResponseBody
    public ResponseEntity<String> findReviewerForSP(@RequestBody String title) throws Exception {   // treba da vrati vise reviewera
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.findReviewerForSP(conn, title), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('EDITOR'))")
    @PostMapping("/pickReviewers/{title}")
    @ResponseBody
    public ResponseEntity pickReviewers(@RequestBody ArrayList<String> emails, @PathVariable String title) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        userService.pickReviewers(conn, emails, title);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
