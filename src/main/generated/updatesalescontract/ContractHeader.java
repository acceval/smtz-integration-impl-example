
package updatesalescontract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Contract_Header complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Contract_Header"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DocumentNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ReasonForRejection" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Activity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ExchangeRate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contract_Header", propOrder = {
    "documentNo",
    "reasonForRejection",
    "activity",
    "exchangeRate"
})
public class ContractHeader {

    @XmlElement(name = "DocumentNo", required = true, nillable = true)
    protected String documentNo;
    @XmlElement(name = "ReasonForRejection", required = true, nillable = true)
    protected String reasonForRejection;
    @XmlElement(name = "Activity", required = true, nillable = true)
    protected String activity;
    @XmlElement(name = "ExchangeRate", required = true, nillable = true)
    protected String exchangeRate;

    /**
     * Gets the value of the documentNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentNo() {
        return documentNo;
    }

    /**
     * Sets the value of the documentNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentNo(String value) {
        this.documentNo = value;
    }

    /**
     * Gets the value of the reasonForRejection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonForRejection() {
        return reasonForRejection;
    }

    /**
     * Sets the value of the reasonForRejection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonForRejection(String value) {
        this.reasonForRejection = value;
    }

    /**
     * Gets the value of the activity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivity() {
        return activity;
    }

    /**
     * Sets the value of the activity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivity(String value) {
        this.activity = value;
    }

    /**
     * Gets the value of the exchangeRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Sets the value of the exchangeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchangeRate(String value) {
        this.exchangeRate = value;
    }

}
