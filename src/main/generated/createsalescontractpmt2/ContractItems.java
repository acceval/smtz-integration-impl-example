
package createsalescontractpmt2;

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
 *         &lt;element name="POItemNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Product" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Plant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="QuantityUOM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Route" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvoicePrice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ClearanceCost" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ClearanceCostCurr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LocShpmtCost" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LocShpmtCostCurr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InbInsurance" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InbInsuranceCurr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InbSurveryor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InbSurveryorCurr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PriceCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="StorageLocation" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Palletize" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AlphaPrice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FloorPrice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "poItemNumber",
    "product",
    "plant",
    "quantity",
    "quantityUOM",
    "route",
    "invoicePrice",
    "clearanceCost",
    "clearanceCostCurr",
    "locShpmtCost",
    "locShpmtCostCurr",
    "inbInsurance",
    "inbInsuranceCurr",
    "inbSurveryor",
    "inbSurveryorCurr",
    "priceCurrency",
    "storageLocation",
    "palletize",
    "alphaPrice",
    "floorPrice"
})
public class ContractItems {

    @XmlElement(name = "POItemNumber", required = true, nillable = true)
    protected String poItemNumber;
    @XmlElement(name = "Product", required = true, nillable = true)
    protected String product;
    @XmlElement(name = "Plant", required = true, nillable = true)
    protected String plant;
    @XmlElement(name = "Quantity", required = true, nillable = true)
    protected String quantity;
    @XmlElement(name = "QuantityUOM", required = true, nillable = true)
    protected String quantityUOM;
    @XmlElement(name = "Route", required = true, nillable = true)
    protected String route;
    @XmlElement(name = "InvoicePrice", required = true, nillable = true)
    protected String invoicePrice;
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
    @XmlElement(name = "PriceCurrency", required = true, nillable = true)
    protected String priceCurrency;
    @XmlElement(name = "StorageLocation", required = true, nillable = true)
    protected String storageLocation;
    @XmlElement(name = "Palletize", required = true, nillable = true)
    protected String palletize;
    @XmlElement(name = "AlphaPrice", required = true, nillable = true)
    protected String alphaPrice;
    @XmlElement(name = "FloorPrice", required = true, nillable = true)
    protected String floorPrice;

    /**
     * Gets the value of the poItemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOItemNumber() {
        return poItemNumber;
    }

    /**
     * Sets the value of the poItemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOItemNumber(String value) {
        this.poItemNumber = value;
    }

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
     * Gets the value of the plant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlant() {
        return plant;
    }

    /**
     * Sets the value of the plant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlant(String value) {
        this.plant = value;
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
     * Gets the value of the route property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoute() {
        return route;
    }

    /**
     * Sets the value of the route property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoute(String value) {
        this.route = value;
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
     * Gets the value of the storageLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageLocation() {
        return storageLocation;
    }

    /**
     * Sets the value of the storageLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageLocation(String value) {
        this.storageLocation = value;
    }

    /**
     * Gets the value of the palletize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPalletize() {
        return palletize;
    }

    /**
     * Sets the value of the palletize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPalletize(String value) {
        this.palletize = value;
    }

    /**
     * Gets the value of the alphaPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlphaPrice() {
        return alphaPrice;
    }

    /**
     * Sets the value of the alphaPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlphaPrice(String value) {
        this.alphaPrice = value;
    }

    /**
     * Gets the value of the floorPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloorPrice() {
        return floorPrice;
    }

    /**
     * Sets the value of the floorPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloorPrice(String value) {
        this.floorPrice = value;
    }

}
