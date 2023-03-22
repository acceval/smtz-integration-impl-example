
package updatesalescontract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Contract_Items complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Contract_Items"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Product" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="QuantityUOM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvoicePrice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PriceCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ReasonForRejection" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PMTItemNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ClearanceCost" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ClearanceCostCurr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LocShpmtCost" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LocShpmtCostCurr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InbInsurance" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InbInsuranceCurr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InbSurveryor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InbSurveryorCurr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contract_Items", propOrder = {
    "product",
    "quantity",
    "quantityUOM",
    "invoicePrice",
    "priceCurrency",
    "reasonForRejection",
    "pmtItemNo",
    "clearanceCost",
    "clearanceCostCurr",
    "locShpmtCost",
    "locShpmtCostCurr",
    "inbInsurance",
    "inbInsuranceCurr",
    "inbSurveryor",
    "inbSurveryorCurr"
})
public class ContractItems {

    @XmlElement(name = "Product", required = true, nillable = true)
    protected String product;
    @XmlElement(name = "Quantity", required = true, nillable = true)
    protected String quantity;
    @XmlElement(name = "QuantityUOM", required = true, nillable = true)
    protected String quantityUOM;
    @XmlElement(name = "InvoicePrice", required = true, nillable = true)
    protected String invoicePrice;
    @XmlElement(name = "PriceCurrency", required = true, nillable = true)
    protected String priceCurrency;
    @XmlElement(name = "ReasonForRejection", required = true, nillable = true)
    protected String reasonForRejection;
    @XmlElement(name = "PMTItemNo", required = true, nillable = true)
    protected String pmtItemNo;
    @XmlElement(name = "ClearanceCost", required = true, nillable = true)
    protected String clearanceCost;
    @XmlElement(name = "ClearanceCostCurr", required = true, nillable = true)
    protected String clearanceCostCurr;
    @XmlElement(name = "LocShpmtCost", required = true, nillable = true)
    protected String locShpmtCost;
    @XmlElement(name = "LocShpmtCostCurr", required = true, nillable = true)
    protected String locShpmtCostCurr;
    @XmlElement(name = "InbInsurance", required = true, nillable = true)
    protected String inbInsurance;
    @XmlElement(name = "InbInsuranceCurr", required = true, nillable = true)
    protected String inbInsuranceCurr;
    @XmlElement(name = "InbSurveryor", required = true, nillable = true)
    protected String inbSurveryor;
    @XmlElement(name = "InbSurveryorCurr", required = true, nillable = true)
    protected String inbSurveryorCurr;

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProduct(String value) {
        this.product = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the quantityUOM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantityUOM() {
        return quantityUOM;
    }

    /**
     * Sets the value of the quantityUOM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantityUOM(String value) {
        this.quantityUOM = value;
    }

    /**
     * Gets the value of the invoicePrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoicePrice() {
        return invoicePrice;
    }

    /**
     * Sets the value of the invoicePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoicePrice(String value) {
        this.invoicePrice = value;
    }

    /**
     * Gets the value of the priceCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceCurrency() {
        return priceCurrency;
    }

    /**
     * Sets the value of the priceCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceCurrency(String value) {
        this.priceCurrency = value;
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
     * Gets the value of the pmtItemNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPMTItemNo() {
        return pmtItemNo;
    }

    /**
     * Sets the value of the pmtItemNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPMTItemNo(String value) {
        this.pmtItemNo = value;
    }

    /**
     * Gets the value of the clearanceCost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClearanceCost() {
        return clearanceCost;
    }

    /**
     * Sets the value of the clearanceCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClearanceCost(String value) {
        this.clearanceCost = value;
    }

    /**
     * Gets the value of the clearanceCostCurr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClearanceCostCurr() {
        return clearanceCostCurr;
    }

    /**
     * Sets the value of the clearanceCostCurr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClearanceCostCurr(String value) {
        this.clearanceCostCurr = value;
    }

    /**
     * Gets the value of the locShpmtCost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocShpmtCost() {
        return locShpmtCost;
    }

    /**
     * Sets the value of the locShpmtCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocShpmtCost(String value) {
        this.locShpmtCost = value;
    }

    /**
     * Gets the value of the locShpmtCostCurr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocShpmtCostCurr() {
        return locShpmtCostCurr;
    }

    /**
     * Sets the value of the locShpmtCostCurr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocShpmtCostCurr(String value) {
        this.locShpmtCostCurr = value;
    }

    /**
     * Gets the value of the inbInsurance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInbInsurance() {
        return inbInsurance;
    }

    /**
     * Sets the value of the inbInsurance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInbInsurance(String value) {
        this.inbInsurance = value;
    }

    /**
     * Gets the value of the inbInsuranceCurr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInbInsuranceCurr() {
        return inbInsuranceCurr;
    }

    /**
     * Sets the value of the inbInsuranceCurr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInbInsuranceCurr(String value) {
        this.inbInsuranceCurr = value;
    }

    /**
     * Gets the value of the inbSurveryor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInbSurveryor() {
        return inbSurveryor;
    }

    /**
     * Sets the value of the inbSurveryor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInbSurveryor(String value) {
        this.inbSurveryor = value;
    }

    /**
     * Gets the value of the inbSurveryorCurr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInbSurveryorCurr() {
        return inbSurveryorCurr;
    }

    /**
     * Sets the value of the inbSurveryorCurr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInbSurveryorCurr(String value) {
        this.inbSurveryorCurr = value;
    }

}
