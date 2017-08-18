
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
 * <p>Java class for ReceiverAgreement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceiverAgreement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MasterLanguage" type="{http://sap.com/xi/BASIS/Global}LanguageCode"/>
 *         &lt;element name="AdministrativeData" type="{http://sap.com/xi/BASIS}ObjectAdministrativeData" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://sap.com/xi/BASIS/Global}LONG_Description" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ReceiverAgreementID" type="{http://sap.com/xi/BASIS}MessageHeaderID"/>
 *         &lt;element name="CommunicationChannel" type="{http://sap.com/xi/BASIS}CommunicationChannelID" minOccurs="0"/>
 *         &lt;element name="SoftwareComponentVersion" type="{http://sap.com/xi/BASIS}SoftwareComponentVersionID" minOccurs="0"/>
 *         &lt;element name="SchemaValidationIndicator" type="{http://sap.com/xi/BASIS/Global}Indicator" minOccurs="0"/>
 *         &lt;element name="AdapterSpecificAttribute" type="{http://sap.com/xi/BASIS}GenericProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AdapterSpecificTableAttribute" type="{http://sap.com/xi/BASIS}GenericPropertyTable" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="HeaderMapping" type="{http://sap.com/xi/BASIS}HeaderMapping" minOccurs="0"/>
 *         &lt;element name="PrefixNamespaceMapping" type="{http://sap.com/xi/BASIS}PrefixNamespaceMapping" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceiverAgreement", propOrder = {
    "masterLanguage",
    "administrativeData",
    "description",
    "receiverAgreementID",
    "communicationChannel",
    "softwareComponentVersion",
    "schemaValidationIndicator",
    "adapterSpecificAttribute",
    "adapterSpecificTableAttribute",
    "headerMapping",
    "prefixNamespaceMapping"
})
public class ReceiverAgreement {

    @XmlElement(name = "MasterLanguage", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String masterLanguage;
    @XmlElement(name = "AdministrativeData")
    protected ObjectAdministrativeData administrativeData;
    @XmlElement(name = "Description")
    protected List<LONGDescription> description;
    @XmlElement(name = "ReceiverAgreementID", required = true)
    protected MessageHeaderID receiverAgreementID;
    @XmlElement(name = "CommunicationChannel")
    protected CommunicationChannelID communicationChannel;
    @XmlElement(name = "SoftwareComponentVersion")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String softwareComponentVersion;
    @XmlElement(name = "SchemaValidationIndicator")
    protected Boolean schemaValidationIndicator;
    @XmlElement(name = "AdapterSpecificAttribute")
    protected List<GenericProperty> adapterSpecificAttribute;
    @XmlElement(name = "AdapterSpecificTableAttribute")
    protected List<GenericPropertyTable> adapterSpecificTableAttribute;
    @XmlElement(name = "HeaderMapping")
    protected HeaderMapping headerMapping;
    @XmlElement(name = "PrefixNamespaceMapping")
    protected List<PrefixNamespaceMapping> prefixNamespaceMapping;

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
     * Gets the value of the receiverAgreementID property.
     * 
     * @return
     *     possible object is
     *     {@link MessageHeaderID }
     *     
     */
    public MessageHeaderID getReceiverAgreementID() {
        return receiverAgreementID;
    }

    /**
     * Sets the value of the receiverAgreementID property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageHeaderID }
     *     
     */
    public void setReceiverAgreementID(MessageHeaderID value) {
        this.receiverAgreementID = value;
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
     * Gets the value of the schemaValidationIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSchemaValidationIndicator() {
        return schemaValidationIndicator;
    }

    /**
     * Sets the value of the schemaValidationIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSchemaValidationIndicator(Boolean value) {
        this.schemaValidationIndicator = value;
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
     * Gets the value of the headerMapping property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderMapping }
     *     
     */
    public HeaderMapping getHeaderMapping() {
        return headerMapping;
    }

    /**
     * Sets the value of the headerMapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderMapping }
     *     
     */
    public void setHeaderMapping(HeaderMapping value) {
        this.headerMapping = value;
    }

    /**
     * Gets the value of the prefixNamespaceMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prefixNamespaceMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrefixNamespaceMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrefixNamespaceMapping }
     * 
     * 
     */
    public List<PrefixNamespaceMapping> getPrefixNamespaceMapping() {
        if (prefixNamespaceMapping == null) {
            prefixNamespaceMapping = new ArrayList<PrefixNamespaceMapping>();
        }
        return this.prefixNamespaceMapping;
    }

}
