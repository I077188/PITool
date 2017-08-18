
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
 * <p>Java class for RestrictedSenderAgreement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RestrictedSenderAgreement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MasterLanguage" type="{http://sap.com/xi/BASIS/Global}LanguageCode"/>
 *         &lt;element name="AdministrativeData" type="{http://sap.com/xi/BASIS}RestrictedObjectAdministrativeData" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://sap.com/xi/BASIS/Global}LONG_Description" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SenderAgreementID" type="{http://sap.com/xi/BASIS}MessageHeaderID"/>
 *         &lt;element name="CommunicationChannel" type="{http://sap.com/xi/BASIS}CommunicationChannelID"/>
 *         &lt;element name="SoftwareComponentVersion" type="{http://sap.com/xi/BASIS}SoftwareComponentVersionID" minOccurs="0"/>
 *         &lt;element name="SchemaValidation" type="{http://sap.com/xi/BASIS}SchemaValidationCode" minOccurs="0"/>
 *         &lt;element name="AdapterSpecificAttribute" type="{http://sap.com/xi/BASIS}GenericProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AdapterSpecificTableAttribute" type="{http://sap.com/xi/BASIS}GenericPropertyTable" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "RestrictedSenderAgreement", propOrder = {
    "masterLanguage",
    "administrativeData",
    "description",
    "senderAgreementID",
    "communicationChannel",
    "softwareComponentVersion",
    "schemaValidation",
    "adapterSpecificAttribute",
    "adapterSpecificTableAttribute",
    "assignedUser"
})
public class RestrictedSenderAgreement {

    @XmlElement(name = "MasterLanguage", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String masterLanguage;
    @XmlElement(name = "AdministrativeData")
    protected RestrictedObjectAdministrativeData administrativeData;
    @XmlElement(name = "Description")
    protected List<LONGDescription> description;
    @XmlElement(name = "SenderAgreementID", required = true)
    protected MessageHeaderID senderAgreementID;
    @XmlElement(name = "CommunicationChannel", required = true)
    protected CommunicationChannelID communicationChannel;
    @XmlElement(name = "SoftwareComponentVersion")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String softwareComponentVersion;
    @XmlElement(name = "SchemaValidation")
    protected SchemaValidationCode schemaValidation;
    @XmlElement(name = "AdapterSpecificAttribute")
    protected List<GenericProperty> adapterSpecificAttribute;
    @XmlElement(name = "AdapterSpecificTableAttribute")
    protected List<GenericPropertyTable> adapterSpecificTableAttribute;
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
     * Gets the value of the senderAgreementID property.
     * 
     * @return
     *     possible object is
     *     {@link MessageHeaderID }
     *     
     */
    public MessageHeaderID getSenderAgreementID() {
        return senderAgreementID;
    }

    /**
     * Sets the value of the senderAgreementID property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageHeaderID }
     *     
     */
    public void setSenderAgreementID(MessageHeaderID value) {
        this.senderAgreementID = value;
    }

    /**
     * Gets the value of the communicationChannel property.
     * 
     * @return
     *     possible object is
     *     {@link CommunicationChannelID }
     *     
     */
    public CommunicationChannelID getCommunicationChannel() {
        return communicationChannel;
    }

    /**
     * Sets the value of the communicationChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunicationChannelID }
     *     
     */
    public void setCommunicationChannel(CommunicationChannelID value) {
        this.communicationChannel = value;
    }

    /**
     * Gets the value of the softwareComponentVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftwareComponentVersion() {
        return softwareComponentVersion;
    }

    /**
     * Sets the value of the softwareComponentVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftwareComponentVersion(String value) {
        this.softwareComponentVersion = value;
    }

    /**
     * Gets the value of the schemaValidation property.
     * 
     * @return
     *     possible object is
     *     {@link SchemaValidationCode }
     *     
     */
    public SchemaValidationCode getSchemaValidation() {
        return schemaValidation;
    }

    /**
     * Sets the value of the schemaValidation property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemaValidationCode }
     *     
     */
    public void setSchemaValidation(SchemaValidationCode value) {
        this.schemaValidation = value;
    }

    /**
     * Gets the value of the adapterSpecificAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adapterSpecificAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdapterSpecificAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GenericProperty }
     * 
     * 
     */
    public List<GenericProperty> getAdapterSpecificAttribute() {
        if (adapterSpecificAttribute == null) {
            adapterSpecificAttribute = new ArrayList<GenericProperty>();
        }
        return this.adapterSpecificAttribute;
    }

    /**
     * Gets the value of the adapterSpecificTableAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adapterSpecificTableAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdapterSpecificTableAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GenericPropertyTable }
     * 
     * 
     */
    public List<GenericPropertyTable> getAdapterSpecificTableAttribute() {
        if (adapterSpecificTableAttribute == null) {
            adapterSpecificTableAttribute = new ArrayList<GenericPropertyTable>();
        }
        return this.adapterSpecificTableAttribute;
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
