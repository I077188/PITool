
package com.sap.xi.basis;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfigurableParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigurableParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Integer" type="{http://sap.com/xi/BASIS}IntegerProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="String" type="{http://sap.com/xi/BASIS}RestrictedGenericProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Channel" type="{http://sap.com/xi/BASIS}ChannelProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Date" type="{http://sap.com/xi/BASIS}DateProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Time" type="{http://sap.com/xi/BASIS}TimeProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Agent" type="{http://sap.com/xi/BASIS}AgentProperty" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigurableParameters", propOrder = {
    "integer",
    "string",
    "channel",
    "date",
    "time",
    "agent"
})
public class ConfigurableParameters {

    @XmlElement(name = "Integer")
    protected List<IntegerProperty> integer;
    @XmlElement(name = "String")
    protected List<RestrictedGenericProperty> string;
    @XmlElement(name = "Channel")
    protected List<ChannelProperty> channel;
    @XmlElement(name = "Date")
    protected List<DateProperty> date;
    @XmlElement(name = "Time")
    protected List<TimeProperty> time;
    @XmlElement(name = "Agent")
    protected List<AgentProperty> agent;

    /**
     * Gets the value of the integer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the integer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInteger().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IntegerProperty }
     * 
     * 
     */
    public List<IntegerProperty> getInteger() {
        if (integer == null) {
            integer = new ArrayList<IntegerProperty>();
        }
        return this.integer;
    }

    /**
     * Gets the value of the string property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the string property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getString().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RestrictedGenericProperty }
     * 
     * 
     */
    public List<RestrictedGenericProperty> getString() {
        if (string == null) {
            string = new ArrayList<RestrictedGenericProperty>();
        }
        return this.string;
    }

    /**
     * Gets the value of the channel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the channel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChannel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChannelProperty }
     * 
     * 
     */
    public List<ChannelProperty> getChannel() {
        if (channel == null) {
            channel = new ArrayList<ChannelProperty>();
        }
        return this.channel;
    }

    /**
     * Gets the value of the date property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the date property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DateProperty }
     * 
     * 
     */
    public List<DateProperty> getDate() {
        if (date == null) {
            date = new ArrayList<DateProperty>();
        }
        return this.date;
    }

    /**
     * Gets the value of the time property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the time property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeProperty }
     * 
     * 
     */
    public List<TimeProperty> getTime() {
        if (time == null) {
            time = new ArrayList<TimeProperty>();
        }
        return this.time;
    }

    /**
     * Gets the value of the agent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgentProperty }
     * 
     * 
     */
    public List<AgentProperty> getAgent() {
        if (agent == null) {
            agent = new ArrayList<AgentProperty>();
        }
        return this.agent;
    }

}
