package com.acceval.core.cache.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExchangeRate implements Serializable {
    private long exchangeRateID;
    private LocalDateTime effectiveDate;

    private Currency fromCurrency;
    private Currency toCurrency;
    private Double rate;
    private Double fromCurrencyRatio;
    private Double toCurrencyRatio;

    public long getExchangeRateID() {
        return exchangeRateID;
    }

    public void setExchangeRateID(long exchangeRateID) {
        this.exchangeRateID = exchangeRateID;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getFromCurrencyRatio() {
        return fromCurrencyRatio;
    }

    public void setFromCurrencyRatio(Double fromCurrencyRatio) {
        this.fromCurrencyRatio = fromCurrencyRatio;
    }

    public Double getToCurrencyRatio() {
        return toCurrencyRatio;
    }

    public void setToCurrencyRatio(Double toCurrencyRatio) {
        this.toCurrencyRatio = toCurrencyRatio;
    }

    public ExchangeRateType getType() {
        return type;
    }

    public void setType(ExchangeRateType type) {
        this.type = type;
    }

    private ExchangeRateType type;
}
