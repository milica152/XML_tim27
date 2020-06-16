
package ftn.project.xml.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for TUser complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TUser">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="username">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="25"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="password">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="4"/>
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="name">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value=".@."/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="myPapers">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="myScientificPaperID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="myReviews">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="myReviewID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pendingPapersToReview">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="paperToReviewID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="role" type="{https://github.com/milica152/XML_tim27}TRole"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TUser", namespace = "https://github.com/milica152/XML_tim27", propOrder = {
        "username",
        "password",
        "name",
        "surname",
        "email",
        "myPapers",
        "myReviews",
        "pendingPapersToReview",
        "role"
})
@XmlRootElement(name = "user", namespace = "https://github.com/milica152/XML_tim27")
public class TUser {

    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected String username;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected String password;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected String name;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected String surname;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected String email;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected TUser.MyPapers myPapers;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected TUser.MyReviews myReviews;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected TUser.PendingPapersToReview pendingPapersToReview;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    @XmlSchemaType(name = "string")
    protected TRole role;

    /**
     * Gets the value of the username property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the surname property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the email property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the myPapers property.
     *
     * @return
     *     possible object is
     *     {@link TUser.MyPapers }
     *
     */
    public TUser.MyPapers getMyPapers() {
        return myPapers;
    }

    /**
     * Sets the value of the myPapers property.
     *
     * @param value
     *     allowed object is
     *     {@link TUser.MyPapers }
     *
     */
    public void setMyPapers(TUser.MyPapers value) {
        this.myPapers = value;
    }

    /**
     * Gets the value of the myReviews property.
     *
     * @return
     *     possible object is
     *     {@link TUser.MyReviews }
     *
     */
    public TUser.MyReviews getMyReviews() {
        return myReviews;
    }

    /**
     * Sets the value of the myReviews property.
     *
     * @param value
     *     allowed object is
     *     {@link TUser.MyReviews }
     *
     */
    public void setMyReviews(TUser.MyReviews value) {
        this.myReviews = value;
    }

    /**
     * Gets the value of the pendingPapersToReview property.
     *
     * @return
     *     possible object is
     *     {@link TUser.PendingPapersToReview }
     *
     */
    public TUser.PendingPapersToReview getPendingPapersToReview() {
        return pendingPapersToReview;
    }

    /**
     * Sets the value of the pendingPapersToReview property.
     *
     * @param value
     *     allowed object is
     *     {@link TUser.PendingPapersToReview }
     *
     */
    public void setPendingPapersToReview(TUser.PendingPapersToReview value) {
        this.pendingPapersToReview = value;
    }

    /**
     * Gets the value of the role property.
     *
     * @return
     *     possible object is
     *     {@link TRole }
     *
     */
    public TRole getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     *
     * @param value
     *     allowed object is
     *     {@link TRole }
     *
     */
    public void setRole(TRole value) {
        this.role = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="myScientificPaperID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "myScientificPaperID"
    })
    public static class MyPapers {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected List<String> myScientificPaperID;

        /**
         * Gets the value of the myScientificPaperID property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the myScientificPaperID property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMyScientificPaperID().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         *
         *
         */
        public List<String> getMyScientificPaperID() {
            if (myScientificPaperID == null) {
                myScientificPaperID = new ArrayList<String>();
            }
            return this.myScientificPaperID;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="myReviewID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "myReviewID"
    })
    public static class MyReviews {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected List<String> myReviewID;

        /**
         * Gets the value of the myReviewID property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the myReviewID property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMyReviewID().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         *
         *
         */
        public List<String> getMyReviewID() {
            if (myReviewID == null) {
                myReviewID = new ArrayList<String>();
            }
            return this.myReviewID;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="paperToReviewID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "paperToReviewID"
    })
    public static class PendingPapersToReview {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected List<String> paperToReviewID;

        /**
         * Gets the value of the paperToReviewID property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the paperToReviewID property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPaperToReviewID().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         *
         *
         */
        public List<String> getPaperToReviewID() {
            if (paperToReviewID == null) {
                paperToReviewID = new ArrayList<String>();
            }
            return this.paperToReviewID;
        }

    }

}
