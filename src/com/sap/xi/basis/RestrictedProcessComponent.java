
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
 * <p>Java class for RestrictedProcessComponent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RestrictedProcessComponent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MasterLanguage" type="{http://sap.com/xi/BASIS/Global}LanguageCode"/>
 *         &lt;element name="AdministrativeData" type="{http://sap.com/xi/BASIS}RestrictedObjectAdministrativeData" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://sap.com/xi/BASIS/Global}LONG_Description" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ProcessComponentID" type="{http://sap.com/xi/BASIS}ProcessComponentID"/>
 *         &lt;element name="RepositoryProcessComponent" type="{http://sap.com/xi/BASIS}DesignObjectID"/>
 *         &lt;element name="CrossReferencedParty" type="{http://sap.com/xi/BASIS}CommunicationPartyID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CrossReferencedBusinessSystem" type="{http://sap.com/xi/BASIS}CommunicationComponentID" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictedProcessComponent", propOrder = {
    "masterLanguage",
    "administrativeData",
    "description",
    "processComponentID",
    "repositoryProcessComponent",
    "crossReferencedParty",
    "crossReferencedBusinessSystem"
})
public class RestrictedProcessComponent {

    @XmlElement(name = "MasterLanguage", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String masterLanguage;
    @XmlElement(name = "AdministrativeData")
    protected RestrictedObjectAdministrativeData administrativeData;
    @XmlElement(name = "Description")
    protected List<LONGDescription> description;
    @XmlElement(name = "ProcessComponentID", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String processComponentID;
    @XmlElement(name = "RepositoryProcessComponent", required = true)
    protected DesignObjectID repositoryProcessComponent;
    @XmlElement(name = "CrossReferencedParty")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected List<String> crossReferencedParty;
    @XmlElement(name = "CrossReferencedBusinessSystem")
    protected List<CommunicationComponentID> crossReferencedBusinessSystem;

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
     * Gets the value of the processComponentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessComponentID() {
        return processComponentID;
    }

    /**
     * Sets the value of the processComponentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessComponentID(String value) {
        this.processComponentID = value;
    }

    /**
     * Gets the value of the repositoryProcessComponent property.
     * 
     * @return
     *     possible object is
     *     {@link DesignObjectID }
     *     
     */
    public DesignObjectID getRepositoryProcessComponent() {
        return repositoryProcessComponent;
    }

    /**
     * Sets the value of the repositoryProcessComponent property.
     * 
     * @param value
     *     allowed object is
     *     {@link DesignObjectID }
     *     
     */
    public void setRepositoryProcessComponent(DesignObjectID value) {
        this.repositoryProcessComponent = value;
    }

    /**
     * Gets the value of the crossReferencedParty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the crossReferencedParty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCrossReferencedParty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCrossReferencedParty() {
        if (crossReferencedParty == null) {
            crossReferencedParty = new ArrayList<String>();
        }
        return this.crossReferencedParty;
    }

    /**
     * Gets the value of the crossReferencedBusinessSystem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the crossReferencedBusinessSystem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCrossReferencedBusinessSystem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationComponentID }
     * 
     * 
     */
    public List<CommunicationComponentID> getCrossReferencedBusinessSystem() {
        if (crossReferencedBusinessSystem == null) {
            crossReferencedBusinessSystem = new ArrayList<CommunicationComponentID>();
        }
        return this.crossReferencedBusinessSystem;
    }

}
