
package com.sap.xi.basis;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for DirectConnectionDeleteOpenForEditIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DirectConnectionDeleteOpenForEditIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChangeListID" type="{http://sap.com/xi/BASIS}ChangeListChangeListID" minOccurs="0"/>
 *         &lt;element name="DirectConnectionID" type="{http://sap.com/xi/BASIS}MessageHeaderID" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DirectConnectionDeleteOpenForEditIn", propOrder = {
    "changeListID",
    "directConnectionID"
})
public class DirectConnectionDeleteOpenForEditIn {

    @XmlElement(name = "ChangeListID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String changeListID;
    @XmlElement(name = "DirectConnectionID")
    protected List<MessageHeaderID> directConnectionID;

    /**
     * Gets the value of the changeListID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangeListID() {
        return changeListID;
    }

    /**
     * Sets the value of the changeListID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangeListID(String value) {
        this.changeListID = value;
    }

    /**
     * Gets the value of the directConnectionID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the directConnectionID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDirectConnectionID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageHeaderID }
     * 
     * 
     */
    public List<MessageHeaderID> getDirectConnectionID() {
        if (directConnectionID == null) {
            directConnectionID = new ArrayList<MessageHeaderID>();
        }
        return this.directConnectionID;
    }

}
