package com.acceval.core.integration.model;

public class CustomerCreditInfoResponse {

    private Double totalCreditExposure;
    private Double totalCreditExposurePercentage;
    private Double totalCreditLimit;
    private Double balanceCreditLimit;
    private Double balanceCreditLimitPercentage;
    private String creditBlock;
    private String currency;

    public Double getTotalCreditExposure() {
        return totalCreditExposure;
    }

    public void setTotalCreditExposure(Double totalCreditExposure) {
        this.totalCreditExposure = totalCreditExposure;
    }

    public Double getTotalCreditExposurePercentage() {
        return totalCreditExposurePercentage;
    }

    public void setTotalCreditExposurePercentage(Double totalCreditExposurePercentage) {
        this.totalCreditExposurePercentage = totalCreditExposurePercentage;
    }

    public Double getTotalCreditLimit() {
        return totalCreditLimit;
    }

    public void setTotalCreditLimit(Double totalCreditLimit) {
        this.totalCreditLimit = totalCreditLimit;
    }

    public Double getBalanceCreditLimit() {
        return balanceCreditLimit;
    }

    public void setBalanceCreditLimit(Double balanceCreditLimit) {
        this.balanceCreditLimit = balanceCreditLimit;
    }

    public Double getBalanceCreditLimitPercentage() {
        return balanceCreditLimitPercentage;
    }

    public void setBalanceCreditLimitPercentage(Double balanceCreditLimitPercentage) {
        this.balanceCreditLimitPercentage = balanceCreditLimitPercentage;
    }

    public String getCreditBlock() {
        return creditBlock;
    }

    public void setCreditBlock(String creditBlock) {
        this.creditBlock = creditBlock;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
