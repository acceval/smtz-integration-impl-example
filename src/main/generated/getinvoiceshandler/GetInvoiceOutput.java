
package getinvoiceshandler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetInvoiceOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetInvoiceOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EntryTime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EntryDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesOrganization" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DistributionChannel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Division" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesOffice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BillingDocument" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BillingItem" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BillingType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PricingDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SoldtoParty" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ShipTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BillTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Payer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CountryDestination" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TermsPaymentKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DocumentCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ExchangeRateFIPostings" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ActualInvoicedQuantity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesUnit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MaterialNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Plant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesGroup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvoicePrice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ConditionUnitInDocument" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvoicePriceQuantity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InvoicePriceCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ReferenceDocumentID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ReferenceDocumentItemID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RequestedDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BillingDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ShippingConditions" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ShipToCity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Incoterms" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DestinationPort" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesOrderDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OriginPort" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RegionPlant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesOrderCreationDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesContractNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DocumentNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DocumentItemNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ETA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="StorageLocation" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DocumentQuantity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DocumentValue" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BillingItemValue" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Palletize" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SDDocumentCategory" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CancelledBillingDocNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MaterialEntered" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ConditionExchangeRate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NetWeight" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PurchaseOrderDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PriceListType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="GoodsMovementDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesOrderNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TaxAmount" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TaxCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndOfLoadingDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ItemCategory" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OrderReason" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetInvoiceOutput", propOrder = {
    "entryTime",
    "entryDate",
    "salesOrganization",
    "distributionChannel",
    "division",
    "salesOffice",
    "billingDocument",
    "billingItem",
    "billingType",
    "pricingDate",
    "soldtoParty",
    "shipTo",
    "billTo",
    "payer",
    "countryDestination",
    "termsPaymentKey",
    "documentCurrency",
    "exchangeRateFIPostings",
    "actualInvoicedQuantity",
    "salesUnit",
    "materialNumber",
    "plant",
    "salesGroup",
    "invoicePrice",
    "conditionUnitInDocument",
    "invoicePriceQuantity",
    "invoicePriceCurrency",
    "referenceDocumentID",
    "referenceDocumentItemID",
    "requestedDeliveryDate",
    "billingDate",
    "shippingConditions",
    "shipToCity",
    "incoterms",
    "destinationPort",
    "salesOrderDate",
    "originPort",
    "regionPlant",
    "salesOrderCreationDate",
    "salesContractNo",
    "documentNo",
    "documentItemNo",
    "eta",
    "storageLocation",
    "documentQuantity",
    "documentValue",
    "billingItemValue",
    "palletize",
    "sdDocumentCategory",
    "cancelledBillingDocNo",
    "materialEntered",
    "conditionExchangeRate",
    "netWeight",
    "purchaseOrderDate",
    "priceListType",
    "goodsMovementDate",
    "salesOrderNo",
    "taxAmount",
    "taxCurrency",
    "endOfLoadingDate",
    "itemCategory",
    "orderReason"
})
public class GetInvoiceOutput {

