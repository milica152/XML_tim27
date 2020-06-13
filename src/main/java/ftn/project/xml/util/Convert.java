package ftn.project.xml.util;

import ftn.project.xml.dto.UserRegisterDTO;
import ftn.project.xml.model.TRole;
import ftn.project.xml.model.TUser;
import org.springframework.stereotype.Service;

@Service
public class Convert {
    public TUser userDTOtoTUser(UserRegisterDTO userRegisterDTO) {
        TUser user = new TUser();
        user.setEmail(userRegisterDTO.getEmail());
        user.setSurname(userRegisterDTO.getSurname());
        user.setUsername(userRegisterDTO.getUsername());
        user.setName(userRegisterDTO.getName());
        user.setPassword(userRegisterDTO.getPassword());

        user.setMyPapers(new TUser.MyPapers());
        user.setMyReviews(new TUser.MyReviews());
        user.setPendingPapersToReview(new TUser.PendingPapersToReview());
        user.setRole(TRole.AUTHOR);
            return user;
    }
}
