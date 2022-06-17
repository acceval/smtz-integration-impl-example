package com.acceval.core.pricing.so;

import java.io.Serializable;

import org.springframework.ui.Model;

import com.acceval.core.model.BaseModel;

public class PricingTechnique extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private long pricingTechniqueID;

	private String code;
	private String name;

	private Model priceModel;
	private Model marginModel;
	private Component percentageReferenceComponent;

	public long getPricingTechniqueID() {
		return pricingTechniqueID;
	}

	public void setPricingTechniqueID(long pricingTechniqueID) {
		this.pricingTechniqueID = pricingTechniqueID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Model getPriceModel() {
		return priceModel;
	}

	public void setPriceModel(Model priceModel) {
		this.priceModel = priceModel;
	}

	public Model getMarginModel() {
		return marginModel;
	}

	public void setMarginModel(Model marginModel) {
		this.marginModel = marginModel;
	}

	public Component getPercentageReferenceComponent() {
		return percentageReferenceComponent;
	}

	public void setPercentageReferenceComponent(Component percentageReferenceComponent) {
		this.percentageReferenceComponent = percentageReferenceComponent;
	}
}
