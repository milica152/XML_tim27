package ftn.project.xml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for grade.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="grade">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="accepted"/>
 *     &lt;enumeration value="rejected"/>
 *     &lt;enumeration value="submitted"/>
 *     &lt;enumeration value="assigned"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "grade")
@XmlEnum
public enum Grade {

    @XmlEnumValue("accepted")
    ACCEPTED("accepted"),
    @XmlEnumValue("rejected")
    REJECTED("rejected"),
    @XmlEnumValue("submitted")
    SUBMITTED("submitted"),
    @XmlEnumValue("assigned")
    ASSIGNED("assigned");
    private final String value;

    Grade(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Grade fromValue(String v) {
        for (Grade c: Grade.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}