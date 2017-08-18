
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
 * <p>Java class for ConfigurationScenario complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigurationScenario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MasterLanguage" type="{http://sap.com/xi/BASIS/Global}LanguageCode"/>
 *         &lt;element name="AdministrativeData" type="{http://sap.com/xi/BASIS}ObjectAdministrativeData" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://sap.com/xi/BASIS/Global}LONG_Description" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ConfigurationScenarioID" type="{http://sap.com/xi/BASIS}ConfigurationScenarioID"/>
 *         &lt;element name="RepositoryScenario" type="{http://sap.com/xi/BASIS}DesignObjectID" minOccurs="0"/>
 *         &lt;element name="Party" type="{http://sap.com/xi/BASIS}CommunicationPartyID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ProcessComponent" type="{http://sap.com/xi/BASIS}ProcessComponentID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="BusinessSystem" type="{http://sap.com/xi/BASIS}CommunicationComponentID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="BusinessComponent" type="{http://sap.com/xi/BASIS}CommunicationComponentID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="IntegrationProcess" type="{http://sap.com/xi/BASIS}CommunicationComponentID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CommunicationChannel" type="{http://sap.com/xi/BASIS}CommunicationChannelID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SenderAgreement" type="{http://sap.com/xi/BASIS}MessageHeaderID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ReceiverAgreement" type="{http://sap.com/xi/BASIS}MessageHeaderID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DirectConnection" type="{http://sap.com/xi/BASIS}MessageHeaderID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="IntegratedConfiguration" type="{http://sap.com/xi/BASIS}MessageHeaderID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ReceiverDetermination" type="{http://sap.com/xi/BASIS}MessageHeaderID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ReceiverRule" type="{http://sap.com/xi/BASIS}ReceiverRuleID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="InterfaceDetermination" type="{http://sap.com/xi/BASIS}MessageHeaderID" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigurationScenario", propOrder = {
    "masterLanguage",
    "administrativeData",
    "description",
    "configurationScenarioID",
    "repositoryScenario",
    "party",
    "processComponent",
    "businessSystem",
    "businessComponent",
    "integrationProcess",
    "communicationChannel",
    "senderAgreement",
    "receiverAgreement",
    "directConnection",
    "integratedConfiguration",
    "receiverDetermination",
    "receiverRule",
    "interfaceDetermination"
})
public class ConfigurationScenario {

