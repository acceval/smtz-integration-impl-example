package com.acceval.core.integration.model;

import java.time.LocalDate;
import java.util.List;

public class SAPMarketPriceRequest {
    private List<String> sapCodes;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    public List<String> getSapCodes() {
        return sapCodes;
    }

    public void setSapCodes(List<String> sapCodes) {
        this.sapCodes = sapCodes;
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
