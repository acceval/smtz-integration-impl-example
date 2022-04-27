package com.acceval.core.pricing.so;

import java.io.Serializable;

import com.acceval.core.repository.MicroServiceObject;

@MicroServiceObject(primaryKey = "pricingTechniqueResultID", originEntityClass = "com.acceval.pricing.model.PricingTechniqueResult",
		useCommonQuery = true)
public class PricingTechniqueResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private long pricingTechniqueID;
	private long priceModelID;
	private int priceModelVersion;
	private long marginModelID;
	private int marginModelVersion;

	private ModelResult techniqueModelResult;
	private boolean isModelVersionChanged;

	public ModelResult getTechniqueModelResult() {
		return techniqueModelResult;
	}

	public void setTechniqueModelResult(ModelResult techniqueModelResult) {
		this.techniqueModelResult = techniqueModelResult;
	}

	public long getPricingTechniqueID() {
		return pricingTechniqueID;
	}

	public void setPricingTechniqueID(long pricingTechniqueID) {
		this.pricingTechniqueID = pricingTechniqueID;
	}

	public long getPriceModelID() {
		return priceModelID;
	}

	public void setPriceModelID(long priceModelID) {
		this.priceModelID = priceModelID;
	}

	public int getPriceModelVersion() {
		return priceModelVersion;
	}

	public void setPriceModelVersion(int priceModelVersion) {
		this.priceModelVersion = priceModelVersion;
	}

	public long getMarginModelID() {
		return marginModelID;
	}

	public void setMarginModelID(long marginModelID) {
		this.marginModelID = marginModelID;
	}

	public int getMarginModelVersion() {
		return marginModelVersion;
	}

	public void setMarginModelVersion(int marginModelVersion) {
		this.marginModelVersion = marginModelVersion;
	}

	public boolean isModelVersionChanged() {
		return isModelVersionChanged;
	}

	public void setModelVersionChanged(boolean isModelVersionChanged) {
		this.isModelVersionChanged = isModelVersionChanged;
	}

}
