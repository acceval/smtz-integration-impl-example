package com.acceval.core.pricing.so;

import java.io.Serializable;

public class ValueCalculatorParam implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum VcParamType {
		NUMBER, PAYMENT_TERM, AMOUNT, UNIT_AMOUNT, UOM
	}

	private Long valueCalculatorParamID;

	private String code;
	private String name;
	private VcParamType vcParamType;
	private boolean editable;
	private String postfix;

	// for splitted child param
	private String parentCode;

	public Long getValueCalculatorParamID() {
		return valueCalculatorParamID;
	}

	public void setValueCalculatorParamID(Long valueCalculatorParamID) {
		this.valueCalculatorParamID = valueCalculatorParamID;
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

	public VcParamType getVcParamType() {
		return vcParamType;
	}

	public void setVcParamType(VcParamType vcParamType) {
		this.vcParamType = vcParamType;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}
