package com.acceval.core.so;

import java.io.Serializable;

import com.acceval.core.pricing.so.ModelVariableContext;

public class SalesDocLink implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum AlphaRequestType {
		OFFER, TARGET, FLOOR
	}

	private String enterpriseDocNumber;
	private String marketplaceDocNumber;

//	private double alphaSystem;
//	private AlphaRequestType alphaRequestType;

	private String waterfallModelCode;
	private String waterfallModelDeciderCode;
	private String conditionTableCode;
	private ModelVariableContext context;

	public String getEnterpriseDocNumber() {
		return enterpriseDocNumber;
	}

	public void setEnterpriseDocNumber(String enterpriseDocNumber) {
		this.enterpriseDocNumber = enterpriseDocNumber;
	}

	public String getMarketplaceDocNumber() {
		return marketplaceDocNumber;
	}

	public void setMarketplaceDocNumber(String marketplaceDocNumber) {
		this.marketplaceDocNumber = marketplaceDocNumber;
	}

	public String getWaterfallModelCode() {
		return waterfallModelCode;
	}

	public void setWaterfallModelCode(String waterfallModelCode) {
		this.waterfallModelCode = waterfallModelCode;
	}

	public String getWaterfallModelDeciderCode() {
		return waterfallModelDeciderCode;
	}

	public void setWaterfallModelDeciderCode(String waterfallModelDeciderCode) {
		this.waterfallModelDeciderCode = waterfallModelDeciderCode;
	}

	public String getConditionTableCode() {
		return conditionTableCode;
	}

	public void setConditionTableCode(String conditionTableCode) {
		this.conditionTableCode = conditionTableCode;
	}

	public ModelVariableContext getContext() {
		return context;
	}

	public void setContext(ModelVariableContext context) {
		this.context = context;
	}
}
