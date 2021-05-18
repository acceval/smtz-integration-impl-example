package com.acceval.core.integration.model;

import java.time.LocalDate;
import java.util.List;

public class MarketPriceResponse {

    private List<MarketPriceResponseItem> items;

    public List<MarketPriceResponseItem> getItems() {
        return items;
    }

    public void setItems(List<MarketPriceResponseItem> items) {
        this.items = items;
    }

    public static class MarketPriceResponseItem {
        private String symbol;
        private LocalDate date;
        private double high;
        private double low;
        private double close;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
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

        public double getClose() {
            return close;
        }

        public void setClose(double close) {
            this.close = close;
        }
    }
}
