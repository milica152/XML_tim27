
package ftn.project.xml.model;

import java.math.BigInteger;
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
 *         &lt;element name="row" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="cell" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="style" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="ordinal" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="ordinal" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute ref="{https://github.com/milica152/XML_tim27}id"/>
 *       &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="height" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="measuringUnit" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "row"
})
@XmlRootElement(name = "table", namespace = "https://github.com/milica152/XML_tim27")
public class Table {

    @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
    protected List<Table.Row> row;
    @XmlAttribute(name = "id", namespace = "https://github.com/milica152/XML_tim27")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "width")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger width;
    @XmlAttribute(name = "height")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger height;
    @XmlAttribute(name = "measuringUnit")
    protected String measuringUnit;

    /**
     * Gets the value of the row property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the row property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRow().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Table.Row }
     * 
     * 
     */
    public List<Table.Row> getRow() {
        if (row == null) {
            row = new ArrayList<Table.Row>();
        }
        return this.row;
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
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWidth(BigInteger value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHeight(BigInteger value) {
        this.height = value;
    }

    /**
     * Gets the value of the measuringUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasuringUnit() {
        return measuringUnit;
    }

    /**
     * Sets the value of the measuringUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasuringUnit(String value) {
        this.measuringUnit = value;
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
     *         &lt;element name="cell" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="style" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="ordinal" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="ordinal" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "cell"
    })
    public static class Row {

        @XmlElement(namespace = "https://github.com/milica152/XML_tim27", required = true)
        protected List<Table.Row.Cell> cell;
        @XmlAttribute(name = "ordinal")
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger ordinal;

        /**
         * Gets the value of the cell property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cell property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCell().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Table.Row.Cell }
         * 
         * 
         */
        public List<Table.Row.Cell> getCell() {
            if (cell == null) {
                cell = new ArrayList<Table.Row.Cell>();
            }
            return this.cell;
        }

        /**
         * Gets the value of the ordinal property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getOrdinal() {
            return ordinal;
        }

        /**
         * Sets the value of the ordinal property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setOrdinal(BigInteger value) {
            this.ordinal = value;
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
         *       &lt;attribute name="style" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="ordinal" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
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
        public static class Cell {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "style")
            protected String style;
            @XmlAttribute(name = "ordinal")
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger ordinal;

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
             * Gets the value of the style property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStyle() {
                return style;
            }

            /**
             * Sets the value of the style property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStyle(String value) {
                this.style = value;
            }

            /**
             * Gets the value of the ordinal property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getOrdinal() {
                return ordinal;
            }

            /**
             * Sets the value of the ordinal property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setOrdinal(BigInteger value) {
                this.ordinal = value;
            }

        }

    }

}
