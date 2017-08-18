
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
 * <p>Java class for ReceiverDetermination complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceiverDetermination">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MasterLanguage" type="{http://sap.com/xi/BASIS/Global}LanguageCode"/>
 *         &lt;element name="AdministrativeData" type="{http://sap.com/xi/BASIS}ObjectAdministrativeData" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://sap.com/xi/BASIS/Global}LONG_Description" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ReceiverDeterminationID" type="{http://sap.com/xi/BASIS}MessageHeaderID"/>
 *         &lt;element name="Rule" type="{http://sap.com/xi/BASIS}ReceiverDeterminationRule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Include" type="{http://sap.com/xi/BASIS}ReceiverDeterminationInclude" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DynamicReceiverRule" type="{http://sap.com/xi/BASIS}ReceiverDeterminationMapping" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SoftwareComponentVersionID" type="{http://sap.com/xi/BASIS}SoftwareComponentVersionID" minOccurs="0"/>
 *         &lt;element name="PrefixNamespaceMapping" type="{http://sap.com/xi/BASIS}PrefixNamespaceMapping" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="NoReceiverBehaviour" type="{http://sap.com/xi/BASIS}NoReceiverBehaviour" minOccurs="0"/>
 *         &lt;element name="NoReceiverReceiver" type="{http://sap.com/xi/BASIS}CommunicationPartnerExtractor" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceiverDetermination", propOrder = {
    "masterLanguage",
    "administrativeData",
    "description",
    "receiverDeterminationID",
    "rule",
    "include",
    "dynamicReceiverRule",
    "softwareComponentVersionID",
    "prefixNamespaceMapping",
    "noReceiverBehaviour",
    "noReceiverReceiver"
})
public class ReceiverDetermination {

    @XmlElement(name = "MasterLanguage", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String masterLanguage;
    @XmlElement(name = "AdministrativeData")
    protected ObjectAdministrativeData administrativeData;
    @XmlElement(name = "Description")
    protected List<LONGDescription> description;
    @XmlElement(name = "ReceiverDeterminationID", required = true)
    protected MessageHeaderID receiverDeterminationID;
    @XmlElement(name = "Rule")
    protected List<ReceiverDeterminationRule> rule;
    @XmlElement(name = "Include")
    protected List<ReceiverDeterminationInclude> include;
    @XmlElement(name = "DynamicReceiverRule")
    protected List<ReceiverDeterminationMapping> dynamicReceiverRule;
    @XmlElement(name = "SoftwareComponentVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String softwareComponentVersionID;
    @XmlElement(name = "PrefixNamespaceMapping")
    protected List<PrefixNamespaceMapping> prefixNamespaceMapping;
    @XmlElement(name = "NoReceiverBehaviour")
    protected NoReceiverBehaviour noReceiverBehaviour;
    @XmlElement(name = "NoReceiverReceiver")
    protected CommunicationPartnerExtractor noReceiverReceiver;

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
     * Gets the value of the receiverDeterminationID property.
     * 
     * @return
     *     possible object is
     *     {@link MessageHeaderID }
     *     
     */
    public MessageHeaderID getReceiverDeterminationID() {
        return receiverDeterminationID;
    }

    /**
     * Sets the value of the receiverDeterminationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageHeaderID }
     *     
     */
    public void setReceiverDeterminationID(MessageHeaderID value) {
        this.receiverDeterminationID = value;
    }

    /**
     * Gets the value of the rule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReceiverDeterminationRule }
     * 
     * 
     */
    public List<ReceiverDeterminationRule> getRule() {
        if (rule == null) {
            rule = new ArrayList<ReceiverDeterminationRule>();
        }
        return this.rule;
    }

    /**
     * Gets the value of the include property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the include property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInclude().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReceiverDeterminationInclude }
     * 
     * 
     */
    public List<ReceiverDeterminationInclude> getInclude() {
        if (include == null) {
            include = new ArrayList<ReceiverDeterminationInclude>();
        }
        return this.include;
    }

    /**
     * Gets the value of the dynamicReceiverRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dynamicReceiverRule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDynamicReceiverRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReceiverDeterminationMapping }
     * 
     * 
     */
    public List<ReceiverDeterminationMapping> getDynamicReceiverRule() {
        if (dynamicReceiverRule == null) {
            dynamicReceiverRule = new ArrayList<ReceiverDeterminationMapping>();
        }
        return this.dynamicReceiverRule;
    }

    /**
     * Gets the value of the softwareComponentVersionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftwareComponentVersionID() {
        return softwareComponentVersionID;
    }

    /**
     * Sets the value of the softwareComponentVersionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftwareComponentVersionID(String value) {
        this.softwareComponentVersionID = value;
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

    /**
     * Gets the value of the noReceiverBehaviour property.
     * 
     * @return
     *     possible object is
     *     {@link NoReceiverBehaviour }
     *     
     */
    public NoReceiverBehaviour getNoReceiverBehaviour() {
        return noReceiverBehaviour;
    }

    /**
     * Sets the value of the noReceiverBehaviour property.
     * 
     * @param value
     *     allowed object is
     *     {@link NoReceiverBehaviour }
     *     
     */
    public void setNoReceiverBehaviour(NoReceiverBehaviour value) {
        this.noReceiverBehaviour = value;
    }

    /**
     * Gets the value of the noReceiverReceiver property.
     * 
     * @return
     *     possible object is
     *     {@link CommunicationPartnerExtractor }
     *     
     */
    public CommunicationPartnerExtractor getNoReceiverReceiver() {
        return noReceiverReceiver;
    }

    /**
     * Sets the value of the noReceiverReceiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunicationPartnerExtractor }
     *     
     */
    public void setNoReceiverReceiver(CommunicationPartnerExtractor value) {
        this.noReceiverReceiver = value;
    }

}
