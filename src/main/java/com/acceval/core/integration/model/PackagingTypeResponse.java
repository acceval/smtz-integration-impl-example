package com.acceval.core.integration.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class PackagingTypeResponse implements Serializable {

    private String objectType;
    private String product;
    private String classType;
    private String characteristics;

    private List<Item> items;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item implements Serializable {
        private String characteristic;
        private String characteristicValue;
        private LocalDate date;

        public String getCharacteristic() {
            return characteristic;
        }

        public void setCharacteristic(String characteristic) {
            this.characteristic = characteristic;
        }

        public String getCharacteristicValue() {
            return characteristicValue;
        }

        public void setCharacteristicValue(String characteristicValue) {
            this.characteristicValue = characteristicValue;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }
}
