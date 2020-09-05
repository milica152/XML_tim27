
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
 *     &lt;enumeration value="accepted"/>
 *     &lt;enumeration value="rejected"/>
 *     &lt;enumeration value="withdrawn"/>
 *     &lt;enumeration value="in process"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TStatusS")
@XmlEnum
public enum TStatusS {

    @XmlEnumValue("accepted")
    ACCEPTED("accepted"),
    @XmlEnumValue("rejected")
    REJECTED("rejected"),
    @XmlEnumValue("withdrawn")
    WITHDRAWN("withdrawn"),
    @XmlEnumValue("in process")
    IN_PROCESS("in process");
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
