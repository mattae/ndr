
package org.fhi360.lamis.modules.ndr.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AntenatalRegistrationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AntenatalRegistrationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VisitID" type="{}StringType"/>
 *         &lt;element name="VisitDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="LastMenstralPeriod" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="GestationalAgeAtANCRegistration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Gravida" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Parity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SourceOfReferral" type="{}StringType"/>
 *         &lt;element name="ExpectedDateOfDelivery" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Syphilis">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TestedForSyphilis">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{}CodeType">
 *                         &lt;enumeration value="Y"/>
 *                         &lt;enumeration value="N"/>
 *                         &lt;enumeration value="U"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="SyphilisTestResult">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{}CodeType">
 *                         &lt;enumeration value="Pos"/>
 *                         &lt;enumeration value="Neg"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TreatedForSyphilis">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{}CodeType">
 *                         &lt;enumeration value="Y"/>
 *                         &lt;enumeration value="N"/>
 *                         &lt;enumeration value="U"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="SyphilisResult" type="{}StringType"/>
 *                   &lt;element name="ReferredSyphilisPositiveClient">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{}CodeType">
 *                         &lt;enumeration value="Y"/>
 *                         &lt;enumeration value="N"/>
 *                         &lt;enumeration value="U"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "AntenatalRegistrationType", propOrder = {
    "visitID",
    "visitDate",
    "lastMenstralPeriod",
    "gestationalAgeAtANCRegistration",
    "gravida",
    "parity",
    "sourceOfReferral",
    "expectedDateOfDelivery",
    "syphilis"
})
public class AntenatalRegistrationType {

