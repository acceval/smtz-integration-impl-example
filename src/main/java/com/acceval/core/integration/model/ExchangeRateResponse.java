package com.acceval.core.integration.model;

import java.time.LocalDateTime;

public class ExchangeRateResponse {

    protected LocalDateTime effectiveDate;
    protected String fromCurrency;
    protected String toCurrency;
    protected double rate;
    protected String exchangeRateType;
    protected double ratioFrom;
    protected double ratioTo;

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getExchangeRateType() {
        return exchangeRateType;
    }

    public void setExchangeRateType(String exchangeRateType) {
        this.exchangeRateType = exchangeRateType;
    }

    public double getRatioFrom() {
        return ratioFrom;
    }

    public void setRatioFrom(double ratioFrom) {
        this.ratioFrom = ratioFrom;
    }

    public double getRatioTo() {
        return ratioTo;
    }

    public void setRatioTo(double ratioTo) {
        this.ratioTo = ratioTo;
    }
}
