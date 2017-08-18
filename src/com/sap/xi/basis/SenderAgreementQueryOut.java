
package com.sap.xi.basis;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SenderAgreementQueryOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SenderAgreementQueryOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SenderAgreementID" type="{http://sap.com/xi/BASIS}MessageHeaderID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LogMessageCollection" type="{http://sap.com/xi/BASIS}LogMessageCollection" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SenderAgreementQueryOut", propOrder = {
    "senderAgreementID",
    "logMessageCollection"
})
public class SenderAgreementQueryOut {

    @XmlElement(name = "SenderAgreementID")
    protected List<MessageHeaderID> senderAgreementID;
    @XmlElement(name = "LogMessageCollection")
    protected LogMessageCollection logMessageCollection;

    /**
     * Gets the value of the senderAgreementID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the senderAgreementID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSenderAgreementID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageHeaderID }
     * 
     * 
     */
    public List<MessageHeaderID> getSenderAgreementID() {
        if (senderAgreementID == null) {
            senderAgreementID = new ArrayList<MessageHeaderID>();
        }
        return this.senderAgreementID;
    }

    /**
     * Gets the value of the logMessageCollection property.
     * 
     * @return
     *     possible object is
     *     {@link LogMessageCollection }
     *     
     */
    public LogMessageCollection getLogMessageCollection() {
        return logMessageCollection;
    }

    /**
     * Sets the value of the logMessageCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogMessageCollection }
     *     
     */
    public void setLogMessageCollection(LogMessageCollection value) {
        this.logMessageCollection = value;
    }

}
