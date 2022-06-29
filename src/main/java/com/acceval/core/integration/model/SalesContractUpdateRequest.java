package com.acceval.core.integration.model;

public class SalesContractUpdateRequest {
    private String salesContractNumber;
    private String pmtItemNo;
    private String productCode;
    private double quantity;
    private double invoicePrice;
    private String priceCurrency;
    private double exchangeRate;
    
    private double clearanceCost;
    private double locationShipmentCost;
    private double inboundInsurance;
    private double inboundSurveyor;
    private boolean backToBackChangeFlag = false;

    public String getSalesContractNumber() {
        return salesContractNumber;
    }

    public void setSalesContractNumber(String salesContractNumber) {
        this.salesContractNumber = salesContractNumber;
    }

    public String getPmtItemNo() {
        return pmtItemNo;
    }

    public void setPmtItemNo(String pmtItemNo) {
        this.pmtItemNo = pmtItemNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(double invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getClearanceCost() {
		return clearanceCost;
	}

	public void setClearanceCost(double clearanceCost) {
		this.clearanceCost = clearanceCost;
	}

	public double getLocationShipmentCost() {
		return locationShipmentCost;
	}

	public void setLocationShipmentCost(double locationShipmentCost) {
		this.locationShipmentCost = locationShipmentCost;
	}

	public double getInboundInsurance() {
		return inboundInsurance;
	}

	public void setInboundInsurance(double inboundInsurance) {
		this.inboundInsurance = inboundInsurance;
	}

	public double getInboundSurveyor() {
		return inboundSurveyor;
	}

	public void setInboundSurveyor(double inboundSurveyor) {
		this.inboundSurveyor = inboundSurveyor;
	}

	public boolean isBackToBackChangeFlag() {
		return backToBackChangeFlag;
	}

	public void setBackToBackChangeFlag(boolean backToBackChangeFlag) {
		this.backToBackChangeFlag = backToBackChangeFlag;
	}    
	
}
