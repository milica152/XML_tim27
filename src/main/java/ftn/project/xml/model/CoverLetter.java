
package ftn.project.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
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
 *         &lt;element ref="{https://github.com/milica152/XML_tim27}title"/>
 *         &lt;element ref="{https://github.com/milica152/XML_tim27}author"/>
 *         &lt;element name="chapters">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{https://github.com/milica152/XML_tim27}TChapter">
 *                 &lt;choice>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}image"/>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}table"/>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}list"/>
 *                   &lt;element name="codeBlock">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="referencePointer">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/choice>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{https://github.com/milica152/XML_tim27}scientificPaper"/>
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
    "title",
    "author",
    "chapters",
    "scientificPaper"
})
@XmlRootElement(name = "coverLetter", namespace = "https://github.com/milica152/XML_tim27")
public class CoverLetter {

    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected Title title;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected Author author;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected CoverLetter.Chapters chapters;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected ScientificPaper scientificPaper;
    @XmlAttribute(name = "id", namespace = "https://github.com/milica152/XML_tim27")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

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
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link Author }
     *     
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link Author }
     *     
     */
    public void setAuthor(Author value) {
        this.author = value;
    }

    /**
     * Gets the value of the chapters property.
     * 
     * @return
     *     possible object is
     *     {@link CoverLetter.Chapters }
     *     
     */
    public CoverLetter.Chapters getChapters() {
        return chapters;
    }

    /**
     * Sets the value of the chapters property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoverLetter.Chapters }
     *     
     */
    public void setChapters(CoverLetter.Chapters value) {
        this.chapters = value;
    }

    /**
     * Gets the value of the scientificPaper property.
     * 
     * @return
     *     possible object is
     *     {@link ScientificPaper }
     *     
     */
    public ScientificPaper getScientificPaper() {
        return scientificPaper;
    }

    /**
     * Sets the value of the scientificPaper property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScientificPaper }
     *     
     */
    public void setScientificPaper(ScientificPaper value) {
        this.scientificPaper = value;
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
     *     &lt;extension base="{https://github.com/milica152/XML_tim27}TChapter">
     *       &lt;choice>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}image"/>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}table"/>
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}list"/>
     *         &lt;element name="codeBlock">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="referencePointer">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/choice>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "image",
        "table",
        "list",
        "codeBlock",
        "referencePointer"
    })
    public static class Chapters
        extends TChapter
    {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Image image;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected Table table;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected List list;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected CoverLetter.Chapters.CodeBlock codeBlock;
        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected CoverLetter.Chapters.ReferencePointer referencePointer;

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
         * Gets the value of the list property.
         * 
         * @return
         *     possible object is
         *     {@link List }
         *     
         */
        public List getList() {
            return list;
        }

        /**
         * Sets the value of the list property.
         * 
         * @param value
         *     allowed object is
         *     {@link List }
         *     
         */
        public void setList(List value) {
            this.list = value;
        }

        /**
         * Gets the value of the codeBlock property.
         * 
         * @return
         *     possible object is
         *     {@link CoverLetter.Chapters.CodeBlock }
         *     
         */
        public CoverLetter.Chapters.CodeBlock getCodeBlock() {
            return codeBlock;
        }

        /**
         * Sets the value of the codeBlock property.
         * 
         * @param value
         *     allowed object is
         *     {@link CoverLetter.Chapters.CodeBlock }
         *     
         */
        public void setCodeBlock(CoverLetter.Chapters.CodeBlock value) {
            this.codeBlock = value;
        }

        /**
         * Gets the value of the referencePointer property.
         * 
         * @return
         *     possible object is
         *     {@link CoverLetter.Chapters.ReferencePointer }
         *     
         */
        public CoverLetter.Chapters.ReferencePointer getReferencePointer() {
            return referencePointer;
        }

        /**
         * Sets the value of the referencePointer property.
         * 
         * @param value
         *     allowed object is
         *     {@link CoverLetter.Chapters.ReferencePointer }
         *     
         */
        public void setReferencePointer(CoverLetter.Chapters.ReferencePointer value) {
            this.referencePointer = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class CodeBlock {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "id", namespace = "https://github.com/milica152/XML_tim27")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlID
            @XmlSchemaType(name = "ID")
            protected String id;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
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

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class ReferencePointer {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "id", namespace = "https://github.com/milica152/XML_tim27")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlID
            @XmlSchemaType(name = "ID")
            protected String id;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
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

        }

    }

}
