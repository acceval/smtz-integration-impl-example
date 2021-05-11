package com.acceval.core.amqp;

import java.io.Serializable;

public class CompanyPayload<T extends Serializable> implements Serializable {
    private Long companyId;
    private T payload;

    public CompanyPayload() { }

    public CompanyPayload(Long companyId, T payload) {
        this.companyId = companyId;
        this.payload = payload;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
