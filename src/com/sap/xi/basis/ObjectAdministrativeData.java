
package com.sap.xi.basis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * ObjectAdministrativeData
 * 
 * <p>Java class for ObjectAdministrativeData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectAdministrativeData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponsibleUserAccountID" type="{http://sap.com/xi/BASIS}UserID" minOccurs="0"/>
 *         &lt;element name="LastChangeUserAccountID" type="{http://sap.com/xi/BASIS}UserID" minOccurs="0"/>
 *         &lt;element name="LastChangeDateTime" type="{http://sap.com/xi/BASIS/Global}GLOBAL_DateTime" minOccurs="0"/>
 *         &lt;element name="FolderPathID" type="{http://sap.com/xi/BASIS/Global}LANGUAGEINDEPENDENT_Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectAdministrativeData", propOrder = {
    "responsibleUserAccountID",
    "lastChangeUserAccountID",
    "lastChangeDateTime",
    "folderPathID"
})
public class ObjectAdministrativeData {

    @XmlElement(name = "ResponsibleUserAccountID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String responsibleUserAccountID;
    @XmlElement(name = "LastChangeUserAccountID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String lastChangeUserAccountID;
    @XmlElement(name = "LastChangeDateTime")
    protected XMLGregorianCalendar lastChangeDateTime;
    @XmlElement(name = "FolderPathID")
    protected String folderPathID;

    /**
     * Gets the value of the responsibleUserAccountID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsibleUserAccountID() {
        return responsibleUserAccountID;
    }

    /**
     * Sets the value of the responsibleUserAccountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsibleUserAccountID(String value) {
        this.responsibleUserAccountID = value;
    }

    /**
     * Gets the value of the lastChangeUserAccountID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastChangeUserAccountID() {
        return lastChangeUserAccountID;
    }

    /**
     * Sets the value of the lastChangeUserAccountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastChangeUserAccountID(String value) {
        this.lastChangeUserAccountID = value;
    }

    /**
     * Gets the value of the lastChangeDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastChangeDateTime() {
        return lastChangeDateTime;
    }

    /**
     * Sets the value of the lastChangeDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastChangeDateTime(XMLGregorianCalendar value) {
        this.lastChangeDateTime = value;
    }

    /**
     * Gets the value of the folderPathID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolderPathID() {
        return folderPathID;
    }

    /**
     * Sets the value of the folderPathID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolderPathID(String value) {
        this.folderPathID = value;
    }

}
