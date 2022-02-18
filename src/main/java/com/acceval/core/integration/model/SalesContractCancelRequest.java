package com.acceval.core.integration.model;

public class SalesContractCancelRequest {
    private String salesContractNumber;
    private String pmtItemNo;
    private String productCode;

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
}
