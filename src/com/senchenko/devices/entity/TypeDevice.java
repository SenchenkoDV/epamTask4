//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.05 at 04:40:19 PM MSK 
//


package com.senchenko.devices.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TypeDevice.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TypeDevice">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Multimedia"/>
 *     &lt;enumeration value="IO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TypeDevice")
@XmlEnum
public enum TypeDevice {

    @XmlEnumValue("Multimedia")
    MULTIMEDIA("Multimedia"),
    IO("IO");
    private final String value;

    TypeDevice(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeDevice fromValue(String v) {
        for (TypeDevice c: TypeDevice.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
