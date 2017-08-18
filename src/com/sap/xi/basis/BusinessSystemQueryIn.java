
package com.sap.xi.basis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.sap.xi.basis.global.LONGDescription;


/**
 * <p>Java class for BusinessSystemQueryIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessSystemQueryIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BusinessSystemID" type="{http://sap.com/xi/BASIS}CommunicationComponentID" minOccurs="0"/>
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
@XmlType(name = "BusinessSystemQueryIn", propOrder = {
    "businessSystemID",
    "description",
    "administrativeData"
})
public class BusinessSystemQueryIn {

    @XmlElement(name = "BusinessSystemID")
    protected CommunicationComponentID businessSystemID;
    @XmlElement(name = "Description")
    protected LONGDescription description;
    @XmlElement(name = "AdministrativeData")
    protected ObjectAdministrativeData administrativeData;

    /**
     * Gets the value of the businessSystemID property.
     * 
     * @return
     *     possible object is
     *     {@link CommunicationComponentID }
     *     
     */
    public CommunicationComponentID getBusinessSystemID() {
        return businessSystemID;
    }

    /**
     * Sets the value of the businessSystemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunicationComponentID }
     *     
     */
    public void setBusinessSystemID(CommunicationComponentID value) {
        this.businessSystemID = value;
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
