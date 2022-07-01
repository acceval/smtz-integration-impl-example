package com.acceval.core.pricing.so;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * refer to com.acceval.pricing.model.ModelCompResult
 */
public class ModelCompResult implements Serializable {
	private static final long serialVersionUID = 1L;

	/** refer to com.acceval.pricing.model.ModelComponent.POS_NEG */
	public enum POS_NEG {
		POSITIVE, NEGATIVE
	}

	/** refer to com.acceval.pricing.model.ModelComponent.ModelComponentType */
	public enum ModelComponentType {
		COST("Cost", POS_NEG.NEGATIVE), COST_FACTOR("Cost Factor", POS_NEG.POSITIVE), DISCOUNT("Discount", POS_NEG.NEGATIVE),
		LIST_PRICE("List Price Setting", POS_NEG.POSITIVE), MARGIN("Margin", POS_NEG.POSITIVE), MARKUP("Markup", POS_NEG.POSITIVE),
		PRICE("Price", POS_NEG.POSITIVE), TARGET_MARGIN("Target Margin", POS_NEG.POSITIVE);

		ModelComponentType(String name, POS_NEG posNeg) {
			this.name = name;
			this.posNeg = posNeg;
		};

		private String name;
		private POS_NEG posNeg;

		public String getName() {
			return name;
		}

		public POS_NEG getPosNeg() {
			return posNeg;
		}

		public double getValue(double val) {
			if (posNeg.equals(POS_NEG.NEGATIVE)) {
				return val * -1;
			}
			return val;
		}
	}

	private Component component;
	private String parentComponentCode;
	@Enumerated(EnumType.STRING)
	private ModelComponentType modelComponentType;

	private List<ComponentActionResult> componentActionResults;
	private List<ModelCompResultInputter> modelCompResultInputters;
	private double modelComponentValue;
	private double modelComponentAmountValue;
	private double modelComponentPercentage;
	private double pricingTechniquePercentage;

	private double comparePriValue;

	public double convertToComponentValue() {
		return getModelComponentType().getValue(this.modelComponentValue);
	}

	public double getModelComponentValue() {
		return modelComponentValue;
	}

	public void setModelComponentValue(double modelComponentValue) {
		this.modelComponentValue = modelComponentValue;
	}

	public double getModelComponentAmountValue() {
		return modelComponentAmountValue;
	}

	public void setModelComponentAmountValue(double modelComponentAmountValue) {
		this.modelComponentAmountValue = modelComponentAmountValue;
	}

	public double getModelComponentPercentage() {
		return modelComponentPercentage;
	}

	public void setModelComponentPercentage(double modelComponentPercentage) {
		this.modelComponentPercentage = modelComponentPercentage;
	}

	public double getPricingTechniquePercentage() {
		return pricingTechniquePercentage;
	}

	public void setPricingTechniquePercentage(double pricingTechniquePercentage) {
		this.pricingTechniquePercentage = pricingTechniquePercentage;
	}

	public double getComparePriValue() {
		return comparePriValue;
	}

	public void setComparePriValue(double comparePriValue) {
		this.comparePriValue = comparePriValue;
	}

	public List<ModelCompResultInputter> getModelCompResultInputters() {
		return modelCompResultInputters;
	}

	public void setModelCompResultInputters(List<ModelCompResultInputter> modelCompResultInputters) {
		this.modelCompResultInputters = modelCompResultInputters;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public String getParentComponentCode() {
		return parentComponentCode;
	}

	public void setParentComponentCode(String parentComponentCode) {
		this.parentComponentCode = parentComponentCode;
	}

	public ModelComponentType getModelComponentType() {
		return modelComponentType;
	}

	public void setModelComponentType(ModelComponentType modelComponentType) {
		this.modelComponentType = modelComponentType;
	}

	public List<ComponentActionResult> getComponentActionResults() {
		return componentActionResults;
	}

	public void setComponentActionResults(List<ComponentActionResult> componentActionResults) {
		this.componentActionResults = componentActionResults;
	}

}