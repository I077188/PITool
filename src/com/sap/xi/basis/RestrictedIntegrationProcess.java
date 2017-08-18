
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
 * <p>Java class for RestrictedIntegrationProcess complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RestrictedIntegrationProcess">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MasterLanguage" type="{http://sap.com/xi/BASIS/Global}LanguageCode"/>
 *         &lt;element name="AdministrativeData" type="{http://sap.com/xi/BASIS}RestrictedObjectAdministrativeData" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://sap.com/xi/BASIS/Global}LONG_Description" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="IntegrationProcessID" type="{http://sap.com/xi/BASIS}CommunicationComponentID"/>
 *         &lt;element name="RepositoryIntegrationProcess" type="{http://sap.com/xi/BASIS}DesignObjectID"/>
 *         &lt;element name="ProcessParameters" type="{http://sap.com/xi/BASIS}ConfigurableParameters" minOccurs="0"/>
 *         &lt;element name="AdditionalIdentifier" type="{http://sap.com/xi/BASIS}CommunicationComponentAdditionalIdentifier" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictedIntegrationProcess", propOrder = {
    "masterLanguage",
    "administrativeData",
    "description",
    "integrationProcessID",
    "repositoryIntegrationProcess",
    "processParameters",
    "additionalIdentifier"
})
public class RestrictedIntegrationProcess {

    @XmlElement(name = "MasterLanguage", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String masterLanguage;
    @XmlElement(name = "AdministrativeData")
    protected RestrictedObjectAdministrativeData administrativeData;
    @XmlElement(name = "Description")
    protected List<LONGDescription> description;
    @XmlElement(name = "IntegrationProcessID", required = true)
    protected CommunicationComponentID integrationProcessID;
    @XmlElement(name = "RepositoryIntegrationProcess", required = true)
    protected DesignObjectID repositoryIntegrationProcess;
    @XmlElement(name = "ProcessParameters")
    protected ConfigurableParameters processParameters;
    @XmlElement(name = "AdditionalIdentifier")
    protected List<CommunicationComponentAdditionalIdentifier> additionalIdentifier;

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
     * Gets the value of the integrationProcessID property.
     * 
     * @return
     *     possible object is
     *     {@link CommunicationComponentID }
     *     
     */
    public CommunicationComponentID getIntegrationProcessID() {
        return integrationProcessID;
    }

    /**
     * Sets the value of the integrationProcessID property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunicationComponentID }
     *     
     */
    public void setIntegrationProcessID(CommunicationComponentID value) {
        this.integrationProcessID = value;
    }

    /**
     * Gets the value of the repositoryIntegrationProcess property.
     * 
     * @return
     *     possible object is
     *     {@link DesignObjectID }
     *     
     */
    public DesignObjectID getRepositoryIntegrationProcess() {
        return repositoryIntegrationProcess;
    }

    /**
     * Sets the value of the repositoryIntegrationProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link DesignObjectID }
     *     
     */
    public void setRepositoryIntegrationProcess(DesignObjectID value) {
        this.repositoryIntegrationProcess = value;
    }

    /**
     * Gets the value of the processParameters property.
     * 
     * @return
     *     possible object is
     *     {@link ConfigurableParameters }
     *     
     */
    public ConfigurableParameters getProcessParameters() {
        return processParameters;
    }

    /**
     * Sets the value of the processParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigurableParameters }
     *     
     */
    public void setProcessParameters(ConfigurableParameters value) {
        this.processParameters = value;
    }

    /**
     * Gets the value of the additionalIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationComponentAdditionalIdentifier }
     * 
     * 
     */
    public List<CommunicationComponentAdditionalIdentifier> getAdditionalIdentifier() {
        if (additionalIdentifier == null) {
            additionalIdentifier = new ArrayList<CommunicationComponentAdditionalIdentifier>();
        }
        return this.additionalIdentifier;
    }

}
