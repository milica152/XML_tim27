package ftn.project.xml.repository;

import ftn.project.xml.model.TUser;

public class UserRepository {

    private static String usersPath = "/db/xml/users";
    private static String documentPath = "dbUsers.xml";


    public TUser save(TUser user){
        return new TUser();
    }

    public TUser update(TUser user){
        return new TUser();
    }

    public TUser delete(TUser user){
        return new TUser();
    }

    public TUser getByUsername(TUser user){
        return new TUser();
    }


}
