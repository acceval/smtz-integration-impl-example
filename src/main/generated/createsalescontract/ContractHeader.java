
package createsalescontract;

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
 *         &lt;element name="SalesOrganisation" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DistributionChannel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesDivision" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesGroup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesOffice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PurchaseDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PriceList" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Incoterms1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Incoterms2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PaymentTerm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PriceDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ValidFrom" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ValidTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TenderType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PONumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PODate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ShippingCondition" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CustomerType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PriceCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RETA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ContractType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ExchangeRate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ExchangeRateFI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "salesOrganisation",
    "distributionChannel",
    "salesDivision",
    "salesGroup",
    "salesOffice",
    "purchaseDate",
    "priceList",
    "incoterms1",
    "incoterms2",
    "paymentTerm",
    "priceDate",
    "validFrom",
    "validTo",
    "tenderType",
    "poNumber",
    "poDate",
    "shippingCondition",
    "customerType",
    "priceCurrency",
    "reta",
    "contractType",
    "exchangeRate",
    "exchangeRateFI"
})
public class ContractHeader {

    @XmlElement(name = "SalesOrganisation", required = true, nillable = true)
    protected String salesOrganisation;
    @XmlElement(name = "DistributionChannel", required = true, nillable = true)
    protected String distributionChannel;
    @XmlElement(name = "SalesDivision", required = true, nillable = true)
    protected String salesDivision;
    @XmlElement(name = "SalesGroup", required = true, nillable = true)
    protected String salesGroup;
    @XmlElement(name = "SalesOffice", required = true, nillable = true)
    protected String salesOffice;
    @XmlElement(name = "PurchaseDate", required = true, nillable = true)
    protected String purchaseDate;
    @XmlElement(name = "PriceList", required = true, nillable = true)
    protected String priceList;
    @XmlElement(name = "Incoterms1", required = true, nillable = true)
    protected String incoterms1;
    @XmlElement(name = "Incoterms2", required = true, nillable = true)
    protected String incoterms2;
    @XmlElement(name = "PaymentTerm", required = true, nillable = true)
    protected String paymentTerm;
    @XmlElement(name = "PriceDate", required = true, nillable = true)
    protected String priceDate;
    @XmlElement(name = "ValidFrom", required = true, nillable = true)
    protected String validFrom;
    @XmlElement(name = "ValidTo", required = true, nillable = true)
    protected String validTo;
    @XmlElement(name = "TenderType", required = true, nillable = true)
    protected String tenderType;
    @XmlElement(name = "PONumber", required = true, nillable = true)
    protected String poNumber;
    @XmlElement(name = "PODate", required = true, nillable = true)
    protected String poDate;
    @XmlElement(name = "ShippingCondition", required = true, nillable = true)
    protected String shippingCondition;
    @XmlElement(name = "CustomerType", required = true, nillable = true)
    protected String customerType;
    @XmlElement(name = "PriceCurrency", required = true, nillable = true)
    protected String priceCurrency;
    @XmlElement(name = "RETA", required = true, nillable = true)
    protected String reta;
    @XmlElement(name = "ContractType", required = true, nillable = true)
    protected String contractType;
    @XmlElement(name = "ExchangeRate", required = true, nillable = true)
    protected String exchangeRate;
    @XmlElement(name = "ExchangeRateFI", required = true, nillable = true)
    protected String exchangeRateFI;

    /**
     * Gets the value of the salesOrganisation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesOrganisation() {
        return salesOrganisation;
    }

    /**
     * Sets the value of the salesOrganisation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesOrganisation(String value) {
        this.salesOrganisation = value;
    }

    /**
     * Gets the value of the distributionChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributionChannel() {
        return distributionChannel;
    }

    /**
     * Sets the value of the distributionChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributionChannel(String value) {
        this.distributionChannel = value;
    }

    /**
     * Gets the value of the salesDivision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesDivision() {
        return salesDivision;
    }

    /**
     * Sets the value of the salesDivision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesDivision(String value) {
        this.salesDivision = value;
    }

    /**
     * Gets the value of the salesGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesGroup() {
        return salesGroup;
    }

    /**
     * Sets the value of the salesGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesGroup(String value) {
        this.salesGroup = value;
    }

    /**
     * Gets the value of the salesOffice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesOffice() {
        return salesOffice;
    }

    /**
     * Sets the value of the salesOffice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesOffice(String value) {
        this.salesOffice = value;
    }

    /**
     * Gets the value of the purchaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets the value of the purchaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurchaseDate(String value) {
        this.purchaseDate = value;
    }

    /**
     * Gets the value of the priceList property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceList() {
        return priceList;
    }

    /**
     * Sets the value of the priceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceList(String value) {
        this.priceList = value;
    }

    /**
     * Gets the value of the incoterms1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncoterms1() {
        return incoterms1;
    }

    /**
     * Sets the value of the incoterms1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncoterms1(String value) {
        this.incoterms1 = value;
    }

    /**
     * Gets the value of the incoterms2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncoterms2() {
        return incoterms2;
    }

    /**
     * Sets the value of the incoterms2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncoterms2(String value) {
        this.incoterms2 = value;
    }

    /**
     * Gets the value of the paymentTerm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentTerm() {
        return paymentTerm;
    }

    /**
     * Sets the value of the paymentTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentTerm(String value) {
        this.paymentTerm = value;
    }

    /**
     * Gets the value of the priceDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceDate() {
        return priceDate;
    }

    /**
     * Sets the value of the priceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceDate(String value) {
        this.priceDate = value;
    }

    /**
     * Gets the value of the validFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the value of the validFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidFrom(String value) {
        this.validFrom = value;
    }

    /**
     * Gets the value of the validTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidTo() {
        return validTo;
    }

    /**
     * Sets the value of the validTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidTo(String value) {
        this.validTo = value;
    }

    /**
     * Gets the value of the tenderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTenderType() {
        return tenderType;
    }

    /**
     * Sets the value of the tenderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTenderType(String value) {
        this.tenderType = value;
    }

    /**
     * Gets the value of the poNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPONumber() {
        return poNumber;
    }

    /**
     * Sets the value of the poNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPONumber(String value) {
        this.poNumber = value;
    }

    /**
     * Gets the value of the poDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPODate() {
        return poDate;
    }

    /**
     * Sets the value of the poDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPODate(String value) {
        this.poDate = value;
    }

    /**
     * Gets the value of the shippingCondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingCondition() {
        return shippingCondition;
    }

    /**
     * Sets the value of the shippingCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingCondition(String value) {
        this.shippingCondition = value;
    }

    /**
     * Gets the value of the customerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * Sets the value of the customerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerType(String value) {
        this.customerType = value;
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
     * Gets the value of the reta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRETA() {
        return reta;
    }

    /**
     * Sets the value of the reta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRETA(String value) {
        this.reta = value;
    }

    /**
     * Gets the value of the contractType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * Sets the value of the contractType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractType(String value) {
        this.contractType = value;
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

    /**
     * Gets the value of the exchangeRateFI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchangeRateFI() {
        return exchangeRateFI;
    }

    /**
     * Sets the value of the exchangeRateFI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchangeRateFI(String value) {
        this.exchangeRateFI = value;
    }

}
