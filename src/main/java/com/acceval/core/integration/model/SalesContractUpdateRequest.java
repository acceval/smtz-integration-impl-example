package com.acceval.core.integration.model;

public class SalesContractUpdateRequest {
    private String salesContractNumber;
    private String pmtItemNo;
    private String productCode;
    private double quantity;
    private double invoicePrice;
    private String priceCurrency;

    public String getSalesContractNumber() {
        return salesContractNumber;
    }

    public void setSalesContractNumber(String salesContractNumber) {
        this.salesContractNumber = salesContractNumber;
    }

    public String getPmtItemNo() {
        return pmtItemNo;
    }

    public void setPmtItemNo(String pmtItemNo) {
        this.pmtItemNo = pmtItemNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
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
}
