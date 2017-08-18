
package com.sap.xi.basis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CacheNotification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CacheNotification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DistributeCacheIndicator" type="{http://sap.com/xi/BASIS/Global}Indicator" minOccurs="0"/>
 *         &lt;element name="CacheUser" type="{http://sap.com/xi/BASIS}UserID" minOccurs="0"/>
 *         &lt;element name="CacheUserPassword" type="{http://sap.com/xi/BASIS/Global}LANGUAGEINDEPENDENT_Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CacheNotification", propOrder = {
    "distributeCacheIndicator",
    "cacheUser",
    "cacheUserPassword"
})
public class CacheNotification {

    @XmlElement(name = "DistributeCacheIndicator")
    protected Boolean distributeCacheIndicator;
    @XmlElement(name = "CacheUser")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String cacheUser;
    @XmlElement(name = "CacheUserPassword")
    protected String cacheUserPassword;

    /**
     * Gets the value of the distributeCacheIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDistributeCacheIndicator() {
        return distributeCacheIndicator;
    }

    /**
     * Sets the value of the distributeCacheIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDistributeCacheIndicator(Boolean value) {
        this.distributeCacheIndicator = value;
    }

    /**
     * Gets the value of the cacheUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCacheUser() {
        return cacheUser;
    }

    /**
     * Sets the value of the cacheUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCacheUser(String value) {
        this.cacheUser = value;
    }

    /**
     * Gets the value of the cacheUserPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCacheUserPassword() {
        return cacheUserPassword;
    }

    /**
     * Sets the value of the cacheUserPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCacheUserPassword(String value) {
        this.cacheUserPassword = value;
    }

}
