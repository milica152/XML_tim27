
package ftn.project.xml.model;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ftn.project.xml.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ftn.project.xml.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TUser }
     * 
     */
    public TUser createTUser() {
        return new TUser();
    }

    /**
     * Create an instance of {@link Users }
     * 
     */
    public Users createUsers() {
        return new Users();
    }

    /**
     * Create an instance of {@link TUser.MyPapers }
     * 
     */
    public TUser.MyPapers createTUserMyPapers() {
        return new TUser.MyPapers();
    }

    /**
     * Create an instance of {@link TUser.MyReviews }
     * 
     */
    public TUser.MyReviews createTUserMyReviews() {
        return new TUser.MyReviews();
    }

    /**
     * Create an instance of {@link TUser.PendingPapersToReview }
     * 
     */
    public TUser.PendingPapersToReview createTUserPendingPapersToReview() {
        return new TUser.PendingPapersToReview();
    }

}
