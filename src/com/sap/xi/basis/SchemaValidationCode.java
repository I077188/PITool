
package com.sap.xi.basis;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SchemaValidationCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SchemaValidationCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Adapter Engine"/>
 *     &lt;enumeration value="No Validation"/>
 *     &lt;enumeration value="Integration Server"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SchemaValidationCode")
@XmlEnum
public enum SchemaValidationCode {

    @XmlEnumValue("Adapter Engine")
    ADAPTER_ENGINE("Adapter Engine"),
    @XmlEnumValue("No Validation")
    NO_VALIDATION("No Validation"),
    @XmlEnumValue("Integration Server")
    INTEGRATION_SERVER("Integration Server");
    private final String value;

    SchemaValidationCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SchemaValidationCode fromValue(String v) {
        for (SchemaValidationCode c: SchemaValidationCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
