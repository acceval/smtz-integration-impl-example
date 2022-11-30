package com.acceval.core.so;

import java.io.Serializable;
import java.util.List;

import com.acceval.core.pricing.so.ModelVariableContext;

public class SalesDocLink implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum EnterpriseRequestType {
		OFFER, TARGET, FLOOR
	}

	private String enterpriseDocNumber;
	private String marketplaceDocNumber;
	private boolean saveEnterpriseSalesDoc = true;

	private String waterfallModelCode;
	private String waterfallModelDeciderCode;
	private String conditionTableCode;
	private ModelVariableContext context;
	private List<ModelVariableContext> lstContext;

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

	public boolean isSaveEnterpriseSalesDoc() {
		return saveEnterpriseSalesDoc;
	}

	public void setSaveEnterpriseSalesDoc(boolean saveEnterpriseSalesDoc) {
		this.saveEnterpriseSalesDoc = saveEnterpriseSalesDoc;
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

	public List<ModelVariableContext> getLstContext() {
		return lstContext;
	}

	public void setLstContext(List<ModelVariableContext> lstContext) {
		this.lstContext = lstContext;
	}
}
