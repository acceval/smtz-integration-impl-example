package com.acceval.core.integration.model;

import java.util.List;

public class SKUAlternateConversionResponse {
    
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        private String product;

        private List<ItemConversion> conversions;

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public List<ItemConversion> getConversions() {
            return conversions;
        }

        public void setConversions(List<ItemConversion> conversions) {
            this.conversions = conversions;
        }
    }

    public static class ItemConversion {

        private String fromUOM; // to uom
        private String toUOM; // from uom
        private Double conversion;

        public String getFromUOM() {
            return fromUOM;
        }

        public void setFromUOM(String fromUOM) {
            this.fromUOM = fromUOM;
        }

        public String getToUOM() {
            return toUOM;
        }

        public void setToUOM(String toUOM) {
            this.toUOM = toUOM;
        }

        public Double getConversion() {
            return conversion;
        }

        public void setConversion(Double conversion) {
            this.conversion = conversion;
        }
    }
}