    @XmlElement(name = "EntryTime", required = true, nillable = true)
    protected String entryTime;
    @XmlElement(name = "EntryDate", required = true, nillable = true)
    protected String entryDate;
    @XmlElement(name = "SalesOrganization", required = true, nillable = true)
    protected String salesOrganization;
    @XmlElement(name = "DistributionChannel", required = true, nillable = true)
    protected String distributionChannel;
    @XmlElement(name = "Division", required = true, nillable = true)
    protected String division;
    @XmlElement(name = "SalesOffice", required = true, nillable = true)
    protected String salesOffice;
    @XmlElement(name = "BillingDocument", required = true, nillable = true)
    protected String billingDocument;
    @XmlElement(name = "BillingItem", required = true, nillable = true)
    protected String billingItem;
    @XmlElement(name = "BillingType", required = true, nillable = true)
    protected String billingType;
    @XmlElement(name = "PricingDate", required = true, nillable = true)
    protected String pricingDate;
    @XmlElement(name = "SoldtoParty", required = true, nillable = true)
    protected String soldtoParty;
    @XmlElement(name = "ShipTo", required = true, nillable = true)
    protected String shipTo;
    @XmlElement(name = "BillTo", required = true, nillable = true)
    protected String billTo;
    @XmlElement(name = "Payer", required = true, nillable = true)
    protected String payer;
    @XmlElement(name = "CountryDestination", required = true, nillable = true)
    protected String countryDestination;
    @XmlElement(name = "TermsPaymentKey", required = true, nillable = true)
    protected String termsPaymentKey;
    @XmlElement(name = "DocumentCurrency", required = true, nillable = true)
    protected String documentCurrency;
    @XmlElement(name = "ExchangeRateFIPostings", required = true, nillable = true)
    protected String exchangeRateFIPostings;
    @XmlElement(name = "ActualInvoicedQuantity", required = true, nillable = true)
    protected String actualInvoicedQuantity;
    @XmlElement(name = "SalesUnit", required = true, nillable = true)
    protected String salesUnit;
    @XmlElement(name = "MaterialNumber", required = true, nillable = true)
    protected String materialNumber;
    @XmlElement(name = "Plant", required = true, nillable = true)
    protected String plant;
    @XmlElement(name = "SalesGroup", required = true, nillable = true)
    protected String salesGroup;
    @XmlElement(name = "InvoicePrice", required = true, nillable = true)
    protected String invoicePrice;
    @XmlElement(name = "ConditionUnitInDocument", required = true, nillable = true)
    protected String conditionUnitInDocument;
    @XmlElement(name = "InvoicePriceQuantity", required = true, nillable = true)
    protected String invoicePriceQuantity;
    @XmlElement(name = "InvoicePriceCurrency", required = true, nillable = true)
    protected String invoicePriceCurrency;
    @XmlElement(name = "ReferenceDocumentID", required = true, nillable = true)
    protected String referenceDocumentID;
    @XmlElement(name = "ReferenceDocumentItemID", required = true, nillable = true)
    protected String referenceDocumentItemID;
    @XmlElement(name = "RequestedDeliveryDate", required = true, nillable = true)
    protected String requestedDeliveryDate;
    @XmlElement(name = "BillingDate", required = true, nillable = true)
    protected String billingDate;
    @XmlElement(name = "ShippingConditions", required = true, nillable = true)
    protected String shippingConditions;
    @XmlElement(name = "ShipToCity", required = true, nillable = true)
    protected String shipToCity;
    @XmlElement(name = "Incoterms", required = true, nillable = true)
    protected String incoterms;
    @XmlElement(name = "DestinationPort", required = true, nillable = true)
    protected String destinationPort;
    @XmlElement(name = "SalesOrderDate", required = true, nillable = true)
    protected String salesOrderDate;
    @XmlElement(name = "OriginPort", required = true, nillable = true)
    protected String originPort;
    @XmlElement(name = "RegionPlant", required = true, nillable = true)
    protected String regionPlant;
    @XmlElement(name = "SalesOrderCreationDate", required = true, nillable = true)
    protected String salesOrderCreationDate;
    @XmlElement(name = "SalesContractNo", required = true, nillable = true)
    protected String salesContractNo;
    @XmlElement(name = "DocumentNo", required = true, nillable = true)
    protected String documentNo;
    @XmlElement(name = "DocumentItemNo", required = true, nillable = true)
    protected String documentItemNo;
    @XmlElement(name = "ETA", required = true, nillable = true)
    protected String eta;
    @XmlElement(name = "StorageLocation", required = true, nillable = true)
    protected String storageLocation;
    @XmlElement(name = "DocumentQuantity", required = true, nillable = true)
    protected String documentQuantity;
    @XmlElement(name = "DocumentValue", required = true, nillable = true)
    protected String documentValue;
    @XmlElement(name = "BillingItemValue", required = true, nillable = true)
    protected String billingItemValue;
    @XmlElement(name = "Palletize", required = true, nillable = true)
    protected String palletize;
    @XmlElement(name = "SDDocumentCategory", required = true, nillable = true)
    protected String sdDocumentCategory;
    @XmlElement(name = "CancelledBillingDocNo", required = true, nillable = true)
    protected String cancelledBillingDocNo;
    @XmlElement(name = "MaterialEntered", required = true, nillable = true)
    protected String materialEntered;
    @XmlElement(name = "ConditionExchangeRate", required = true, nillable = true)
    protected String conditionExchangeRate;
    @XmlElement(name = "NetWeight", required = true, nillable = true)
    protected String netWeight;
    @XmlElement(name = "PurchaseOrderDate", required = true, nillable = true)
    protected String purchaseOrderDate;
    @XmlElement(name = "PriceListType", required = true, nillable = true)
    protected String priceListType;
    @XmlElement(name = "GoodsMovementDate", required = true, nillable = true)
    protected String goodsMovementDate;
    @XmlElement(name = "SalesOrderNo", required = true, nillable = true)
    protected String salesOrderNo;
    @XmlElement(name = "TaxAmount", required = true, nillable = true)
    protected String taxAmount;
    @XmlElement(name = "TaxCurrency", required = true, nillable = true)
    protected String taxCurrency;
    @XmlElement(name = "EndOfLoadingDate", required = true, nillable = true)
    protected String endOfLoadingDate;
    @XmlElement(name = "ItemCategory", required = true, nillable = true)
    protected String itemCategory;
    @XmlElement(name = "OrderReason", required = true, nillable = true)
    protected String orderReason;

