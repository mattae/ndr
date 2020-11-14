
package org.fhi360.lamis.modules.ndr.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostTestCounsellingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostTestCounsellingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TestedForHIVBeforeWithinThisYear">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="HIVRequestAndResultFormSignedByTester" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="HIVRequestAndResultFormFilledWithCTIForm" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ClientRecievedHIVTestResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PostTestCounsellingDone" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RiskReductionPlanDeveloped" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PostTestDisclosurePlanDeveloped" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WillBringPartnerForHIVTesting" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WillBringOwnChildrenForHIVTesting" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ProvidedWithInformationOnFPandDualContraception" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ClientOrPartnerUseFPMethodsOtherThanCondoms" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ClientOrPartnerUseCondomsAsOneFPMethods" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CorrectCondomUseDemonstrated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CondomsProvidedToClient" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ClientReferredToOtherServices" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostTestCounsellingType", propOrder = {
    "testedForHIVBeforeWithinThisYear",
    "hivRequestAndResultFormSignedByTester",
    "hivRequestAndResultFormFilledWithCTIForm",
    "clientRecievedHIVTestResult",
    "postTestCounsellingDone",
    "riskReductionPlanDeveloped",
    "postTestDisclosurePlanDeveloped",
    "willBringPartnerForHIVTesting",
    "willBringOwnChildrenForHIVTesting",
    "providedWithInformationOnFPandDualContraception",
    "clientOrPartnerUseFPMethodsOtherThanCondoms",
    "clientOrPartnerUseCondomsAsOneFPMethods",
    "correctCondomUseDemonstrated",
    "condomsProvidedToClient",
    "clientReferredToOtherServices"
})
public class PostTestCounsellingType {

    @XmlElement(name = "TestedForHIVBeforeWithinThisYear", required = true)
    protected String testedForHIVBeforeWithinThisYear;
    @XmlElement(name = "HIVRequestAndResultFormSignedByTester")
    protected boolean hivRequestAndResultFormSignedByTester;
    @XmlElement(name = "HIVRequestAndResultFormFilledWithCTIForm")
    protected boolean hivRequestAndResultFormFilledWithCTIForm;
    @XmlElement(name = "ClientRecievedHIVTestResult")
    protected boolean clientRecievedHIVTestResult;
    @XmlElement(name = "PostTestCounsellingDone")
    protected boolean postTestCounsellingDone;
    @XmlElement(name = "RiskReductionPlanDeveloped")
    protected boolean riskReductionPlanDeveloped;
    @XmlElement(name = "PostTestDisclosurePlanDeveloped")
    protected boolean postTestDisclosurePlanDeveloped;
    @XmlElement(name = "WillBringPartnerForHIVTesting")
    protected boolean willBringPartnerForHIVTesting;
    @XmlElement(name = "WillBringOwnChildrenForHIVTesting")
    protected boolean willBringOwnChildrenForHIVTesting;
    @XmlElement(name = "ProvidedWithInformationOnFPandDualContraception")
    protected boolean providedWithInformationOnFPandDualContraception;
    @XmlElement(name = "ClientOrPartnerUseFPMethodsOtherThanCondoms")
    protected boolean clientOrPartnerUseFPMethodsOtherThanCondoms;
    @XmlElement(name = "ClientOrPartnerUseCondomsAsOneFPMethods")
    protected boolean clientOrPartnerUseCondomsAsOneFPMethods;
    @XmlElement(name = "CorrectCondomUseDemonstrated")
    protected boolean correctCondomUseDemonstrated;
    @XmlElement(name = "CondomsProvidedToClient")
    protected boolean condomsProvidedToClient;
    @XmlElement(name = "ClientReferredToOtherServices")
    protected boolean clientReferredToOtherServices;

    /**
     * Gets the value of the testedForHIVBeforeWithinThisYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestedForHIVBeforeWithinThisYear() {
        return testedForHIVBeforeWithinThisYear;
    }

    /**
     * Sets the value of the testedForHIVBeforeWithinThisYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestedForHIVBeforeWithinThisYear(String value) {
        this.testedForHIVBeforeWithinThisYear = value;
    }

    /**
     * Gets the value of the hivRequestAndResultFormSignedByTester property.
     * 
     */
    public boolean isHIVRequestAndResultFormSignedByTester() {
        return hivRequestAndResultFormSignedByTester;
    }

    /**
     * Sets the value of the hivRequestAndResultFormSignedByTester property.
     * 
     */
    public void setHIVRequestAndResultFormSignedByTester(boolean value) {
        this.hivRequestAndResultFormSignedByTester = value;
    }

    /**
     * Gets the value of the hivRequestAndResultFormFilledWithCTIForm property.
     * 
     */
    public boolean isHIVRequestAndResultFormFilledWithCTIForm() {
        return hivRequestAndResultFormFilledWithCTIForm;
    }

    /**
     * Sets the value of the hivRequestAndResultFormFilledWithCTIForm property.
     * 
     */
    public void setHIVRequestAndResultFormFilledWithCTIForm(boolean value) {
        this.hivRequestAndResultFormFilledWithCTIForm = value;
    }

    /**
     * Gets the value of the clientRecievedHIVTestResult property.
     * 
     */
    public boolean isClientRecievedHIVTestResult() {
        return clientRecievedHIVTestResult;
    }

