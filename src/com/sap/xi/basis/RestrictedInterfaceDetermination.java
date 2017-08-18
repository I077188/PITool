
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
 * <p>Java class for RestrictedInterfaceDetermination complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RestrictedInterfaceDetermination">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MasterLanguage" type="{http://sap.com/xi/BASIS/Global}LanguageCode"/>
 *         &lt;element name="AdministrativeData" type="{http://sap.com/xi/BASIS}RestrictedObjectAdministrativeData" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://sap.com/xi/BASIS/Global}LONG_Description" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="InterfaceDeterminationID" type="{http://sap.com/xi/BASIS}MessageHeaderID"/>
 *         &lt;element name="Rule" type="{http://sap.com/xi/BASIS}InterfaceDeterminationRule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SoftwareComponentVersionID" type="{http://sap.com/xi/BASIS}SoftwareComponentVersionID" minOccurs="0"/>
 *         &lt;element name="PrefixNamespaceMapping" type="{http://sap.com/xi/BASIS}PrefixNamespaceMapping" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="QualityOfService" type="{http://sap.com/xi/BASIS}QualityOfService"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictedInterfaceDetermination", propOrder = {
    "masterLanguage",
    "administrativeData",
    "description",
    "interfaceDeterminationID",
    "rule",
    "softwareComponentVersionID",
    "prefixNamespaceMapping",
    "qualityOfService"
})
public class RestrictedInterfaceDetermination {

    @XmlElement(name = "MasterLanguage", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String masterLanguage;
    @XmlElement(name = "AdministrativeData")
    protected RestrictedObjectAdministrativeData administrativeData;
    @XmlElement(name = "Description")
    protected List<LONGDescription> description;
    @XmlElement(name = "InterfaceDeterminationID", required = true)
    protected MessageHeaderID interfaceDeterminationID;
    @XmlElement(name = "Rule")
    protected List<InterfaceDeterminationRule> rule;
    @XmlElement(name = "SoftwareComponentVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String softwareComponentVersionID;
    @XmlElement(name = "PrefixNamespaceMapping")
    protected List<PrefixNamespaceMapping> prefixNamespaceMapping;
    @XmlElement(name = "QualityOfService", required = true)
    protected QualityOfService qualityOfService;

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
     * Gets the value of the interfaceDeterminationID property.
     * 
     * @return
     *     possible object is
     *     {@link MessageHeaderID }
     *     
     */
    public MessageHeaderID getInterfaceDeterminationID() {
        return interfaceDeterminationID;
    }

    /**
     * Sets the value of the interfaceDeterminationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageHeaderID }
     *     
     */
    public void setInterfaceDeterminationID(MessageHeaderID value) {
        this.interfaceDeterminationID = value;
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
     * {@link InterfaceDeterminationRule }
     * 
     * 
     */
    public List<InterfaceDeterminationRule> getRule() {
        if (rule == null) {
            rule = new ArrayList<InterfaceDeterminationRule>();
        }
        return this.rule;
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
     * Gets the value of the qualityOfService property.
     * 
     * @return
     *     possible object is
     *     {@link QualityOfService }
     *     
     */
    public QualityOfService getQualityOfService() {
        return qualityOfService;
    }

    /**
     * Sets the value of the qualityOfService property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityOfService }
     *     
     */
    public void setQualityOfService(QualityOfService value) {
        this.qualityOfService = value;
    }

}
