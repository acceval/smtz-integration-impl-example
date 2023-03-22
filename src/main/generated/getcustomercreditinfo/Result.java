
package getcustomercreditinfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Result complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Result"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TotalCreditExposure" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TotalCreditExposurePercentage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TotalCreditLimit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BalanceCreditLimit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BalanceCreditLimitPercentage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreditBlock" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SecurityDoc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SecurityDocExpiryDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Result", propOrder = {
    "totalCreditExposure",
    "totalCreditExposurePercentage",
    "totalCreditLimit",
    "balanceCreditLimit",
    "balanceCreditLimitPercentage",
    "creditBlock",
    "securityDoc",
    "securityDocExpiryDate"
})
public class Result {

    @XmlElement(name = "TotalCreditExposure", required = true, nillable = true)
    protected String totalCreditExposure;
    @XmlElement(name = "TotalCreditExposurePercentage", required = true, nillable = true)
    protected String totalCreditExposurePercentage;
    @XmlElement(name = "TotalCreditLimit", required = true, nillable = true)
    protected String totalCreditLimit;
    @XmlElement(name = "BalanceCreditLimit", required = true, nillable = true)
    protected String balanceCreditLimit;
    @XmlElement(name = "BalanceCreditLimitPercentage", required = true, nillable = true)
    protected String balanceCreditLimitPercentage;
    @XmlElement(name = "CreditBlock", required = true, nillable = true)
    protected String creditBlock;
    @XmlElement(name = "SecurityDoc", required = true, nillable = true)
    protected String securityDoc;
    @XmlElement(name = "SecurityDocExpiryDate", required = true, nillable = true)
    protected String securityDocExpiryDate;

    /**
     * Gets the value of the totalCreditExposure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalCreditExposure() {
        return totalCreditExposure;
    }

    /**
     * Sets the value of the totalCreditExposure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalCreditExposure(String value) {
        this.totalCreditExposure = value;
    }

    /**
     * Gets the value of the totalCreditExposurePercentage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalCreditExposurePercentage() {
        return totalCreditExposurePercentage;
    }

    /**
     * Sets the value of the totalCreditExposurePercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalCreditExposurePercentage(String value) {
        this.totalCreditExposurePercentage = value;
    }

    /**
     * Gets the value of the totalCreditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalCreditLimit() {
        return totalCreditLimit;
    }

    /**
     * Sets the value of the totalCreditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalCreditLimit(String value) {
        this.totalCreditLimit = value;
    }

    /**
     * Gets the value of the balanceCreditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalanceCreditLimit() {
        return balanceCreditLimit;
    }

    /**
     * Sets the value of the balanceCreditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalanceCreditLimit(String value) {
        this.balanceCreditLimit = value;
    }

    /**
     * Gets the value of the balanceCreditLimitPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalanceCreditLimitPercentage() {
        return balanceCreditLimitPercentage;
    }

    /**
     * Sets the value of the balanceCreditLimitPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalanceCreditLimitPercentage(String value) {
        this.balanceCreditLimitPercentage = value;
    }

    /**
     * Gets the value of the creditBlock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditBlock() {
        return creditBlock;
    }

    /**
     * Sets the value of the creditBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditBlock(String value) {
        this.creditBlock = value;
    }

    /**
     * Gets the value of the securityDoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityDoc() {
        return securityDoc;
    }

    /**
     * Sets the value of the securityDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityDoc(String value) {
        this.securityDoc = value;
    }

    /**
     * Gets the value of the securityDocExpiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityDocExpiryDate() {
        return securityDocExpiryDate;
    }

    /**
     * Sets the value of the securityDocExpiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityDocExpiryDate(String value) {
        this.securityDocExpiryDate = value;
    }

}
