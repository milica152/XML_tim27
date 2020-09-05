
package ftn.project.xml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TStatusS.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TStatusS">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="published"/>
 *     &lt;enumeration value="rejected"/>
 *     &lt;enumeration value="withdrawn"/>
 *     &lt;enumeration value="on_review"/>
 *     &lt;enumeration value="on_revision"/>
 *     &lt;enumeration value="submitted"/>
 *     &lt;enumeration value="revised"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TStatusS")
@XmlEnum
public enum TStatusS {

    @XmlEnumValue("published")
    PUBLISHED("published"),
    @XmlEnumValue("rejected")
    REJECTED("rejected"),
    @XmlEnumValue("withdrawn")
    WITHDRAWN("withdrawn"),
    @XmlEnumValue("on_review")
    ON_REVIEW("on_review"),
    @XmlEnumValue("on_revision")
    ON_REVISION("on_revision"),
    @XmlEnumValue("submitted")
    SUBMITTED("submitted"),
    @XmlEnumValue("revised")
    REVISED("revised");
    private final String value;

    TStatusS(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TStatusS fromValue(String v) {
        for (TStatusS c: TStatusS.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
