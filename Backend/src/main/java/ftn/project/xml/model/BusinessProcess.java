
package ftn.project.xml.model;

import java.math.BigInteger;
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
 *         &lt;element name="status" type="{}statusEnum"/>
 *         &lt;element name="reviewsGrade" type="{}reviewsGradeType"/>
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
    protected StatusEnum status;
    @XmlElement(required = true)
    protected ReviewsGradeType reviewsGrade;

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
     *     {@link StatusEnum }
     *     
     */
    public StatusEnum getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusEnum }
     *     
     */
    public void setStatus(StatusEnum value) {
        this.status = value;
    }

    /**
     * Gets the value of the reviewsGrade property.
     * 
     * @return
     *     possible object is
     *     {@link ReviewsGradeType }
     *     
     */
    public ReviewsGradeType getReviewsGrade() {
        return reviewsGrade;
    }

    /**
     * Sets the value of the reviewsGrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReviewsGradeType }
     *     
     */
    public void setReviewsGrade(ReviewsGradeType value) {
        this.reviewsGrade = value;
    }

}
