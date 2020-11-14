
package org.fhi360.lamis.modules.ndr.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartnerNotificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartnerNotificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Partnername" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PartnerGender">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IndexRelation">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DescriptiveAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartnerNotificationType", propOrder = {
    "partnername",
    "partnerGender",
    "indexRelation",
    "descriptiveAddress",
    "phoneNumber"
})
public class PartnerNotificationType {

    @XmlElement(name = "Partnername", required = true)
    protected String partnername;
    @XmlElement(name = "PartnerGender", required = true)
    protected String partnerGender;
    @XmlElement(name = "IndexRelation", required = true)
    protected String indexRelation;
    @XmlElement(name = "DescriptiveAddress", required = true)
    protected String descriptiveAddress;
    @XmlElement(name = "PhoneNumber", required = true)
    protected String phoneNumber;

    /**
     * Gets the value of the partnername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnername() {
        return partnername;
    }

    /**
     * Sets the value of the partnername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnername(String value) {
        this.partnername = value;
    }

    /**
     * Gets the value of the partnerGender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerGender() {
        return partnerGender;
    }

    /**
     * Sets the value of the partnerGender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerGender(String value) {
        this.partnerGender = value;
    }

    /**
     * Gets the value of the indexRelation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexRelation() {
        return indexRelation;
    }

    /**
     * Sets the value of the indexRelation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexRelation(String value) {
        this.indexRelation = value;
    }

    /**
     * Gets the value of the descriptiveAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescriptiveAddress() {
        return descriptiveAddress;
    }

    /**
     * Sets the value of the descriptiveAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescriptiveAddress(String value) {
        this.descriptiveAddress = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

}
