
package ftn.project.xml.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="reviewer" type="{https://github.com/milica152/XML_tim27}TAuthor"/>
 *         &lt;element name="authors">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{https://github.com/milica152/XML_tim27}author" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="questionnaire">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="item" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="question" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="answer">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="exceeds_expectations"/>
 *                                   &lt;enumeration value="meets_expectations"/>
 *                                   &lt;enumeration value="needs_improvement"/>
 *                                   &lt;enumeration value="unacceptable"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="comment_example">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="comments">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="comment" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="paperRating">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="outstanding"/>
 *               &lt;enumeration value="very_good"/>
 *               &lt;enumeration value="good"/>
 *               &lt;enumeration value="fair"/>
 *               &lt;enumeration value="poor"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="recommendation">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="accept"/>
 *               &lt;enumeration value="return"/>
 *               &lt;enumeration value="reject"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
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
    "title",
    "id",
    "reviewer",
    "authors",
    "questionnaire",
    "comments",
    "paperRating",
    "recommendation"
})
@XmlRootElement(name = "review", namespace = "https://github.com/milica152/XML_tim27")
public class Review {

    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected String title;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String id;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected TAuthor reviewer;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected Review.Authors authors;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected Review.Questionnaire questionnaire;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected Review.Comments comments;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected String paperRating;
    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected String recommendation;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
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
     * Gets the value of the reviewer property.
     * 
     * @return
     *     possible object is
     *     {@link TAuthor }
     *     
     */
    public TAuthor getReviewer() {
        return reviewer;
    }

    /**
     * Sets the value of the reviewer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAuthor }
     *     
     */
    public void setReviewer(TAuthor value) {
        this.reviewer = value;
    }

    /**
     * Gets the value of the authors property.
     * 
     * @return
     *     possible object is
     *     {@link Review.Authors }
     *     
     */
    public Review.Authors getAuthors() {
        return authors;
    }

    /**
     * Sets the value of the authors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Review.Authors }
     *     
     */
    public void setAuthors(Review.Authors value) {
        this.authors = value;
    }

    /**
     * Gets the value of the questionnaire property.
     * 
     * @return
     *     possible object is
     *     {@link Review.Questionnaire }
     *     
     */
    public Review.Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    /**
     * Sets the value of the questionnaire property.
     * 
     * @param value
     *     allowed object is
     *     {@link Review.Questionnaire }
     *     
     */
    public void setQuestionnaire(Review.Questionnaire value) {
        this.questionnaire = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link Review.Comments }
     *     
     */
    public Review.Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Review.Comments }
     *     
     */
    public void setComments(Review.Comments value) {
        this.comments = value;
    }

    /**
     * Gets the value of the paperRating property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaperRating() {
        return paperRating;
    }

    /**
     * Sets the value of the paperRating property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaperRating(String value) {
        this.paperRating = value;
    }

    /**
     * Gets the value of the recommendation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecommendation() {
        return recommendation;
    }

    /**
     * Sets the value of the recommendation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecommendation(String value) {
        this.recommendation = value;
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
     *         &lt;element ref="{https://github.com/milica152/XML_tim27}author" maxOccurs="unbounded"/>
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
        "author"
    })
    public static class Authors {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
        protected List<Author> author;

        /**
         * Gets the value of the author property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the author property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAuthor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Author }
         * 
         * 
         */
        public List<Author> getAuthor() {
            if (author == null) {
                author = new ArrayList<Author>();
            }
            return this.author;
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
     *         &lt;element name="comment" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
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
        "comment"
    })
    public static class Comments {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27")
        protected List<Review.Comments.Comment> comment;

        /**
         * Gets the value of the comment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the comment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getComment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Review.Comments.Comment }
         * 
         * 
         */
        public List<Review.Comments.Comment> getComment() {
            if (comment == null) {
                comment = new ArrayList<Review.Comments.Comment>();
            }
            return this.comment;
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
         *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
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
        public static class Comment {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "id")
            @XmlSchemaType(name = "anyURI")
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
     *         &lt;element name="item" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="question" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="answer">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;enumeration value="exceeds_expectations"/>
     *                         &lt;enumeration value="meets_expectations"/>
     *                         &lt;enumeration value="needs_improvement"/>
     *                         &lt;enumeration value="unacceptable"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="comment_example">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
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
    @XmlType(name = "", propOrder = {
        "item"
    })
    public static class Questionnaire {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
        protected List<Review.Questionnaire.Item> item;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Review.Questionnaire.Item }
         * 
         * 
         */
        public List<Review.Questionnaire.Item> getItem() {
            if (item == null) {
                item = new ArrayList<Review.Questionnaire.Item>();
            }
            return this.item;
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
         *         &lt;element name="question" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="answer">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;enumeration value="exceeds_expectations"/>
         *               &lt;enumeration value="meets_expectations"/>
         *               &lt;enumeration value="needs_improvement"/>
         *               &lt;enumeration value="unacceptable"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="comment_example">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "question",
            "answer",
            "commentExample"
        })
        public static class Item {

            @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
            protected String question;
            @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
            protected String answer;
            @XmlElement(name = "comment_example", namespace = "https://github.com/milica152/XML_tim27", required = true)
            protected Review.Questionnaire.Item.CommentExample commentExample;

            /**
             * Gets the value of the question property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getQuestion() {
                return question;
            }

            /**
             * Sets the value of the question property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setQuestion(String value) {
                this.question = value;
            }

            /**
             * Gets the value of the answer property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnswer() {
                return answer;
            }

            /**
             * Sets the value of the answer property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnswer(String value) {
                this.answer = value;
            }

            /**
             * Gets the value of the commentExample property.
             * 
             * @return
             *     possible object is
             *     {@link Review.Questionnaire.Item.CommentExample }
             *     
             */
            public Review.Questionnaire.Item.CommentExample getCommentExample() {
                return commentExample;
            }

            /**
             * Sets the value of the commentExample property.
             * 
             * @param value
             *     allowed object is
             *     {@link Review.Questionnaire.Item.CommentExample }
             *     
             */
            public void setCommentExample(Review.Questionnaire.Item.CommentExample value) {
                this.commentExample = value;
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
             *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
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
            public static class CommentExample {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "id")
                @XmlSchemaType(name = "anyURI")
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

}
