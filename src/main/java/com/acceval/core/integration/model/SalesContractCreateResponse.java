package com.acceval.core.integration.model;

public class SalesContractCreateResponse {
    private boolean success;
    private String contractNumber;
    private String message;
    private String sapMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSapMessage() {
        return sapMessage;
    }

    public void setSapMessage(String sapMessage) {
        this.sapMessage = sapMessage;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
}
