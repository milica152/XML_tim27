package ftn.project.xml.service;

import ftn.project.xml.dto.UserLoginDTO;
import ftn.project.xml.dto.UserRegisterDTO;
import ftn.project.xml.exceptions.EntityAlreadyExistsException;
import ftn.project.xml.model.*;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.net.URL;
import java.util.*;
import java.util.List;

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
    private BusinessProcessService businessProcessService;

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
            papers.setMyScientificPaperID(new ArrayList<>());
            regUser.setMyPapers(papers);

            TUser.PendingPapersToReview pendingPapersToReview = new TUser.PendingPapersToReview();
            pendingPapersToReview.setPaperToReviewID(new ArrayList<>());
            regUser.setPendingPapersToReview(pendingPapersToReview);

            TUser.MyReviews myReviews = new TUser.MyReviews();
            myReviews.setMyReviewID(new ArrayList<>());
            regUser.setMyReviews(myReviews);

            TUser u = userRepository.save(conn, regUser);
            return u;
        }
    }

    public String login(AuthenticationUtilities.ConnectionProperties conn, UserLoginDTO userLoginDTO) throws UsernameNotFoundException, BadCredentialsException, ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
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

    public String delete(AuthenticationUtilities.ConnectionProperties conn, String email) throws Exception {
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
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TUser user = userRepository.getUserByEmail(conn, loggedUser.getEmail());
        TUser.PendingPapersToReview myPapers = user.getPendingPapersToReview();
        for(String spID : myPapers.getPaperToReviewID()){
            if(spID.equalsIgnoreCase(title)){
                myPapers.getPaperToReviewID().remove(spID);
                break;
            }
        }

        user.setPendingPapersToReview(myPapers);
        userRepository.delete(conn, user.getEmail());
        userRepository.save(conn, user);
        return "ok";
    }

    private HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public List<String> findReviewersForSP(AuthenticationUtilities.ConnectionProperties conn, String title) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException {
        String sp = scientificPaperRepository.getByTitle(conn, title);
        ScientificPaper mainSP =XML2SciPep(sp);

        HashMap<String, Integer> result = new HashMap<>();
        Users allUsers = userRepository.getAll(conn);

        // initialize map
        for(TUser user : allUsers.getUser()){
            result.put(user.getEmail(), 0);
            List<String> mySciPepTitles = scientificPaperRepository.getMyPapers(conn, user.getEmail());
            for(String myPaperTitle : mySciPepTitles){
                if(!myPaperTitle.equalsIgnoreCase(title)){     // ako ja nisam autor ovog rada, mogu biti reviewer
                    String myPaperContent = scientificPaperRepository.getByTitle(conn, myPaperTitle);
                    ScientificPaper myPaper =XML2SciPep(myPaperContent);
                    for(ScientificPaper.Metadata.Keywords.Keyword keyword : myPaper.getMetadata().getKeywords().getKeyword()){
                        for(ScientificPaper.Metadata.Keywords.Keyword mainKeyword : mainSP.getMetadata().getKeywords().getKeyword()){
                            if(mainKeyword.getValue().equalsIgnoreCase(keyword.getValue())){
                                result.put(user.getEmail(), result.get(user.getEmail()) + 1);
                            }
                        }
                    }
                }else{         // this is my paper, i can't be reviewer
                    result.remove(user.getEmail());
                    break;
                }
            }
        }

        result = sortByValue(result);
        List<String> emails = new ArrayList<>();
        for(String email : result.keySet()){
            emails.add(email);
        }

        List<String> emailsReversed = new ArrayList<>();
        for(int i = emails.size()-1; i>=0; i--){
            emailsReversed.add(emails.get(i));
        }

        if(emailsReversed.size()<10){
            return emailsReversed;
        }else {
            return emailsReversed.subList(0, 10);
        }

    }

    private static ScientificPaper XML2SciPep(String xmlContent) throws JAXBException {
        ScientificPaper result;
        StringReader reader = new StringReader(xmlContent);

        JAXBContext context = JAXBContext.newInstance("ftn.project.xml.model");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        result = (ScientificPaper) unmarshaller.unmarshal(reader);

        return result;
    }

    public void pickReviewers(AuthenticationUtilities.ConnectionProperties conn, ArrayList<String> emails, String title) throws Exception {
        for(String reviewer : emails){
            TUser user = userRepository.getUserByEmail(conn, reviewer);
            TUser.PendingPapersToReview myPapers = user.getPendingPapersToReview();

            if(myPapers == null){
                myPapers = new TUser.PendingPapersToReview();
            }

            myPapers.getPaperToReviewID().add(title);
            user.setPendingPapersToReview(myPapers);
            userRepository.delete(conn, reviewer);
            userRepository.save(conn, user);
        }
        BusinessProcess businessProcess = businessProcessService.findByScientificPaperTitle(title);
        businessProcess.setStatus(StatusEnum.ON_REVIEW);
        businessProcessService.save(businessProcess);
    }

}
