
package ftn.project.xml.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TRole.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TRole">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="author"/>
 *     &lt;enumeration value="editor"/>
 *     &lt;enumeration value="reviewer"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TRole", namespace = "https://github.com/milica152/XML_tim27")
@XmlEnum
public enum TRole {

    @XmlEnumValue("author")
    AUTHOR("author"),
    @XmlEnumValue("editor")
    EDITOR("editor"),
    @XmlEnumValue("reviewer")
    REVIEWER("reviewer");
    private final String value;

    TRole(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TRole fromValue(String v) {
        for (TRole c: TRole.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
