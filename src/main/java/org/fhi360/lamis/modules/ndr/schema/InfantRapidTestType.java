
package org.fhi360.lamis.modules.ndr.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InfantRapidTestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfantRapidTestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AgeAtTest" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DateOfTest" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="RapidTestResult">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Pos"/>
 *               &lt;enumeration value="Neg"/>
 *               &lt;enumeration value="Indet"/>
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
@XmlType(name = "InfantRapidTestType", propOrder = {
    "ageAtTest",
    "dateOfTest",
    "rapidTestResult"
})
public class InfantRapidTestType {

    @XmlElement(name = "AgeAtTest")
    protected int ageAtTest;
    @XmlElement(name = "DateOfTest", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfTest;
    @XmlElement(name = "RapidTestResult", required = true)
    protected String rapidTestResult;

    /**
     * Gets the value of the ageAtTest property.
     * 
     */
    public int getAgeAtTest() {
        return ageAtTest;
    }

    /**
     * Sets the value of the ageAtTest property.
     * 
     */
    public void setAgeAtTest(int value) {
        this.ageAtTest = value;
    }

    /**
     * Gets the value of the dateOfTest property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfTest() {
        return dateOfTest;
    }

    /**
     * Sets the value of the dateOfTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfTest(XMLGregorianCalendar value) {
        this.dateOfTest = value;
    }

    /**
     * Gets the value of the rapidTestResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRapidTestResult() {
        return rapidTestResult;
    }

    /**
     * Sets the value of the rapidTestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRapidTestResult(String value) {
        this.rapidTestResult = value;
    }

}
