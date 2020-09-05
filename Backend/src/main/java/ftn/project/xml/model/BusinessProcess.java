package ftn.project.xml.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="paperTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="status" minOccurs="1"/>
 *         &lt;complexType>
 *                  &lt;simpleContent>
 *                    &lt;extension base="&lt;https://github.com/milica152/XML_tim27>TStatusS">
 *                      &lt;anyAttribute processContents='skip' namespace=''/>
 *                    &lt;/extension>
 *                  &lt;/simpleContent>
 *                 &lt;/complexType>
 *         &lt;element name="reviewsGrade" type="{}reviewsGradeType" maxOccurs="unbounded"/>
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
    "paperTitle",
    "version",
    "status",
    "reviewsGrade"
})
@XmlRootElement(name = "businessProcess")
public class BusinessProcess {

    @XmlElement(required = true)
    protected String paperTitle;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger version;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TStatusS status;
    @XmlElement(required = true)
    protected List<ReviewsGradeType> reviewsGrade;

    /**
     * Gets the value of the paperTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaperTitle() {
        return paperTitle;
    }

    /**
     * Sets the value of the paperTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaperTitle(String value) {
        this.paperTitle = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVersion(BigInteger value) {
        this.version = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link TStatusS }
     *     
     */
    public TStatusS getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link TStatusS }
     *     
     */
    public void setStatus(TStatusS value) {
        this.status = value;
    }

    /**
     * Gets the value of the reviewsGrade property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reviewsGrade property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReviewsGrade().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReviewsGradeType }
     * 
     * 
     */
    public List<ReviewsGradeType> getReviewsGrade() {
        if (reviewsGrade == null) {
            reviewsGrade = new ArrayList<ReviewsGradeType>();
        }
        return this.reviewsGrade;
    }

    public void setReviewsGrade(List<ReviewsGradeType> reviewsGradeTypes) {
        this.reviewsGrade = reviewsGradeTypes;
    }
}

