package ftn.project.xml.controller;

import ftn.project.xml.dto.UserLoginDTO;
import ftn.project.xml.dto.UserRegisterDTO;
import ftn.project.xml.model.TRole;
import ftn.project.xml.model.TUser;
import ftn.project.xml.service.UserService;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping(value = "/user")
public class UserController {
    private AuthenticationUtilities.ConnectionProperties conn;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<TUser> saveUser(@RequestBody UserRegisterDTO userRegisterDTO) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>( userService.save(conn, userRegisterDTO), HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TUser> login(@RequestBody UserLoginDTO userLoginDTO) throws Exception  {
        conn = AuthenticationUtilities.loadProperties();
        return userService.login(conn, userLoginDTO);
    }

    @PostMapping("/getByEmail")
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


    @GetMapping("/getEditor")
    @ResponseBody
    public ResponseEntity<TUser> getEditor() throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.getEditor(conn), HttpStatus.OK);
    }


    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestBody String email) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(userService.delete(conn, email), HttpStatus.OK);
    }

}
