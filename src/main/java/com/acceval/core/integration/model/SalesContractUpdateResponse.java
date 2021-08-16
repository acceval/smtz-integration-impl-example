package com.acceval.core.integration.model;

public class SalesContractUpdateResponse {
    private boolean success;
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
}
