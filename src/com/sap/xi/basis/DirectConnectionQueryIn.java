
package com.sap.xi.basis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.sap.xi.basis.global.LONGDescription;


/**
 * <p>Java class for DirectConnectionQueryIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DirectConnectionQueryIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DirectConnectionID" type="{http://sap.com/xi/BASIS}MessageHeaderID" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://sap.com/xi/BASIS/Global}LONG_Description" minOccurs="0"/>
 *         &lt;element name="AdministrativeData" type="{http://sap.com/xi/BASIS}ObjectAdministrativeData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DirectConnectionQueryIn", propOrder = {
    "directConnectionID",
    "description",
    "administrativeData"
})
public class DirectConnectionQueryIn {

    @XmlElement(name = "DirectConnectionID")
    protected MessageHeaderID directConnectionID;
    @XmlElement(name = "Description")
    protected LONGDescription description;
    @XmlElement(name = "AdministrativeData")
    protected ObjectAdministrativeData administrativeData;

    /**
     * Gets the value of the directConnectionID property.
     * 
     * @return
     *     possible object is
     *     {@link MessageHeaderID }
     *     
     */
    public MessageHeaderID getDirectConnectionID() {
        return directConnectionID;
    }

    /**
     * Sets the value of the directConnectionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageHeaderID }
     *     
     */
    public void setDirectConnectionID(MessageHeaderID value) {
        this.directConnectionID = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link LONGDescription }
     *     
     */
    public LONGDescription getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link LONGDescription }
     *     
     */
    public void setDescription(LONGDescription value) {
        this.description = value;
    }

    /**
     * Gets the value of the administrativeData property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectAdministrativeData }
     *     
     */
    public ObjectAdministrativeData getAdministrativeData() {
        return administrativeData;
    }

    /**
     * Sets the value of the administrativeData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectAdministrativeData }
     *     
     */
    public void setAdministrativeData(ObjectAdministrativeData value) {
        this.administrativeData = value;
    }

}
