
package org.fhi360.lamis.modules.ndr.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rightHandType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rightHandType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RightThumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RightIndex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RightMiddle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RightWedding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RightSmall" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rightHandType", propOrder = {
    "rightThumb",
    "rightIndex",
    "rightMiddle",
    "rightWedding",
    "rightSmall"
})
public class RightHandType {

    @XmlElement(name = "RightThumb")
    protected String rightThumb;
    @XmlElement(name = "RightIndex")
    protected String rightIndex;
    @XmlElement(name = "RightMiddle")
    protected String rightMiddle;
    @XmlElement(name = "RightWedding")
    protected String rightWedding;
    @XmlElement(name = "RightSmall")
    protected String rightSmall;

    /**
     * Gets the value of the rightThumb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRightThumb() {
        return rightThumb;
    }

    /**
     * Sets the value of the rightThumb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRightThumb(String value) {
        this.rightThumb = value;
    }

    /**
     * Gets the value of the rightIndex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRightIndex() {
        return rightIndex;
    }

    /**
     * Sets the value of the rightIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRightIndex(String value) {
        this.rightIndex = value;
    }

    /**
     * Gets the value of the rightMiddle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRightMiddle() {
        return rightMiddle;
    }

    /**
     * Sets the value of the rightMiddle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRightMiddle(String value) {
        this.rightMiddle = value;
    }

    /**
     * Gets the value of the rightWedding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRightWedding() {
        return rightWedding;
    }

    /**
     * Sets the value of the rightWedding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRightWedding(String value) {
        this.rightWedding = value;
    }

    /**
     * Gets the value of the rightSmall property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRightSmall() {
        return rightSmall;
    }

    /**
     * Sets the value of the rightSmall property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRightSmall(String value) {
        this.rightSmall = value;
    }

}
