package com.acceval.core.pricing.so;

import java.util.List;

import com.acceval.core.model.VariableContext;

public class PricingVariableContext extends VariableContext {
	private static final long serialVersionUID = 1L;

	private PricingTechniqueResult pricingTechniqueResult;

	public List<ModelCompResult> customFieldGetModelCompResults() {
		if (getPricingTechniqueResult() != null && getPricingTechniqueResult().getTechniqueModelResult() != null) {
			return getPricingTechniqueResult().getTechniqueModelResult().getModelCompResults();
		}
		return null;
	}

	public double customFieldGetComponentValue(String compCode) {
		List<ModelCompResult> results = customFieldGetModelCompResults();
		if (results != null) {
			for (ModelCompResult result : results) {
				if (compCode.equals(result.getComponent().getCode())) {
					return result.getModelComponentValue();
				}
			}
		}
		return 0;
	}

	public PricingTechniqueResult getPricingTechniqueResult() {
		return pricingTechniqueResult;
	}

	public void setPricingTechniqueResult(PricingTechniqueResult pricingTechniqueResult) {
		this.pricingTechniqueResult = pricingTechniqueResult;
	}
}