    /**
     * Sets the value of the clientRecievedHIVTestResult property.
     * 
     */
    public void setClientRecievedHIVTestResult(boolean value) {
        this.clientRecievedHIVTestResult = value;
    }

    /**
     * Gets the value of the postTestCounsellingDone property.
     * 
     */
    public boolean isPostTestCounsellingDone() {
        return postTestCounsellingDone;
    }

    /**
     * Sets the value of the postTestCounsellingDone property.
     * 
     */
    public void setPostTestCounsellingDone(boolean value) {
        this.postTestCounsellingDone = value;
    }

    /**
     * Gets the value of the riskReductionPlanDeveloped property.
     * 
     */
    public boolean isRiskReductionPlanDeveloped() {
        return riskReductionPlanDeveloped;
    }

    /**
     * Sets the value of the riskReductionPlanDeveloped property.
     * 
     */
    public void setRiskReductionPlanDeveloped(boolean value) {
        this.riskReductionPlanDeveloped = value;
    }

    /**
     * Gets the value of the postTestDisclosurePlanDeveloped property.
     * 
     */
    public boolean isPostTestDisclosurePlanDeveloped() {
        return postTestDisclosurePlanDeveloped;
    }

    /**
     * Sets the value of the postTestDisclosurePlanDeveloped property.
     * 
     */
    public void setPostTestDisclosurePlanDeveloped(boolean value) {
        this.postTestDisclosurePlanDeveloped = value;
    }

    /**
     * Gets the value of the willBringPartnerForHIVTesting property.
     * 
     */
    public boolean isWillBringPartnerForHIVTesting() {
        return willBringPartnerForHIVTesting;
    }

    /**
     * Sets the value of the willBringPartnerForHIVTesting property.
     * 
     */
    public void setWillBringPartnerForHIVTesting(boolean value) {
        this.willBringPartnerForHIVTesting = value;
    }

    /**
     * Gets the value of the willBringOwnChildrenForHIVTesting property.
     * 
     */
    public boolean isWillBringOwnChildrenForHIVTesting() {
        return willBringOwnChildrenForHIVTesting;
    }

    /**
     * Sets the value of the willBringOwnChildrenForHIVTesting property.
     * 
     */
    public void setWillBringOwnChildrenForHIVTesting(boolean value) {
        this.willBringOwnChildrenForHIVTesting = value;
    }

    /**
     * Gets the value of the providedWithInformationOnFPandDualContraception property.
     * 
     */
    public boolean isProvidedWithInformationOnFPandDualContraception() {
        return providedWithInformationOnFPandDualContraception;
    }

    /**
     * Sets the value of the providedWithInformationOnFPandDualContraception property.
     * 
     */
    public void setProvidedWithInformationOnFPandDualContraception(boolean value) {
        this.providedWithInformationOnFPandDualContraception = value;
    }

    /**
     * Gets the value of the clientOrPartnerUseFPMethodsOtherThanCondoms property.
     * 
     */
    public boolean isClientOrPartnerUseFPMethodsOtherThanCondoms() {
        return clientOrPartnerUseFPMethodsOtherThanCondoms;
    }

    /**
     * Sets the value of the clientOrPartnerUseFPMethodsOtherThanCondoms property.
     * 
     */
    public void setClientOrPartnerUseFPMethodsOtherThanCondoms(boolean value) {
        this.clientOrPartnerUseFPMethodsOtherThanCondoms = value;
    }

    /**
     * Gets the value of the clientOrPartnerUseCondomsAsOneFPMethods property.
     * 
     */
    public boolean isClientOrPartnerUseCondomsAsOneFPMethods() {
        return clientOrPartnerUseCondomsAsOneFPMethods;
    }

    /**
     * Sets the value of the clientOrPartnerUseCondomsAsOneFPMethods property.
     * 
     */
    public void setClientOrPartnerUseCondomsAsOneFPMethods(boolean value) {
        this.clientOrPartnerUseCondomsAsOneFPMethods = value;
    }

    /**
     * Gets the value of the correctCondomUseDemonstrated property.
     * 
     */
    public boolean isCorrectCondomUseDemonstrated() {
        return correctCondomUseDemonstrated;
    }

    /**
     * Sets the value of the correctCondomUseDemonstrated property.
     * 
     */
    public void setCorrectCondomUseDemonstrated(boolean value) {
        this.correctCondomUseDemonstrated = value;
    }

    /**
     * Gets the value of the condomsProvidedToClient property.
     * 
     */
    public boolean isCondomsProvidedToClient() {
        return condomsProvidedToClient;
    }

    /**
     * Sets the value of the condomsProvidedToClient property.
     * 
     */
    public void setCondomsProvidedToClient(boolean value) {
        this.condomsProvidedToClient = value;
    }

    /**
     * Gets the value of the clientReferredToOtherServices property.
     * 
     */
    public boolean isClientReferredToOtherServices() {
        return clientReferredToOtherServices;
    }

    /**
     * Sets the value of the clientReferredToOtherServices property.
     * 
     */
    public void setClientReferredToOtherServices(boolean value) {
        this.clientReferredToOtherServices = value;
    }

}
