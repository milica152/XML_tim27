
package ftn.project.xml.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="corrections">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="listItem" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="correction">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{https://github.com/milica152/XML_tim27}Correction">
 *                                     &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "corrections"
})
@XmlRootElement(name = "eMailNotification", namespace = "https://github.com/milica152/XML_tim27")
public class EMailNotification {

    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected EMailNotification.Corrections corrections;
    @XmlAttribute(name = "id", namespace = "https://github.com/milica152/XML_tim27")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the corrections property.
     * 
     * @return
     *     possible object is
     *     {@link EMailNotification.Corrections }
     *     
     */
    public EMailNotification.Corrections getCorrections() {
        return corrections;
    }

    /**
     * Sets the value of the corrections property.
     * 
     * @param value
     *     allowed object is
     *     {@link EMailNotification.Corrections }
     *     
     */
    public void setCorrections(EMailNotification.Corrections value) {
        this.corrections = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     *         &lt;element name="listItem" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="correction">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{https://github.com/milica152/XML_tim27}Correction">
     *                           &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "listItem"
    })
    public static class Corrections {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
        protected List<EMailNotification.Corrections.ListItem> listItem;
        @XmlAttribute(name = "id", namespace = "https://github.com/milica152/XML_tim27")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;

        /**
         * Gets the value of the listItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the listItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getListItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EMailNotification.Corrections.ListItem }
         * 
         * 
         */
        public List<EMailNotification.Corrections.ListItem> getListItem() {
            if (listItem == null) {
                listItem = new ArrayList<EMailNotification.Corrections.ListItem>();
            }
            return this.listItem;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
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
         *         &lt;element name="correction">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{https://github.com/milica152/XML_tim27}Correction">
         *                 &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
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
            "correction"
        })
        public static class ListItem {

            @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
            protected EMailNotification.Corrections.ListItem.Correction correction;

            /**
             * Gets the value of the correction property.
             * 
             * @return
             *     possible object is
             *     {@link EMailNotification.Corrections.ListItem.Correction }
             *     
             */
            public EMailNotification.Corrections.ListItem.Correction getCorrection() {
                return correction;
            }

            /**
             * Sets the value of the correction property.
             * 
             * @param value
             *     allowed object is
             *     {@link EMailNotification.Corrections.ListItem.Correction }
             *     
             */
            public void setCorrection(EMailNotification.Corrections.ListItem.Correction value) {
                this.correction = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{https://github.com/milica152/XML_tim27}Correction">
             *       &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Correction
                extends ftn.project.xml.model.Correction
            {

                @XmlAttribute(name = "id", namespace = "https://github.com/milica152/XML_tim27")
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                @XmlID
                @XmlSchemaType(name = "ID")
                protected String id;

                /**
                 * Gets the value of the id property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getId() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setId(String value) {
                    this.id = value;
                }

            }

        }

    }

}
