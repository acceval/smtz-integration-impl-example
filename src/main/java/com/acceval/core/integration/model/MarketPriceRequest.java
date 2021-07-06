package com.acceval.core.integration.model;

import java.time.LocalDate;
import java.util.List;

public class MarketPriceRequest {
    private List<String> symbols;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
