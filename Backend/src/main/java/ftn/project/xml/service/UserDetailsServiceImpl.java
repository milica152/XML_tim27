package ftn.project.xml.service;

import ftn.project.xml.model.TUser;
import ftn.project.xml.model.User;
import ftn.project.xml.repository.UserRepository;
import ftn.project.xml.util.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private AuthenticationUtilities.ConnectionProperties conn;

  @Autowired
  private UserRepository userRepository;

  @lombok.SneakyThrows
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    TUser tUser = null;
    User user = null;
    AuthenticationUtilities.ConnectionProperties conn = null;
    try {
      conn = AuthenticationUtilities.loadProperties();
    } catch (IOException e) {
      e.printStackTrace();
    }
    ;
    try {
      tUser = userRepository.getUserByEmail(conn, email);
      if(tUser!=null){
        user = new User(tUser);
        }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (user == null) {
      throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
    } else {
    	return user;
    }
  }

}
