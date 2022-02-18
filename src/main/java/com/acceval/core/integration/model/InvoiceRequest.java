package com.acceval.core.integration.model;

import java.time.LocalDate;
import java.util.List;

public class InvoiceRequest {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private List<String> salesOrganisations;
    private List<String> divisions;

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

    public List<String> getSalesOrganisations() {
        return salesOrganisations;
    }

    public void setSalesOrganisations(List<String> salesOrganisations) {
        this.salesOrganisations = salesOrganisations;
    }

    public List<String> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<String> divisions) {
        this.divisions = divisions;
    }
}
