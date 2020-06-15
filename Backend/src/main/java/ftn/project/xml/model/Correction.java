
package ftn.project.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Correction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Correction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="original">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}image"/>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}chapter"/>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}paragraph"/>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}title"/>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}table"/>
 *                   &lt;element name="string" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="corrected">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}image"/>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}chapter"/>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}paragraph"/>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}title"/>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}table"/>
 *                   &lt;element name="string" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
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
@XmlType(name = "Correction", namespace = "https://github.com/milica152/XML_tim27", propOrder = {
    "original",
    "corrected"
})
@XmlSeeAlso({
    ftn.project.xml.model.EMailNotification.Corrections.ListItem.Correction.class
})
public class Correction {

    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected Correction.Original original;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected Correction.Corrected corrected;

    /**
     * Gets the value of the original property.
     * 
     * @return
     *     possible object is
     *     {@link Correction.Original }
     *     
     */
    public Correction.Original getOriginal() {
        return original;
    }

    /**
     * Sets the value of the original property.
     * 
     * @param value
     *     allowed object is
     *     {@link Correction.Original }
     *     
     */
    public void setOriginal(Correction.Original value) {
        this.original = value;
    }

    /**
     * Gets the value of the corrected property.
     * 
     * @return
     *     possible object is
     *     {@link Correction.Corrected }
     *     
     */
    public Correction.Corrected getCorrected() {
        return corrected;
    }

    /**
     * Sets the value of the corrected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Correction.Corrected }
     *     
     */
    public void setCorrected(Correction.Corrected value) {
        this.corrected = value;
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
     *       &lt;choice>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}image"/>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}chapter"/>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}paragraph"/>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}title"/>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}table"/>
     *         &lt;element name="string" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "image",
        "chapter",
        "paragraph",
        "title",
        "table",
        "string"
    })
    public static class Corrected {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Image image;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Chapter chapter;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Paragraph paragraph;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Title title;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Table table;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected String string;

        /**
         * Gets the value of the image property.
         * 
         * @return
         *     possible object is
         *     {@link Image }
         *     
         */
        public Image getImage() {
            return image;
        }

        /**
         * Sets the value of the image property.
         * 
         * @param value
         *     allowed object is
         *     {@link Image }
         *     
         */
        public void setImage(Image value) {
            this.image = value;
        }

        /**
         * Gets the value of the chapter property.
         * 
         * @return
         *     possible object is
         *     {@link Chapter }
         *     
         */
        public Chapter getChapter() {
            return chapter;
        }

        /**
         * Sets the value of the chapter property.
         * 
         * @param value
         *     allowed object is
         *     {@link Chapter }
         *     
         */
        public void setChapter(Chapter value) {
            this.chapter = value;
        }

        /**
         * Gets the value of the paragraph property.
         * 
         * @return
         *     possible object is
         *     {@link Paragraph }
         *     
         */
        public Paragraph getParagraph() {
            return paragraph;
        }

        /**
         * Sets the value of the paragraph property.
         * 
         * @param value
         *     allowed object is
         *     {@link Paragraph }
         *     
         */
        public void setParagraph(Paragraph value) {
            this.paragraph = value;
        }

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link Title }
         *     
         */
        public Title getTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link Title }
         *     
         */
        public void setTitle(Title value) {
            this.title = value;
        }

        /**
         * Gets the value of the table property.
         * 
         * @return
         *     possible object is
         *     {@link Table }
         *     
         */
        public Table getTable() {
            return table;
        }

        /**
         * Sets the value of the table property.
         * 
         * @param value
         *     allowed object is
         *     {@link Table }
         *     
         */
        public void setTable(Table value) {
            this.table = value;
        }

        /**
         * Gets the value of the string property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getString() {
            return string;
        }

        /**
         * Sets the value of the string property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setString(String value) {
            this.string = value;
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
     *       &lt;choice>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}image"/>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}chapter"/>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}paragraph"/>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}title"/>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}table"/>
     *         &lt;element name="string" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "image",
        "chapter",
        "paragraph",
        "title",
        "table",
        "string"
    })
    public static class Original {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Image image;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Chapter chapter;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Paragraph paragraph;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Title title;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Table table;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected String string;

        /**
         * Gets the value of the image property.
         * 
         * @return
         *     possible object is
         *     {@link Image }
         *     
         */
        public Image getImage() {
            return image;
        }

        /**
         * Sets the value of the image property.
         * 
         * @param value
         *     allowed object is
         *     {@link Image }
         *     
         */
        public void setImage(Image value) {
            this.image = value;
        }

        /**
         * Gets the value of the chapter property.
         * 
         * @return
         *     possible object is
         *     {@link Chapter }
         *     
         */
        public Chapter getChapter() {
            return chapter;
        }

        /**
         * Sets the value of the chapter property.
         * 
         * @param value
         *     allowed object is
         *     {@link Chapter }
         *     
         */
        public void setChapter(Chapter value) {
            this.chapter = value;
        }

        /**
         * Gets the value of the paragraph property.
         * 
         * @return
         *     possible object is
         *     {@link Paragraph }
         *     
         */
        public Paragraph getParagraph() {
            return paragraph;
        }

        /**
         * Sets the value of the paragraph property.
         * 
         * @param value
         *     allowed object is
         *     {@link Paragraph }
         *     
         */
        public void setParagraph(Paragraph value) {
            this.paragraph = value;
        }

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link Title }
         *     
         */
        public Title getTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link Title }
         *     
         */
        public void setTitle(Title value) {
            this.title = value;
        }

        /**
         * Gets the value of the table property.
         * 
         * @return
         *     possible object is
         *     {@link Table }
         *     
         */
        public Table getTable() {
            return table;
        }

        /**
         * Sets the value of the table property.
         * 
         * @param value
         *     allowed object is
         *     {@link Table }
         *     
         */
        public void setTable(Table value) {
            this.table = value;
        }

        /**
         * Gets the value of the string property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getString() {
            return string;
        }

        /**
         * Sets the value of the string property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setString(String value) {
            this.string = value;
        }

    }

}
