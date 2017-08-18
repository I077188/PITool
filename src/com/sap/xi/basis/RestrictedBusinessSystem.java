
package com.sap.xi.basis;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sap.xi.basis.global.LONGDescription;


/**
 * <p>Java class for RestrictedBusinessSystem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RestrictedBusinessSystem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MasterLanguage" type="{http://sap.com/xi/BASIS/Global}LanguageCode"/>
 *         &lt;element name="AdministrativeData" type="{http://sap.com/xi/BASIS}RestrictedObjectAdministrativeData" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://sap.com/xi/BASIS/Global}LONG_Description" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="BusinessSystemID" type="{http://sap.com/xi/BASIS}CommunicationComponentID"/>
 *         &lt;element name="AdditionalIdentifier" type="{http://sap.com/xi/BASIS}CommunicationComponentAdditionalIdentifier" minOccurs="0"/>
 *         &lt;element name="CacheNotification" type="{http://sap.com/xi/BASIS}CacheNotification" minOccurs="0"/>
 *         &lt;element name="AssignedUser" type="{http://sap.com/xi/BASIS}UserID" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictedBusinessSystem", propOrder = {
    "masterLanguage",
    "administrativeData",
    "description",
    "businessSystemID",
    "additionalIdentifier",
    "cacheNotification",
    "assignedUser"
})
public class RestrictedBusinessSystem {

    @XmlElement(name = "MasterLanguage", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String masterLanguage;
    @XmlElement(name = "AdministrativeData")
    protected RestrictedObjectAdministrativeData administrativeData;
    @XmlElement(name = "Description")
    protected List<LONGDescription> description;
    @XmlElement(name = "BusinessSystemID", required = true)
    protected CommunicationComponentID businessSystemID;
    @XmlElement(name = "AdditionalIdentifier")
    protected CommunicationComponentAdditionalIdentifier additionalIdentifier;
    @XmlElement(name = "CacheNotification")
    protected CacheNotification cacheNotification;
    @XmlElement(name = "AssignedUser")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected List<String> assignedUser;

    /**
     * Gets the value of the masterLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMasterLanguage() {
        return masterLanguage;
    }

    /**
     * Sets the value of the masterLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMasterLanguage(String value) {
        this.masterLanguage = value;
    }

    /**
     * Gets the value of the administrativeData property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictedObjectAdministrativeData }
     *     
     */
    public RestrictedObjectAdministrativeData getAdministrativeData() {
        return administrativeData;
    }

    /**
     * Sets the value of the administrativeData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictedObjectAdministrativeData }
     *     
     */
    public void setAdministrativeData(RestrictedObjectAdministrativeData value) {
        this.administrativeData = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LONGDescription }
     * 
     * 
     */
    public List<LONGDescription> getDescription() {
        if (description == null) {
            description = new ArrayList<LONGDescription>();
        }
        return this.description;
    }

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
     * Gets the value of the additionalIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link CommunicationComponentAdditionalIdentifier }
     *     
     */
    public CommunicationComponentAdditionalIdentifier getAdditionalIdentifier() {
        return additionalIdentifier;
    }

    /**
     * Sets the value of the additionalIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunicationComponentAdditionalIdentifier }
     *     
     */
    public void setAdditionalIdentifier(CommunicationComponentAdditionalIdentifier value) {
        this.additionalIdentifier = value;
    }

    /**
     * Gets the value of the cacheNotification property.
     * 
     * @return
     *     possible object is
     *     {@link CacheNotification }
     *     
     */
    public CacheNotification getCacheNotification() {
        return cacheNotification;
    }

    /**
     * Sets the value of the cacheNotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link CacheNotification }
     *     
     */
    public void setCacheNotification(CacheNotification value) {
        this.cacheNotification = value;
    }

    /**
     * Gets the value of the assignedUser property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assignedUser property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssignedUser().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAssignedUser() {
        if (assignedUser == null) {
            assignedUser = new ArrayList<String>();
        }
        return this.assignedUser;
    }

}
