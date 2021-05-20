package com.acceval.core.integration.model;

import java.time.LocalDate;
import java.util.List;

public class SalesContractCreateRequest {

    private ContractHeader contractHeader;
    private List<ContractItem> contractItems;

    public ContractHeader getContractHeader() {
        return contractHeader;
    }

    public void setContractHeader(ContractHeader contractHeader) {
        this.contractHeader = contractHeader;
    }

    public List<ContractItem> getContractItems() {
        return contractItems;
    }

    public void setContractItems(List<ContractItem> contractItems) {
        this.contractItems = contractItems;
    }

    public static class ContractHeader {

        private String soldTo;
        private String shipTo;

        private String salesOrganisation;
        private String distributionChannel;
        private String salesDivision;
        private String salesGroup;
        private String salesOffice;
        private LocalDate purchaseDate;
        private String priceList;
        private String incoterms1;
        private String incoterms2;
        private String paymentTerm;
        private LocalDate priceDate;
        private LocalDate validFrom;
        private LocalDate validTo;
        private String salesDocFullNumber;
        private LocalDate poDate;
        private String shippingCondition;
        private String customerType;
        private String priceCurrency;
        private LocalDate reta;
        private String contractType;
        private double exchangeRate;
        private double exchangeRateFI;

        public String getSoldTo() {
            return soldTo;
        }

        public void setSoldTo(String soldTo) {
            this.soldTo = soldTo;
        }

        public String getShipTo() {
            return shipTo;
        }

        public void setShipTo(String shipTo) {
            this.shipTo = shipTo;
        }

        public String getSalesOrganisation() {
            return salesOrganisation;
        }

        public void setSalesOrganisation(String salesOrganisation) {
            this.salesOrganisation = salesOrganisation;
        }

        public String getDistributionChannel() {
            return distributionChannel;
        }

        public void setDistributionChannel(String distributionChannel) {
            this.distributionChannel = distributionChannel;
        }

        public String getSalesDivision() {
            return salesDivision;
        }

        public void setSalesDivision(String salesDivision) {
            this.salesDivision = salesDivision;
        }

        public String getSalesGroup() {
            return salesGroup;
        }

        public void setSalesGroup(String salesGroup) {
            this.salesGroup = salesGroup;
        }

        public String getSalesOffice() {
            return salesOffice;
        }

        public void setSalesOffice(String salesOffice) {
            this.salesOffice = salesOffice;
        }

        public LocalDate getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(LocalDate purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public String getPriceList() {
            return priceList;
        }

        public void setPriceList(String priceList) {
            this.priceList = priceList;
        }

        public String getIncoterms1() {
            return incoterms1;
        }

        public void setIncoterms1(String incoterms1) {
            this.incoterms1 = incoterms1;
        }

        public String getIncoterms2() {
            return incoterms2;
        }

        public void setIncoterms2(String incoterms2) {
            this.incoterms2 = incoterms2;
        }

        public String getPaymentTerm() {
            return paymentTerm;
        }

        public void setPaymentTerm(String paymentTerm) {
            this.paymentTerm = paymentTerm;
        }

        public LocalDate getPriceDate() {
            return priceDate;
        }

        public void setPriceDate(LocalDate priceDate) {
            this.priceDate = priceDate;
        }

        public LocalDate getValidFrom() {
            return validFrom;
        }

        public void setValidFrom(LocalDate validFrom) {
            this.validFrom = validFrom;
        }

        public LocalDate getValidTo() {
            return validTo;
        }

        public void setValidTo(LocalDate validTo) {
            this.validTo = validTo;
        }

        public String getSalesDocFullNumber() {
            return salesDocFullNumber;
        }

        public void setSalesDocFullNumber(String salesDocFullNumber) {
            this.salesDocFullNumber = salesDocFullNumber;
        }

        public LocalDate getPoDate() {
            return poDate;
        }

        public void setPoDate(LocalDate poDate) {
            this.poDate = poDate;
        }

        public String getShippingCondition() {
            return shippingCondition;
        }

        public void setShippingCondition(String shippingCondition) {
            this.shippingCondition = shippingCondition;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getPriceCurrency() {
            return priceCurrency;
        }

        public void setPriceCurrency(String priceCurrency) {
            this.priceCurrency = priceCurrency;
        }

        public LocalDate getReta() {
            return reta;
        }

        public void setReta(LocalDate reta) {
            this.reta = reta;
        }

        public String getContractType() {
            return contractType;
        }

        public void setContractType(String contractType) {
            this.contractType = contractType;
        }

        public double getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(double exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        public double getExchangeRateFI() {
            return exchangeRateFI;
        }

        public void setExchangeRateFI(double exchangeRateFI) {
            this.exchangeRateFI = exchangeRateFI;
        }
    }
    
    public static class ContractItem {

        private String itemNumber;
        private String product;
        private String plant;
        private double quantity;
        private String quantityUOM;
        private String route;
        private double invoicePrice;
        private String priceCurrency;
        private String palletize;
        private double alphaPrice;
        private double floorPrice;

        public String getItemNumber() {
            return itemNumber;
        }

        public void setItemNumber(String itemNumber) {
            this.itemNumber = itemNumber;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getPlant() {
            return plant;
        }

        public void setPlant(String plant) {
            this.plant = plant;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public String getQuantityUOM() {
            return quantityUOM;
        }

        public void setQuantityUOM(String quantityUOM) {
            this.quantityUOM = quantityUOM;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public double getInvoicePrice() {
            return invoicePrice;
        }

        public void setInvoicePrice(double invoicePrice) {
            this.invoicePrice = invoicePrice;
        }

        public String getPriceCurrency() {
            return priceCurrency;
        }

        public void setPriceCurrency(String priceCurrency) {
            this.priceCurrency = priceCurrency;
        }

        public String getPalletize() {
            return palletize;
        }

        public void setPalletize(String palletize) {
            this.palletize = palletize;
        }

        public double getAlphaPrice() {
            return alphaPrice;
        }

        public void setAlphaPrice(double alphaPrice) {
            this.alphaPrice = alphaPrice;
        }

        public double getFloorPrice() {
            return floorPrice;
        }

        public void setFloorPrice(double floorPrice) {
            this.floorPrice = floorPrice;
        }
    }

}
