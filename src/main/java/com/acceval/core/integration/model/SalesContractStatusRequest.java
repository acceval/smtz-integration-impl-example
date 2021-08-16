package com.acceval.core.integration.model;

import java.util.List;

public class SalesContractStatusRequest {
    private List<String> contractNumbers;

    public List<String> getContractNumbers() {
        return contractNumbers;
    }

    public void setContractNumbers(List<String> contractNumbers) {
        this.contractNumbers = contractNumbers;
    }
}
