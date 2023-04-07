package ws;

import java.util.List;

public class InvoiceWebServiceRequest {
    List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {

        private String entryTime;
        private String entryDate;
        private String salesOrganization;
        private String distributionChannel;
        private String division;
        private String salesOffice;
        private String billingDocument;
        private String billingItem;
        private String billingType;
        private String pricingDate;
        private String soldtoParty;
        private String shipTo;
        private String billTo;
        private String payer;
        private String countryDestination;
        private String termsPaymentKey;
        private String documentCurrency;
        private String exchangeRateFIPostings;
        private String actualInvoicedQuantity;
        private String salesUnit;
        private String materialNumber;
        private String plant;
        private String salesGroup;
        private String invoicePrice;
        private String conditionUnitInDocument;
        private String invoicePriceQuantity;
        private String invoicePriceCurrency;
        private String referenceDocumentID;
        private String referenceDocumentItemID;
        private String requestedDeliveryDate;
        private String billingDate;
        private String shippingConditions;
        private String shipToCity;
        private String incoterms;
        private String destinationPort;
        private String salesOrderDate;
        private String originPort;
        private String regionPlant;
        private String salesOrderCreationDate;
        private String salesContractNo;
        private String documentNo;
        private String documentItemNo;
        private String eta;
        private String storageLocation;
        private String documentQuantity;
        private String documentValue;
        private String billingItemValue;
        private String palletize;
        private String sdDocumentCategory;
        private String cancelledBillingDocNo;
        private String materialEntered;
        private String conditionExchangeRate;
        private String netWeight;
        private String purchaseOrderDate;
        private String priceListType;
        private String goodsMovementDate;
        private String salesOrderNo;
        private String taxAmount;
        private String taxCurrency;
        private String endOfLoadingDate;
        private String itemCategory;
        private String orderReason;

        public String getEntryTime() {
            return entryTime;
        }

        public void setEntryTime(String entryTime) {
            this.entryTime = entryTime;
        }

        public String getEntryDate() {
            return entryDate;
        }

        public void setEntryDate(String entryDate) {
            this.entryDate = entryDate;
        }

        public String getSalesOrganization() {
            return salesOrganization;
        }

        public void setSalesOrganization(String salesOrganization) {
            this.salesOrganization = salesOrganization;
        }

        public String getDistributionChannel() {
            return distributionChannel;
        }

        public void setDistributionChannel(String distributionChannel) {
            this.distributionChannel = distributionChannel;
        }

        public String getDivision() {
            return division;
        }

        public void setDivision(String division) {
            this.division = division;
        }

        public String getSalesOffice() {
            return salesOffice;
        }

        public void setSalesOffice(String salesOffice) {
            this.salesOffice = salesOffice;
        }

        public String getBillingDocument() {
            return billingDocument;
        }

        public void setBillingDocument(String billingDocument) {
            this.billingDocument = billingDocument;
        }

        public String getBillingItem() {
            return billingItem;
        }

        public void setBillingItem(String billingItem) {
            this.billingItem = billingItem;
        }

        public String getBillingType() {
            return billingType;
        }

        public void setBillingType(String billingType) {
            this.billingType = billingType;
        }

        public String getPricingDate() {
            return pricingDate;
        }

        public void setPricingDate(String pricingDate) {
            this.pricingDate = pricingDate;
        }

        public String getSoldtoParty() {
            return soldtoParty;
        }

        public void setSoldtoParty(String soldtoParty) {
            this.soldtoParty = soldtoParty;
        }

        public String getShipTo() {
            return shipTo;
        }

        public void setShipTo(String shipTo) {
            this.shipTo = shipTo;
        }

        public String getBillTo() {
            return billTo;
        }

        public void setBillTo(String billTo) {
            this.billTo = billTo;
        }

        public String getPayer() {
            return payer;
        }

        public void setPayer(String payer) {
            this.payer = payer;
        }

        public String getCountryDestination() {
            return countryDestination;
        }

        public void setCountryDestination(String countryDestination) {
            this.countryDestination = countryDestination;
        }

        public String getTermsPaymentKey() {
            return termsPaymentKey;
        }

        public void setTermsPaymentKey(String termsPaymentKey) {
            this.termsPaymentKey = termsPaymentKey;
        }

        public String getDocumentCurrency() {
            return documentCurrency;
        }

        public void setDocumentCurrency(String documentCurrency) {
            this.documentCurrency = documentCurrency;
        }

        public String getExchangeRateFIPostings() {
            return exchangeRateFIPostings;
        }

        public void setExchangeRateFIPostings(String exchangeRateFIPostings) {
            this.exchangeRateFIPostings = exchangeRateFIPostings;
        }

        public String getActualInvoicedQuantity() {
            return actualInvoicedQuantity;
        }

        public void setActualInvoicedQuantity(String actualInvoicedQuantity) {
            this.actualInvoicedQuantity = actualInvoicedQuantity;
        }

        public String getSalesUnit() {
            return salesUnit;
        }

        public void setSalesUnit(String salesUnit) {
            this.salesUnit = salesUnit;
        }

        public String getMaterialNumber() {
            return materialNumber;
        }

        public void setMaterialNumber(String materialNumber) {
            this.materialNumber = materialNumber;
        }

        public String getPlant() {
            return plant;
        }

        public void setPlant(String plant) {
            this.plant = plant;
        }

        public String getSalesGroup() {
            return salesGroup;
        }

        public void setSalesGroup(String salesGroup) {
            this.salesGroup = salesGroup;
        }

        public String getInvoicePrice() {
            return invoicePrice;
        }

        public void setInvoicePrice(String invoicePrice) {
            this.invoicePrice = invoicePrice;
        }

        public String getConditionUnitInDocument() {
            return conditionUnitInDocument;
        }

        public void setConditionUnitInDocument(String conditionUnitInDocument) {
            this.conditionUnitInDocument = conditionUnitInDocument;
        }

        public String getInvoicePriceQuantity() {
            return invoicePriceQuantity;
        }

        public void setInvoicePriceQuantity(String invoicePriceQuantity) {
            this.invoicePriceQuantity = invoicePriceQuantity;
        }

        public String getInvoicePriceCurrency() {
            return invoicePriceCurrency;
        }

        public void setInvoicePriceCurrency(String invoicePriceCurrency) {
            this.invoicePriceCurrency = invoicePriceCurrency;
        }

        public String getReferenceDocumentID() {
            return referenceDocumentID;
        }

        public void setReferenceDocumentID(String referenceDocumentID) {
            this.referenceDocumentID = referenceDocumentID;
        }

        public String getReferenceDocumentItemID() {
            return referenceDocumentItemID;
        }

        public void setReferenceDocumentItemID(String referenceDocumentItemID) {
            this.referenceDocumentItemID = referenceDocumentItemID;
        }

        public String getRequestedDeliveryDate() {
            return requestedDeliveryDate;
        }

        public void setRequestedDeliveryDate(String requestedDeliveryDate) {
            this.requestedDeliveryDate = requestedDeliveryDate;
        }

        public String getBillingDate() {
            return billingDate;
        }

        public void setBillingDate(String billingDate) {
            this.billingDate = billingDate;
        }

        public String getShippingConditions() {
            return shippingConditions;
        }

        public void setShippingConditions(String shippingConditions) {
            this.shippingConditions = shippingConditions;
        }

        public String getShipToCity() {
            return shipToCity;
        }

        public void setShipToCity(String shipToCity) {
            this.shipToCity = shipToCity;
        }

        public String getIncoterms() {
            return incoterms;
        }

        public void setIncoterms(String incoterms) {
            this.incoterms = incoterms;
        }

        public String getDestinationPort() {
            return destinationPort;
        }

        public void setDestinationPort(String destinationPort) {
            this.destinationPort = destinationPort;
        }

        public String getSalesOrderDate() {
            return salesOrderDate;
        }

        public void setSalesOrderDate(String salesOrderDate) {
            this.salesOrderDate = salesOrderDate;
        }

        public String getOriginPort() {
            return originPort;
        }

        public void setOriginPort(String originPort) {
            this.originPort = originPort;
        }

        public String getRegionPlant() {
            return regionPlant;
        }

        public void setRegionPlant(String regionPlant) {
            this.regionPlant = regionPlant;
        }

        public String getSalesOrderCreationDate() {
            return salesOrderCreationDate;
        }

        public void setSalesOrderCreationDate(String salesOrderCreationDate) {
            this.salesOrderCreationDate = salesOrderCreationDate;
        }

        public String getSalesContractNo() {
            return salesContractNo;
        }

        public void setSalesContractNo(String salesContractNo) {
            this.salesContractNo = salesContractNo;
        }

        public String getDocumentNo() {
            return documentNo;
        }

        public void setDocumentNo(String documentNo) {
            this.documentNo = documentNo;
        }

        public String getDocumentItemNo() {
            return documentItemNo;
        }

        public void setDocumentItemNo(String documentItemNo) {
            this.documentItemNo = documentItemNo;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

        public String getStorageLocation() {
            return storageLocation;
        }

        public void setStorageLocation(String storageLocation) {
            this.storageLocation = storageLocation;
        }

        public String getDocumentQuantity() {
            return documentQuantity;
        }

        public void setDocumentQuantity(String documentQuantity) {
            this.documentQuantity = documentQuantity;
        }

        public String getDocumentValue() {
            return documentValue;
        }

        public void setDocumentValue(String documentValue) {
            this.documentValue = documentValue;
        }

        public String getBillingItemValue() {
            return billingItemValue;
        }

        public void setBillingItemValue(String billingItemValue) {
            this.billingItemValue = billingItemValue;
        }

        public String getPalletize() {
            return palletize;
        }

        public void setPalletize(String palletize) {
            this.palletize = palletize;
        }

        public String getSdDocumentCategory() {
            return sdDocumentCategory;
        }

        public void setSdDocumentCategory(String sdDocumentCategory) {
            this.sdDocumentCategory = sdDocumentCategory;
        }

        public String getCancelledBillingDocNo() {
            return cancelledBillingDocNo;
        }

        public void setCancelledBillingDocNo(String cancelledBillingDocNo) {
            this.cancelledBillingDocNo = cancelledBillingDocNo;
        }

        public String getMaterialEntered() {
            return materialEntered;
        }

        public void setMaterialEntered(String materialEntered) {
            this.materialEntered = materialEntered;
        }

        public String getConditionExchangeRate() {
            return conditionExchangeRate;
        }

        public void setConditionExchangeRate(String conditionExchangeRate) {
            this.conditionExchangeRate = conditionExchangeRate;
        }

        public String getNetWeight() {
            return netWeight;
        }

        public void setNetWeight(String netWeight) {
            this.netWeight = netWeight;
        }

        public String getPurchaseOrderDate() {
            return purchaseOrderDate;
        }

        public void setPurchaseOrderDate(String purchaseOrderDate) {
            this.purchaseOrderDate = purchaseOrderDate;
        }

        public String getPriceListType() {
            return priceListType;
        }

        public void setPriceListType(String priceListType) {
            this.priceListType = priceListType;
        }

        public String getGoodsMovementDate() {
            return goodsMovementDate;
        }

        public void setGoodsMovementDate(String goodsMovementDate) {
            this.goodsMovementDate = goodsMovementDate;
        }

        public String getSalesOrderNo() {
            return salesOrderNo;
        }

        public void setSalesOrderNo(String salesOrderNo) {
            this.salesOrderNo = salesOrderNo;
        }

        public String getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(String taxAmount) {
            this.taxAmount = taxAmount;
        }

        public String getTaxCurrency() {
            return taxCurrency;
        }

        public void setTaxCurrency(String taxCurrency) {
            this.taxCurrency = taxCurrency;
        }

        public String getEndOfLoadingDate() {
            return endOfLoadingDate;
        }

        public void setEndOfLoadingDate(String endOfLoadingDate) {
            this.endOfLoadingDate = endOfLoadingDate;
        }

        public String getItemCategory() {
            return itemCategory;
        }

        public void setItemCategory(String itemCategory) {
            this.itemCategory = itemCategory;
        }

        public String getOrderReason() {
            return orderReason;
        }

        public void setOrderReason(String orderReason) {
            this.orderReason = orderReason;
        }
    }
}