    @XmlElement(name = "VisitID", required = true)
    protected String visitID;
    @XmlElement(name = "VisitDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar visitDate;
    @XmlElement(name = "LastMenstralPeriod", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastMenstralPeriod;
    @XmlElement(name = "GestationalAgeAtANCRegistration")
    protected int gestationalAgeAtANCRegistration;
    @XmlElement(name = "Gravida")
    protected int gravida;
    @XmlElement(name = "Parity")
    protected int parity;
    @XmlElement(name = "SourceOfReferral", required = true)
    protected String sourceOfReferral;
    @XmlElement(name = "ExpectedDateOfDelivery", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar expectedDateOfDelivery;
    @XmlElement(name = "Syphilis", required = true)
    protected AntenatalRegistrationType.Syphilis syphilis;

    /**
     * Gets the value of the visitID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisitID() {
        return visitID;
    }

    /**
     * Sets the value of the visitID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisitID(String value) {
        this.visitID = value;
    }

    /**
     * Gets the value of the visitDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVisitDate() {
        return visitDate;
    }

    /**
     * Sets the value of the visitDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVisitDate(XMLGregorianCalendar value) {
        this.visitDate = value;
    }

    /**
     * Gets the value of the lastMenstralPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastMenstralPeriod() {
        return lastMenstralPeriod;
    }

    /**
     * Sets the value of the lastMenstralPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastMenstralPeriod(XMLGregorianCalendar value) {
        this.lastMenstralPeriod = value;
    }

    /**
     * Gets the value of the gestationalAgeAtANCRegistration property.
     * 
     */
    public int getGestationalAgeAtANCRegistration() {
        return gestationalAgeAtANCRegistration;
    }

    /**
     * Sets the value of the gestationalAgeAtANCRegistration property.
     * 
     */
    public void setGestationalAgeAtANCRegistration(int value) {
        this.gestationalAgeAtANCRegistration = value;
    }

    /**
     * Gets the value of the gravida property.
     * 
     */
    public int getGravida() {
        return gravida;
    }

    /**
     * Sets the value of the gravida property.
     * 
     */
    public void setGravida(int value) {
        this.gravida = value;
    }

    /**
     * Gets the value of the parity property.
     * 
     */
    public int getParity() {
        return parity;
    }

    /**
     * Sets the value of the parity property.
     * 
     */
    public void setParity(int value) {
        this.parity = value;
    }

    /**
     * Gets the value of the sourceOfReferral property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceOfReferral() {
        return sourceOfReferral;
    }

    /**
     * Sets the value of the sourceOfReferral property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceOfReferral(String value) {
        this.sourceOfReferral = value;
    }

    /**
     * Gets the value of the expectedDateOfDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpectedDateOfDelivery() {
        return expectedDateOfDelivery;
    }

    /**
     * Sets the value of the expectedDateOfDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpectedDateOfDelivery(XMLGregorianCalendar value) {
        this.expectedDateOfDelivery = value;
    }

    /**
     * Gets the value of the syphilis property.
     * 
     * @return
     *     possible object is
     *     {@link AntenatalRegistrationType.Syphilis }
     *     
     */
    public AntenatalRegistrationType.Syphilis getSyphilis() {
        return syphilis;
    }

    /**
     * Sets the value of the syphilis property.
     * 
     * @param value
     *     allowed object is
     *     {@link AntenatalRegistrationType.Syphilis }
     *     
     */
    public void setSyphilis(AntenatalRegistrationType.Syphilis value) {
        this.syphilis = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="TestedForSyphilis">
     *           &lt;simpleType>
     *             &lt;restriction base="{}CodeType">
     *               &lt;enumeration value="Y"/>
     *               &lt;enumeration value="N"/>
     *               &lt;enumeration value="U"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SyphilisTestResult">
     *           &lt;simpleType>
     *             &lt;restriction base="{}CodeType">
     *               &lt;enumeration value="Pos"/>
     *               &lt;enumeration value="Neg"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TreatedForSyphilis">
     *           &lt;simpleType>
     *             &lt;restriction base="{}CodeType">
     *               &lt;enumeration value="Y"/>
     *               &lt;enumeration value="N"/>
     *               &lt;enumeration value="U"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SyphilisResult" type="{}StringType"/>
     *         &lt;element name="ReferredSyphilisPositiveClient">
     *           &lt;simpleType>
     *             &lt;restriction base="{}CodeType">
     *               &lt;enumeration value="Y"/>
     *               &lt;enumeration value="N"/>
     *               &lt;enumeration value="U"/>
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
    @XmlType(name = "", propOrder = {
        "testedForSyphilis",
        "syphilisTestResult",
        "treatedForSyphilis",
        "syphilisResult",
        "referredSyphilisPositiveClient"
    })
    public static class Syphilis {

        @XmlElement(name = "TestedForSyphilis", required = true)
        protected String testedForSyphilis;
        @XmlElement(name = "SyphilisTestResult", required = true)
        protected String syphilisTestResult;
        @XmlElement(name = "TreatedForSyphilis", required = true)
        protected String treatedForSyphilis;
        @XmlElement(name = "SyphilisResult", required = true)
        protected String syphilisResult;
        @XmlElement(name = "ReferredSyphilisPositiveClient", required = true)
        protected String referredSyphilisPositiveClient;

        /**
         * Gets the value of the testedForSyphilis property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTestedForSyphilis() {
            return testedForSyphilis;
        }

        /**
         * Sets the value of the testedForSyphilis property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTestedForSyphilis(String value) {
            this.testedForSyphilis = value;
        }

        /**
         * Gets the value of the syphilisTestResult property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSyphilisTestResult() {
            return syphilisTestResult;
        }

        /**
         * Sets the value of the syphilisTestResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSyphilisTestResult(String value) {
            this.syphilisTestResult = value;
        }

        /**
         * Gets the value of the treatedForSyphilis property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTreatedForSyphilis() {
            return treatedForSyphilis;
        }

        /**
         * Sets the value of the treatedForSyphilis property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTreatedForSyphilis(String value) {
            this.treatedForSyphilis = value;
        }

        /**
         * Gets the value of the syphilisResult property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSyphilisResult() {
            return syphilisResult;
        }

        /**
         * Sets the value of the syphilisResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSyphilisResult(String value) {
            this.syphilisResult = value;
        }

        /**
         * Gets the value of the referredSyphilisPositiveClient property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReferredSyphilisPositiveClient() {
            return referredSyphilisPositiveClient;
        }

        /**
         * Sets the value of the referredSyphilisPositiveClient property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReferredSyphilisPositiveClient(String value) {
            this.referredSyphilisPositiveClient = value;
        }

    }

}
