package com.acceval.core.cache.model;

import java.io.Serializable;

public class ExchangeRateType implements Serializable {

    private static final long serialVersionUID = -4822694288928607480L;

    private long exchangeRateTypeID;
    private String code;
    private String name;

    public long getExchangeRateTypeID() {
        return exchangeRateTypeID;
    }

    public void setExchangeRateTypeID(long exchangeRateTypeID) {
        this.exchangeRateTypeID = exchangeRateTypeID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
