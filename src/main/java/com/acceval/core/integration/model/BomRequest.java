package com.acceval.core.integration.model;

import java.util.List;

public class BomRequest {
    private String product;
    private String plant;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public static class BomListRequest {
        private List<String> products;
        private List<String> plants;

        public List<String> getProducts() {
            return products;
        }

        public void setProducts(List<String> products) {
            this.products = products;
        }

        public List<String> getPlants() {
            return plants;
        }

        public void setPlants(List<String> plants) {
            this.plants = plants;
        }
    }
}
