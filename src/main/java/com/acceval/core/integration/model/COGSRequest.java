package com.acceval.core.integration.model;

import java.time.LocalDate;
import java.util.List;

public class COGSRequest {

    private List<String> paramProducts;
    private LocalDate paramDate;
    private Integer batchSize;
	private String delaySec;

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<String> getParamProducts() {
        return paramProducts;
    }

    public void setParamProducts(List<String> paramProducts) {
        this.paramProducts = paramProducts;
    }

    public LocalDate getParamDate() {
        return paramDate;
    }

    public void setParamDate(LocalDate paramDate) {
        this.paramDate = paramDate;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

	public String getDelaySec() {
		return delaySec;
	}

	public void setDelaySec(String delaySec) {
		this.delaySec = delaySec;
	}

	public static class Item {
        private String product;
        private String plant;
        private LocalDate date;

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

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }

}
