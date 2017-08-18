
package com.sap.xi.basis;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessSystemReadOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessSystemReadOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BusinessSystem" type="{http://sap.com/xi/BASIS}BusinessSystem" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "BusinessSystemReadOut", propOrder = {
    "businessSystem",
    "logMessageCollection"
})
public class BusinessSystemReadOut {

    @XmlElement(name = "BusinessSystem")
    protected List<BusinessSystem> businessSystem;
    @XmlElement(name = "LogMessageCollection")
    protected LogMessageCollection logMessageCollection;

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
     * {@link BusinessSystem }
     * 
     * 
     */
    public List<BusinessSystem> getBusinessSystem() {
        if (businessSystem == null) {
            businessSystem = new ArrayList<BusinessSystem>();
        }
        return this.businessSystem;
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
