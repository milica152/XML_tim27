package ftn.project.xml.service;


import ftn.project.xml.dto.UserLoginDTO;
import ftn.project.xml.dto.UserRegisterDTO;
import ftn.project.xml.model.TUser;
import ftn.project.xml.repository.UserRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Convert convert;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public TUser save(AuthenticationUtilities.ConnectionProperties conn, UserRegisterDTO userRegisterDTO) throws Exception {
        TUser user = convert.userDTOtoTUser(userRegisterDTO);
        return userRepository.save(conn, user);
    }

    public ResponseEntity<TUser> login(AuthenticationUtilities.ConnectionProperties conn, UserLoginDTO userLoginDTO) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        TUser user = getUserByEmailAndPassword(conn, userLoginDTO);

        if (user != null) {
            if (userLoginDTO.getPassword().equals(user.getPassword())) {
                logger.info("User " + user.getUsername() + " logged in!");
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
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
}
