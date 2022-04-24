package com.acceval.core.pricing.so;

import java.io.Serializable;
import java.util.List;

/**
 * refer to com.acceval.pricing.model.ModelCompResultInputter
 */
public class ModelCompResultInputter implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * refer to com.acceval.pricing.model.ComponentInputter.DATATYPE
	 */
	public enum DATATYPE {
		TEXT, DATE, DATASOURCE, CONTEXT_DISPLAY, FORMULA, VALUE_CALCULATOR, 
		REBATE_CONFIG, COMMITMENT
	}

	private String value;

	// ComponentInputter info
	private String code;
	private String label;
	private boolean isToDisplay;

	private DATATYPE dataType;
	private String dataSourcePath; // for DATATYPE: DATASOURCE

	/** DATATYPE: VALUE_CALCULATOR, value calculator info */
	private Long valueCalculatorConfigID;
	private String formula;
	private String formulaDesc;
	private List<ValueCalculatorParamResult> valueCalculatorParamResults;

	private RebateConfig rebateConfig;
	private SalesItemCommitment commitment;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public DATATYPE getDataType() {
		return dataType;
	}

	public void setDataType(DATATYPE dataType) {
		this.dataType = dataType;
	}

	public String getDataSourcePath() {
		return dataSourcePath;
	}

	public void setDataSourcePath(String dataSourcePath) {
		this.dataSourcePath = dataSourcePath;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isToDisplay() {
		return isToDisplay;
	}

	public void setToDisplay(boolean isToDisplay) {
		this.isToDisplay = isToDisplay;
	}

	public List<ValueCalculatorParamResult> getValueCalculatorParamResults() {
		return valueCalculatorParamResults;
	}

	public void setValueCalculatorParamResults(List<ValueCalculatorParamResult> valueCalculatorParamResults) {
		this.valueCalculatorParamResults = valueCalculatorParamResults;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Long getValueCalculatorConfigID() {
		return valueCalculatorConfigID;
	}

	public void setValueCalculatorConfigID(Long valueCalculatorConfigID) {
		this.valueCalculatorConfigID = valueCalculatorConfigID;
	}

	public String getFormulaDesc() {
		return formulaDesc;
	}

	public void setFormulaDesc(String formulaDesc) {
		this.formulaDesc = formulaDesc;
	}

	public RebateConfig getRebateConfig() {
		return rebateConfig;
	}

	public void setRebateConfig(RebateConfig rebateConfig) {
		this.rebateConfig = rebateConfig;
	}

	public SalesItemCommitment getCommitment() {
		return commitment;
	}

	public void setCommitment(SalesItemCommitment commitment) {
		this.commitment = commitment;
	}
}