    /**
     * Gets the value of the entryTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryTime() {
        return entryTime;
    }

    /**
     * Sets the value of the entryTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryTime(String value) {
        this.entryTime = value;
    }

    /**
     * Gets the value of the entryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     * Sets the value of the entryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryDate(String value) {
        this.entryDate = value;
    }

    /**
     * Gets the value of the salesOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesOrganization() {
        return salesOrganization;
    }

    /**
     * Sets the value of the salesOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesOrganization(String value) {
        this.salesOrganization = value;
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
     * Gets the value of the division property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the value of the division property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDivision(String value) {
        this.division = value;
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
     * Gets the value of the billingDocument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingDocument() {
        return billingDocument;
    }

    /**
     * Sets the value of the billingDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingDocument(String value) {
        this.billingDocument = value;
    }

    /**
     * Gets the value of the billingItem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingItem() {
        return billingItem;
    }

    /**
     * Sets the value of the billingItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingItem(String value) {
        this.billingItem = value;
    }

    /**
     * Gets the value of the billingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingType() {
        return billingType;
    }

    /**
     * Sets the value of the billingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingType(String value) {
        this.billingType = value;
    }

    /**
     * Gets the value of the pricingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingDate() {
        return pricingDate;
    }

    /**
     * Sets the value of the pricingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingDate(String value) {
        this.pricingDate = value;
    }

    /**
     * Gets the value of the soldtoParty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoldtoParty() {
        return soldtoParty;
    }

    /**
     * Sets the value of the soldtoParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoldtoParty(String value) {
        this.soldtoParty = value;
    }

    /**
     * Gets the value of the shipTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipTo() {
        return shipTo;
    }

    /**
     * Sets the value of the shipTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipTo(String value) {
        this.shipTo = value;
    }

    /**
     * Gets the value of the billTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillTo() {
        return billTo;
    }

    /**
     * Sets the value of the billTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillTo(String value) {
        this.billTo = value;
    }

    /**
     * Gets the value of the payer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayer() {
        return payer;
    }

    /**
     * Sets the value of the payer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayer(String value) {
        this.payer = value;
    }

    /**
     * Gets the value of the countryDestination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryDestination() {
        return countryDestination;
    }

    /**
     * Sets the value of the countryDestination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryDestination(String value) {
        this.countryDestination = value;
    }

    /**
     * Gets the value of the termsPaymentKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermsPaymentKey() {
        return termsPaymentKey;
    }

    /**
     * Sets the value of the termsPaymentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermsPaymentKey(String value) {
        this.termsPaymentKey = value;
    }

    /**
     * Gets the value of the documentCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentCurrency() {
        return documentCurrency;
    }

    /**
     * Sets the value of the documentCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentCurrency(String value) {
        this.documentCurrency = value;
    }

    /**
     * Gets the value of the exchangeRateFIPostings property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchangeRateFIPostings() {
        return exchangeRateFIPostings;
    }

    /**
     * Sets the value of the exchangeRateFIPostings property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchangeRateFIPostings(String value) {
        this.exchangeRateFIPostings = value;
    }

    /**
     * Gets the value of the actualInvoicedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActualInvoicedQuantity() {
        return actualInvoicedQuantity;
    }

    /**
     * Sets the value of the actualInvoicedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActualInvoicedQuantity(String value) {
        this.actualInvoicedQuantity = value;
    }

    /**
     * Gets the value of the salesUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesUnit() {
        return salesUnit;
    }

    /**
     * Sets the value of the salesUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesUnit(String value) {
        this.salesUnit = value;
    }

    /**
     * Gets the value of the materialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialNumber() {
        return materialNumber;
    }

    /**
     * Sets the value of the materialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialNumber(String value) {
        this.materialNumber = value;
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
     * Gets the value of the conditionUnitInDocument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionUnitInDocument() {
        return conditionUnitInDocument;
    }

    /**
     * Sets the value of the conditionUnitInDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionUnitInDocument(String value) {
        this.conditionUnitInDocument = value;
    }

    /**
     * Gets the value of the invoicePriceQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoicePriceQuantity() {
        return invoicePriceQuantity;
    }

    /**
     * Sets the value of the invoicePriceQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoicePriceQuantity(String value) {
        this.invoicePriceQuantity = value;
    }

    /**
     * Gets the value of the invoicePriceCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoicePriceCurrency() {
        return invoicePriceCurrency;
    }

    /**
     * Sets the value of the invoicePriceCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoicePriceCurrency(String value) {
        this.invoicePriceCurrency = value;
    }

    /**
     * Gets the value of the referenceDocumentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceDocumentID() {
        return referenceDocumentID;
    }

    /**
     * Sets the value of the referenceDocumentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceDocumentID(String value) {
        this.referenceDocumentID = value;
    }

    /**
     * Gets the value of the referenceDocumentItemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceDocumentItemID() {
        return referenceDocumentItemID;
    }

    /**
     * Sets the value of the referenceDocumentItemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceDocumentItemID(String value) {
        this.referenceDocumentItemID = value;
    }

    /**
     * Gets the value of the requestedDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    /**
     * Sets the value of the requestedDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedDeliveryDate(String value) {
        this.requestedDeliveryDate = value;
    }

    /**
     * Gets the value of the billingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingDate() {
        return billingDate;
    }

    /**
     * Sets the value of the billingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingDate(String value) {
        this.billingDate = value;
    }

    /**
     * Gets the value of the shippingConditions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingConditions() {
        return shippingConditions;
    }

    /**
     * Sets the value of the shippingConditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingConditions(String value) {
        this.shippingConditions = value;
    }

    /**
     * Gets the value of the shipToCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipToCity() {
        return shipToCity;
    }

    /**
     * Sets the value of the shipToCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipToCity(String value) {
        this.shipToCity = value;
    }

    /**
     * Gets the value of the incoterms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncoterms() {
        return incoterms;
    }

    /**
     * Sets the value of the incoterms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncoterms(String value) {
        this.incoterms = value;
    }

    /**
     * Gets the value of the destinationPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationPort() {
        return destinationPort;
    }

    /**
     * Sets the value of the destinationPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationPort(String value) {
        this.destinationPort = value;
    }

    /**
     * Gets the value of the salesOrderDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesOrderDate() {
        return salesOrderDate;
    }

    /**
     * Sets the value of the salesOrderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesOrderDate(String value) {
        this.salesOrderDate = value;
    }

    /**
     * Gets the value of the originPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginPort() {
        return originPort;
    }

    /**
     * Sets the value of the originPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginPort(String value) {
        this.originPort = value;
    }

    /**
     * Gets the value of the regionPlant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionPlant() {
        return regionPlant;
    }

    /**
     * Sets the value of the regionPlant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionPlant(String value) {
        this.regionPlant = value;
    }

    /**
     * Gets the value of the salesOrderCreationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesOrderCreationDate() {
        return salesOrderCreationDate;
    }

    /**
     * Sets the value of the salesOrderCreationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesOrderCreationDate(String value) {
        this.salesOrderCreationDate = value;
    }

    /**
     * Gets the value of the salesContractNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesContractNo() {
        return salesContractNo;
    }

    /**
     * Sets the value of the salesContractNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesContractNo(String value) {
        this.salesContractNo = value;
    }

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
     * Gets the value of the documentItemNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentItemNo() {
        return documentItemNo;
    }

    /**
     * Sets the value of the documentItemNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentItemNo(String value) {
        this.documentItemNo = value;
    }

    /**
     * Gets the value of the eta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getETA() {
        return eta;
    }

    /**
     * Sets the value of the eta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setETA(String value) {
        this.eta = value;
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
     * Gets the value of the documentQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentQuantity() {
        return documentQuantity;
    }

    /**
     * Sets the value of the documentQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentQuantity(String value) {
        this.documentQuantity = value;
    }

    /**
     * Gets the value of the documentValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentValue() {
        return documentValue;
    }

    /**
     * Sets the value of the documentValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentValue(String value) {
        this.documentValue = value;
    }

    /**
     * Gets the value of the billingItemValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingItemValue() {
        return billingItemValue;
    }

    /**
     * Sets the value of the billingItemValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingItemValue(String value) {
        this.billingItemValue = value;
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
     * Gets the value of the sdDocumentCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSDDocumentCategory() {
        return sdDocumentCategory;
    }

    /**
     * Sets the value of the sdDocumentCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSDDocumentCategory(String value) {
        this.sdDocumentCategory = value;
    }

    /**
     * Gets the value of the cancelledBillingDocNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancelledBillingDocNo() {
        return cancelledBillingDocNo;
    }

    /**
     * Sets the value of the cancelledBillingDocNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelledBillingDocNo(String value) {
        this.cancelledBillingDocNo = value;
    }

    /**
     * Gets the value of the materialEntered property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialEntered() {
        return materialEntered;
    }

    /**
     * Sets the value of the materialEntered property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialEntered(String value) {
        this.materialEntered = value;
    }

    /**
     * Gets the value of the conditionExchangeRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionExchangeRate() {
        return conditionExchangeRate;
    }

    /**
     * Sets the value of the conditionExchangeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionExchangeRate(String value) {
        this.conditionExchangeRate = value;
    }

    /**
     * Gets the value of the netWeight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetWeight() {
        return netWeight;
    }

    /**
     * Sets the value of the netWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetWeight(String value) {
        this.netWeight = value;
    }

    /**
     * Gets the value of the purchaseOrderDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    /**
     * Sets the value of the purchaseOrderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurchaseOrderDate(String value) {
        this.purchaseOrderDate = value;
    }

    /**
     * Gets the value of the priceListType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceListType() {
        return priceListType;
    }

    /**
     * Sets the value of the priceListType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceListType(String value) {
        this.priceListType = value;
    }

    /**
     * Gets the value of the goodsMovementDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsMovementDate() {
        return goodsMovementDate;
    }

    /**
     * Sets the value of the goodsMovementDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsMovementDate(String value) {
        this.goodsMovementDate = value;
    }

    /**
     * Gets the value of the salesOrderNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    /**
     * Sets the value of the salesOrderNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesOrderNo(String value) {
        this.salesOrderNo = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxAmount(String value) {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the taxCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxCurrency() {
        return taxCurrency;
    }

    /**
     * Sets the value of the taxCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxCurrency(String value) {
        this.taxCurrency = value;
    }

    /**
     * Gets the value of the endOfLoadingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndOfLoadingDate() {
        return endOfLoadingDate;
    }

    /**
     * Sets the value of the endOfLoadingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndOfLoadingDate(String value) {
        this.endOfLoadingDate = value;
    }

    /**
     * Gets the value of the itemCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemCategory() {
        return itemCategory;
    }

    /**
     * Sets the value of the itemCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemCategory(String value) {
        this.itemCategory = value;
    }

    /**
     * Gets the value of the orderReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderReason() {
        return orderReason;
    }

    /**
     * Sets the value of the orderReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderReason(String value) {
        this.orderReason = value;
    }

}
