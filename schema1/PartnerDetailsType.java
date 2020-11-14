
package org.fhi360.lamis.modules.ndr.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartnerDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartnerDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PartnerAge" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PartnerPreTestCounseled">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="U"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PartnerPostTestCounseled">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="U"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PartnerAcceptsHIVTest">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="U"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PartnerHIVTestResult">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Pos"/>
 *               &lt;enumeration value="Neg"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PartnerHBVStatus">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Pos"/>
 *               &lt;enumeration value="Neg"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PartnerHCVStatus">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Pos"/>
 *               &lt;enumeration value="Neg"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PartnerSyphilisStatus">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="NR"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PartnerReferredTo">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="FP"/>
 *               &lt;enumeration value="ART"/>
 *               &lt;enumeration value="Other"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartnerDetailsType", propOrder = {
    "partnerAge",
    "partnerPreTestCounseled",
    "partnerPostTestCounseled",
    "partnerAcceptsHIVTest",
    "partnerHIVTestResult",
    "partnerHBVStatus",
    "partnerHCVStatus",
    "partnerSyphilisStatus",
    "partnerReferredTo"
})
public class PartnerDetailsType {

    @XmlElement(name = "PartnerAge")
    protected int partnerAge;
    @XmlElement(name = "PartnerPreTestCounseled", required = true)
    protected String partnerPreTestCounseled;
    @XmlElement(name = "PartnerPostTestCounseled", required = true)
    protected String partnerPostTestCounseled;
    @XmlElement(name = "PartnerAcceptsHIVTest", required = true)
    protected String partnerAcceptsHIVTest;
    @XmlElement(name = "PartnerHIVTestResult", required = true)
    protected String partnerHIVTestResult;
    @XmlElement(name = "PartnerHBVStatus", required = true)
    protected String partnerHBVStatus;
    @XmlElement(name = "PartnerHCVStatus", required = true)
    protected String partnerHCVStatus;
    @XmlElement(name = "PartnerSyphilisStatus", required = true)
    protected String partnerSyphilisStatus;
    @XmlElement(name = "PartnerReferredTo", required = true)
    protected String partnerReferredTo;

    /**
     * Gets the value of the partnerAge property.
     * 
     */
    public int getPartnerAge() {
        return partnerAge;
    }

    /**
     * Sets the value of the partnerAge property.
     * 
     */
    public void setPartnerAge(int value) {
        this.partnerAge = value;
    }

    /**
     * Gets the value of the partnerPreTestCounseled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerPreTestCounseled() {
        return partnerPreTestCounseled;
    }

    /**
     * Sets the value of the partnerPreTestCounseled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerPreTestCounseled(String value) {
        this.partnerPreTestCounseled = value;
    }

    /**
     * Gets the value of the partnerPostTestCounseled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerPostTestCounseled() {
        return partnerPostTestCounseled;
    }

    /**
     * Sets the value of the partnerPostTestCounseled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerPostTestCounseled(String value) {
        this.partnerPostTestCounseled = value;
    }

    /**
     * Gets the value of the partnerAcceptsHIVTest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerAcceptsHIVTest() {
        return partnerAcceptsHIVTest;
    }

    /**
     * Sets the value of the partnerAcceptsHIVTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerAcceptsHIVTest(String value) {
        this.partnerAcceptsHIVTest = value;
    }

    /**
     * Gets the value of the partnerHIVTestResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerHIVTestResult() {
        return partnerHIVTestResult;
    }

    /**
     * Sets the value of the partnerHIVTestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerHIVTestResult(String value) {
        this.partnerHIVTestResult = value;
    }

    /**
     * Gets the value of the partnerHBVStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerHBVStatus() {
        return partnerHBVStatus;
    }

    /**
     * Sets the value of the partnerHBVStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerHBVStatus(String value) {
        this.partnerHBVStatus = value;
    }

    /**
     * Gets the value of the partnerHCVStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerHCVStatus() {
        return partnerHCVStatus;
    }

    /**
     * Sets the value of the partnerHCVStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerHCVStatus(String value) {
        this.partnerHCVStatus = value;
    }

    /**
     * Gets the value of the partnerSyphilisStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerSyphilisStatus() {
        return partnerSyphilisStatus;
    }

    /**
     * Sets the value of the partnerSyphilisStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerSyphilisStatus(String value) {
        this.partnerSyphilisStatus = value;
    }

    /**
     * Gets the value of the partnerReferredTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerReferredTo() {
        return partnerReferredTo;
    }

    /**
     * Sets the value of the partnerReferredTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerReferredTo(String value) {
        this.partnerReferredTo = value;
    }

}
