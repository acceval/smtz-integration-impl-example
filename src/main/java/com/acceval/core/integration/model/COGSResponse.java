package com.acceval.core.integration.model;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;
import java.util.List;

public class COGSResponse {
    private List<ItemCOGS> cogsItems;
    private List<ItemCOGSComponent> cogsComponentItems;

    public List<ItemCOGS> getCogsItems() {
        return cogsItems;
    }

    public void setCogsItems(List<ItemCOGS> cogsItems) {
        this.cogsItems = cogsItems;
    }

    public List<ItemCOGSComponent> getCogsComponentItems() {
        return cogsComponentItems;
    }

    public void setCogsComponentItems(List<ItemCOGSComponent> cogsComponentItems) {
        this.cogsComponentItems = cogsComponentItems;
    }

    public static class ItemCOGS {
        private String plant;
        private String product;
        private Double price;
        private String currency;
        private Double quantity;
        private String uom;
        private LocalDate validFrom;
        private LocalDate validTo;

        public String getPlant() {
            return plant;
        }

        public void setPlant(String plant) {
            this.plant = plant;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }

        public String getUom() {
            return uom;
        }

        public void setUom(String uom) {
            this.uom = uom;
        }

        public LocalDate getValidFrom() {
            return validFrom;
        }

        public void setValidFrom(LocalDate validFrom) {
            this.validFrom = validFrom;
        }

        public LocalDate getValidTo() {
            return validTo;
        }

        public void setValidTo(LocalDate validTo) {
            this.validTo = validTo;
        }
    }
    
    public static class ItemCOGSComponent {

        private String plant;
        private String product;
        private String component;
        private Double value;

        public String getPlant() {
            return plant;
        }

        public void setPlant(String plant) {
            this.plant = plant;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getComponent() {
            return component;
        }

        public void setComponent(String component) {
            this.component = component;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }
    }
    
}
