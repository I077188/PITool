
package com.sap.xi.basis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sap.xi.basis.global.LONGDescription;


/**
 * <p>Java class for ValueMappingQueryIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValueMappingQueryIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ValueMappingID" type="{http://sap.com/xi/BASIS}ValueMappingID" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://sap.com/xi/BASIS/Global}LONG_Description" minOccurs="0"/>
 *         &lt;element name="AdministrativeData" type="{http://sap.com/xi/BASIS}ObjectAdministrativeData" minOccurs="0"/>
 *         &lt;element name="GroupName" type="{http://sap.com/xi/BASIS/Global}LANGUAGEINDEPENDENT_MEDIUM_Name" minOccurs="0"/>
 *         &lt;element name="Representation" type="{http://sap.com/xi/BASIS}ValueMappingRepresentation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueMappingQueryIn", propOrder = {
    "valueMappingID",
    "description",
    "administrativeData",
    "groupName",
    "representation"
})
public class ValueMappingQueryIn {

    @XmlElement(name = "ValueMappingID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String valueMappingID;
    @XmlElement(name = "Description")
    protected LONGDescription description;
    @XmlElement(name = "AdministrativeData")
    protected ObjectAdministrativeData administrativeData;
    @XmlElement(name = "GroupName")
    protected String groupName;
    @XmlElement(name = "Representation")
    protected ValueMappingRepresentation representation;

    /**
     * Gets the value of the valueMappingID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueMappingID() {
        return valueMappingID;
    }

    /**
     * Sets the value of the valueMappingID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueMappingID(String value) {
        this.valueMappingID = value;
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

    /**
     * Gets the value of the groupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the value of the groupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

    /**
     * Gets the value of the representation property.
     * 
     * @return
     *     possible object is
     *     {@link ValueMappingRepresentation }
     *     
     */
    public ValueMappingRepresentation getRepresentation() {
        return representation;
    }

    /**
     * Sets the value of the representation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueMappingRepresentation }
     *     
     */
    public void setRepresentation(ValueMappingRepresentation value) {
        this.representation = value;
    }

}
