package ftn.project.xml.service;

import ftn.project.xml.dto.UserLoginDTO;
import ftn.project.xml.dto.UserRegisterDTO;
import ftn.project.xml.exceptions.EntityAlreadyExistsException;
import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.model.TRole;
import ftn.project.xml.model.TUser;
import ftn.project.xml.model.User;
import ftn.project.xml.repository.ScientificPaperRepository;
import ftn.project.xml.repository.UserRepository;
import ftn.project.xml.security.TokenUtils;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.Convert;
import ftn.project.xml.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Convert convert;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ScientificPaperRepository scientificPaperRepository;

    @Autowired
    DBUtils dbUtils;
    private static String usersCollectionPathInDB = "/db/xml/users";   //path kolekcije

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public TUser save(AuthenticationUtilities.ConnectionProperties conn, UserRegisterDTO user, HttpServletRequest request) throws Exception {
        String url = new URL(request.getRequestURL().toString()).getAuthority();
        dbUtils.initilizeDBserver(conn);
        Collection collection = dbUtils.getOrCreateCollection(conn, usersCollectionPathInDB);
        TUser user1 = userRepository.getUserByEmail(conn, user.getEmail());
        if(user1!= null){
            throw new EntityAlreadyExistsException("Email already taken!");
        }else{
            TUser regUser = new TUser();
            regUser.setEmail(user.getEmail());
            regUser.setPassword(passwordEncoder.encode(user.getPassword()));
            regUser.setName(user.getName());
            regUser.setSurname(user.getSurname());
            regUser.setRole(TRole.AUTHOR);

            regUser.setProfession(user.getProfession());
            TUser.MyPapers papers = new TUser.MyPapers();
            papers.setMyScientificPaperID(new ArrayList<String>());
            regUser.setMyPapers(papers);

            TUser u = userRepository.save(conn, regUser);
            return u;
        }
    }

    public String login(AuthenticationUtilities.ConnectionProperties conn, UserLoginDTO userLoginDTO) throws UsernameNotFoundException, BadCredentialsException{
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getEmail(), userLoginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        UserDetails details = userDetailsService.loadUserByUsername(userLoginDTO.getEmail());
        return tokenUtils.generateToken(details);
    }

    public TUser getUserByEmail(AuthenticationUtilities.ConnectionProperties conn, String s) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return userRepository.getUserByEmail(conn, s);
    }

    public TUser getUserByEmailAndPassword(AuthenticationUtilities.ConnectionProperties conn, UserLoginDTO userLogin) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return userRepository.getUserByEmailAndPassword(conn, userLogin.getEmail(), userLogin.getPassword());
    }

    public TUser getEditor(AuthenticationUtilities.ConnectionProperties conn) throws Exception {
        return userRepository.getEditor(conn);
    }

    public String delete(AuthenticationUtilities.ConnectionProperties conn, String email) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return userRepository.delete(conn, email);
    }

    public TUser.MyReviews getMyReviews(AuthenticationUtilities.ConnectionProperties conn) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getMyReviews(conn, user.getEmail());
    }

    public TUser.PendingPapersToReview getMyPendingReviews(AuthenticationUtilities.ConnectionProperties conn) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getMyPendingReviews(conn, user.getEmail());
    }

    public String deletePendingSP(AuthenticationUtilities.ConnectionProperties conn, String title) throws Exception {
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TUser loggedTUser = userRepository.getUserByEmail(conn, "milica@gmail.com");
        TUser.PendingPapersToReview myPapers = loggedTUser.getPendingPapersToReview();
        for(String spID : myPapers.getPaperToReviewID()){
            if(spID.equalsIgnoreCase(title)){
                myPapers.getPaperToReviewID().remove(spID);
                break;
            }
        }
        loggedTUser.setPendingPapersToReview(myPapers);
        userRepository.remove(conn, loggedTUser);
        userRepository.save(conn, loggedTUser);
        return "ok";
    }

    public String findReviewerForSP(AuthenticationUtilities.ConnectionProperties conn, String title) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sp = scientificPaperRepository.getByTitle(conn, title);
        return "";
    }

    public void pickReviewers(AuthenticationUtilities.ConnectionProperties conn, ArrayList<String> emails, String title) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        for(String reviewer : emails){
            userRepository.addPendingPaper(title,conn, reviewer);
        }
    }
    public User getLoggedUser() {
        User logged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return logged;
    }
}