    @XmlElement(name = "MasterLanguage", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String masterLanguage;
    @XmlElement(name = "AdministrativeData")
    protected ObjectAdministrativeData administrativeData;
    @XmlElement(name = "Description")
    protected List<LONGDescription> description;
    @XmlElement(name = "ConfigurationScenarioID", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String configurationScenarioID;
    @XmlElement(name = "RepositoryScenario")
    protected DesignObjectID repositoryScenario;
    @XmlElement(name = "Party")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected List<String> party;
    @XmlElement(name = "ProcessComponent")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected List<String> processComponent;
    @XmlElement(name = "BusinessSystem")
    protected List<CommunicationComponentID> businessSystem;
    @XmlElement(name = "BusinessComponent")
    protected List<CommunicationComponentID> businessComponent;
    @XmlElement(name = "IntegrationProcess")
    protected List<CommunicationComponentID> integrationProcess;
    @XmlElement(name = "CommunicationChannel")
    protected List<CommunicationChannelID> communicationChannel;
    @XmlElement(name = "SenderAgreement")
    protected List<MessageHeaderID> senderAgreement;
    @XmlElement(name = "ReceiverAgreement")
    protected List<MessageHeaderID> receiverAgreement;
    @XmlElement(name = "DirectConnection")
    protected List<MessageHeaderID> directConnection;
    @XmlElement(name = "IntegratedConfiguration")
    protected List<MessageHeaderID> integratedConfiguration;
    @XmlElement(name = "ReceiverDetermination")
    protected List<MessageHeaderID> receiverDetermination;
    @XmlElement(name = "ReceiverRule")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected List<String> receiverRule;
    @XmlElement(name = "InterfaceDetermination")
    protected List<MessageHeaderID> interfaceDetermination;

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
     * Gets the value of the configurationScenarioID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigurationScenarioID() {
        return configurationScenarioID;
    }

    /**
     * Sets the value of the configurationScenarioID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigurationScenarioID(String value) {
        this.configurationScenarioID = value;
    }

    /**
     * Gets the value of the repositoryScenario property.
     * 
     * @return
     *     possible object is
     *     {@link DesignObjectID }
     *     
     */
    public DesignObjectID getRepositoryScenario() {
        return repositoryScenario;
    }

    /**
     * Sets the value of the repositoryScenario property.
     * 
     * @param value
     *     allowed object is
     *     {@link DesignObjectID }
     *     
     */
    public void setRepositoryScenario(DesignObjectID value) {
        this.repositoryScenario = value;
    }

    /**
     * Gets the value of the party property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the party property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getParty() {
        if (party == null) {
            party = new ArrayList<String>();
        }
        return this.party;
    }

    /**
     * Gets the value of the processComponent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the processComponent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcessComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getProcessComponent() {
        if (processComponent == null) {
            processComponent = new ArrayList<String>();
        }
        return this.processComponent;
    }

    /**
     * Gets the value of the businessSystem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the businessSystem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBusinessSystem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationComponentID }
     * 
     * 
     */
    public List<CommunicationComponentID> getBusinessSystem() {
        if (businessSystem == null) {
            businessSystem = new ArrayList<CommunicationComponentID>();
        }
        return this.businessSystem;
    }

    /**
     * Gets the value of the businessComponent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the businessComponent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBusinessComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationComponentID }
     * 
     * 
     */
    public List<CommunicationComponentID> getBusinessComponent() {
        if (businessComponent == null) {
            businessComponent = new ArrayList<CommunicationComponentID>();
        }
        return this.businessComponent;
    }

    /**
     * Gets the value of the integrationProcess property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the integrationProcess property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIntegrationProcess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationComponentID }
     * 
     * 
     */
    public List<CommunicationComponentID> getIntegrationProcess() {
        if (integrationProcess == null) {
            integrationProcess = new ArrayList<CommunicationComponentID>();
        }
        return this.integrationProcess;
    }

    /**
     * Gets the value of the communicationChannel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the communicationChannel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommunicationChannel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationChannelID }
     * 
     * 
     */
    public List<CommunicationChannelID> getCommunicationChannel() {
        if (communicationChannel == null) {
            communicationChannel = new ArrayList<CommunicationChannelID>();
        }
        return this.communicationChannel;
    }

    /**
     * Gets the value of the senderAgreement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the senderAgreement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSenderAgreement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageHeaderID }
     * 
     * 
     */
    public List<MessageHeaderID> getSenderAgreement() {
        if (senderAgreement == null) {
            senderAgreement = new ArrayList<MessageHeaderID>();
        }
        return this.senderAgreement;
    }

    /**
     * Gets the value of the receiverAgreement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receiverAgreement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceiverAgreement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageHeaderID }
     * 
     * 
     */
    public List<MessageHeaderID> getReceiverAgreement() {
        if (receiverAgreement == null) {
            receiverAgreement = new ArrayList<MessageHeaderID>();
        }
        return this.receiverAgreement;
    }

    /**
     * Gets the value of the directConnection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the directConnection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDirectConnection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageHeaderID }
     * 
     * 
     */
    public List<MessageHeaderID> getDirectConnection() {
        if (directConnection == null) {
            directConnection = new ArrayList<MessageHeaderID>();
        }
        return this.directConnection;
    }

    /**
     * Gets the value of the integratedConfiguration property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the integratedConfiguration property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIntegratedConfiguration().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageHeaderID }
     * 
     * 
     */
    public List<MessageHeaderID> getIntegratedConfiguration() {
        if (integratedConfiguration == null) {
            integratedConfiguration = new ArrayList<MessageHeaderID>();
        }
        return this.integratedConfiguration;
    }

    /**
     * Gets the value of the receiverDetermination property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receiverDetermination property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceiverDetermination().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageHeaderID }
     * 
     * 
     */
    public List<MessageHeaderID> getReceiverDetermination() {
        if (receiverDetermination == null) {
            receiverDetermination = new ArrayList<MessageHeaderID>();
        }
        return this.receiverDetermination;
    }

    /**
     * Gets the value of the receiverRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receiverRule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceiverRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getReceiverRule() {
        if (receiverRule == null) {
            receiverRule = new ArrayList<String>();
        }
        return this.receiverRule;
    }

    /**
     * Gets the value of the interfaceDetermination property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interfaceDetermination property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInterfaceDetermination().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageHeaderID }
     * 
     * 
     */
    public List<MessageHeaderID> getInterfaceDetermination() {
        if (interfaceDetermination == null) {
            interfaceDetermination = new ArrayList<MessageHeaderID>();
        }
        return this.interfaceDetermination;
    }

}
