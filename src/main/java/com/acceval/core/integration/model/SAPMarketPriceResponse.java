package com.acceval.core.integration.model;

import java.time.LocalDate;
import java.util.List;

public class SAPMarketPriceResponse {

    private List<SAPMarketPriceResponseItem> items;

    public List<SAPMarketPriceResponseItem> getItems() {
        return items;
    }

    public void setItems(List<SAPMarketPriceResponseItem> items) {
        this.items = items;
    }

    public static class SAPMarketPriceResponseItem {
        private String sapCode;
        private String source;

        private LocalDate date;
        private double high;
        private double low;

        public String getSapCode() {
            return sapCode;
        }

        public void setSapCode(String sapCode) {
            this.sapCode = sapCode;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public double getHigh() {
            return high;
        }

        public void setHigh(double high) {
            this.high = high;
        }

        public double getLow() {
            return low;
        }

        public void setLow(double low) {
            this.low = low;
        }
    }
}
