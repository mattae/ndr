
package org.fhi360.lamis.modules.ndr.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for leftHandType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="leftHandType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LeftThumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftIndex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftMiddle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftWedding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftSmall" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "leftHandType", propOrder = {
    "leftThumb",
    "leftIndex",
    "leftMiddle",
    "leftWedding",
    "leftSmall"
})
public class LeftHandType {

    @XmlElement(name = "LeftThumb")
    protected String leftThumb;
    @XmlElement(name = "LeftIndex")
    protected String leftIndex;
    @XmlElement(name = "LeftMiddle")
    protected String leftMiddle;
    @XmlElement(name = "LeftWedding")
    protected String leftWedding;
    @XmlElement(name = "LeftSmall")
    protected String leftSmall;

    /**
     * Gets the value of the leftThumb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeftThumb() {
        return leftThumb;
    }

    /**
     * Sets the value of the leftThumb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeftThumb(String value) {
        this.leftThumb = value;
    }

    /**
     * Gets the value of the leftIndex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeftIndex() {
        return leftIndex;
    }

    /**
     * Sets the value of the leftIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeftIndex(String value) {
        this.leftIndex = value;
    }

    /**
     * Gets the value of the leftMiddle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeftMiddle() {
        return leftMiddle;
    }

    /**
     * Sets the value of the leftMiddle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeftMiddle(String value) {
        this.leftMiddle = value;
    }

    /**
     * Gets the value of the leftWedding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeftWedding() {
        return leftWedding;
    }

    /**
     * Sets the value of the leftWedding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeftWedding(String value) {
        this.leftWedding = value;
    }

    /**
     * Gets the value of the leftSmall property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeftSmall() {
        return leftSmall;
    }

    /**
     * Sets the value of the leftSmall property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeftSmall(String value) {
        this.leftSmall = value;
    }

}
