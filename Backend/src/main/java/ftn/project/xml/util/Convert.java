package ftn.project.xml.util;

import ftn.project.xml.dto.MetadataDTO;
import ftn.project.xml.dto.UserRegisterDTO;
import ftn.project.xml.model.TRole;
import ftn.project.xml.model.TUser;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public String metadataToJSONFormat(List<MetadataDTO> metadata){
        String result = "";
        result += "{\n\r";

        for(MetadataDTO metadataDTO: metadata){
            result += "\t\"" + metadataDTO.getPredicate() + "\" : \"" + metadataDTO.getObject() + "\",\n\r";
        }
        result = result.substring(0,result.length()-3);
        result += "\n\r";
        result += "}\n\r";

        //System.out.println(result);
        return result;
    }
}
