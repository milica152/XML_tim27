package ftn.project.xml.service;


import ftn.project.xml.dto.UserLoginDTO;
import ftn.project.xml.model.TUser;
import ftn.project.xml.repository.UserRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public TUser save(AuthenticationUtilities.ConnectionProperties conn, TUser user) throws Exception {
        return userRepository.save(conn, user);
    }

    public TUser getUserByEmail(AuthenticationUtilities.ConnectionProperties conn, String s) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return userRepository.getUserByEmail(conn, s);
    }

    public TUser getUserByEmailAndPassword(AuthenticationUtilities.ConnectionProperties conn, UserLoginDTO userLogin) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return userRepository.getUserByEmailAndPassword(conn, userLogin.getEmail(), userLogin.getPassword());
    }
}
