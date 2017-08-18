
package com.sap.xi.basis;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ValueMappingReadIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValueMappingReadIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReadContext" type="{http://sap.com/xi/BASIS}ReadContextCode" minOccurs="0"/>
 *         &lt;element name="ValueMappingID" type="{http://sap.com/xi/BASIS}ValueMappingID" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueMappingReadIn", propOrder = {
    "readContext",
    "valueMappingID"
})
public class ValueMappingReadIn {

    @XmlElement(name = "ReadContext", defaultValue = "User")
    protected ReadContextCode readContext;
    @XmlElement(name = "ValueMappingID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected List<String> valueMappingID;

    /**
     * Gets the value of the readContext property.
     * 
     * @return
     *     possible object is
     *     {@link ReadContextCode }
     *     
     */
    public ReadContextCode getReadContext() {
        return readContext;
    }

    /**
     * Sets the value of the readContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReadContextCode }
     *     
     */
    public void setReadContext(ReadContextCode value) {
        this.readContext = value;
    }

    /**
     * Gets the value of the valueMappingID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueMappingID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueMappingID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getValueMappingID() {
        if (valueMappingID == null) {
            valueMappingID = new ArrayList<String>();
        }
        return this.valueMappingID;
    }

}
