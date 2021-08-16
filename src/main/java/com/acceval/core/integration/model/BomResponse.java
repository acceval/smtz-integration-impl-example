package com.acceval.core.integration.model;

import java.time.LocalDate;
import java.util.List;

public class BomResponse {
    
    private List<Header> headers;
    private List<Item> items;

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Header {

        private String materialNumber;
        private Double confirmedQuantity;
        private String unitOfMeasure;
        private String plant;
        private String assetClass;
        private String alternativeBOM;
        private LocalDate validFromDate;

        public String getMaterialNumber() {
            return materialNumber;
        }

        public void setMaterialNumber(String materialNumber) {
            this.materialNumber = materialNumber;
        }

        public Double getConfirmedQuantity() {
            return confirmedQuantity;
        }

        public void setConfirmedQuantity(Double confirmedQuantity) {
            this.confirmedQuantity = confirmedQuantity;
        }

        public String getUnitOfMeasure() {
            return unitOfMeasure;
        }

        public void setUnitOfMeasure(String unitOfMeasure) {
            this.unitOfMeasure = unitOfMeasure;
        }

        public String getPlant() {
            return plant;
        }

        public void setPlant(String plant) {
            this.plant = plant;
        }

        public String getAssetClass() {
            return assetClass;
        }

        public void setAssetClass(String assetClass) {
            this.assetClass = assetClass;
        }

        public String getAlternativeBOM() {
            return alternativeBOM;
        }

        public void setAlternativeBOM(String alternativeBOM) {
            this.alternativeBOM = alternativeBOM;
        }

        public LocalDate getValidFromDate() {
            return validFromDate;
        }

        public void setValidFromDate(LocalDate validFromDate) {
            this.validFromDate = validFromDate;
        }
    }
    
    public static class Item {

        private String bomComponent;
        private String materialDescription;
        private Double componentQuantity;
        private String baseUnitOfMeasure;
        private String deletionIndicator;
        private String alternativeBOM;

        public String getBomComponent() {
            return bomComponent;
        }

        public void setBomComponent(String bomComponent) {
            this.bomComponent = bomComponent;
        }

        public String getMaterialDescription() {
            return materialDescription;
        }

        public void setMaterialDescription(String materialDescription) {
            this.materialDescription = materialDescription;
        }

        public Double getComponentQuantity() {
            return componentQuantity;
        }

        public void setComponentQuantity(Double componentQuantity) {
            this.componentQuantity = componentQuantity;
        }

        public String getBaseUnitOfMeasure() {
            return baseUnitOfMeasure;
        }

        public void setBaseUnitOfMeasure(String baseUnitOfMeasure) {
            this.baseUnitOfMeasure = baseUnitOfMeasure;
        }

        public String getDeletionIndicator() {
            return deletionIndicator;
        }

        public void setDeletionIndicator(String deletionIndicator) {
            this.deletionIndicator = deletionIndicator;
        }

        public String getAlternativeBOM() {
            return alternativeBOM;
        }

        public void setAlternativeBOM(String alternativeBOM) {
            this.alternativeBOM = alternativeBOM;
        }
    }
}
