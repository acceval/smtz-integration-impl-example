package com.acceval.core.pricing.so;

import java.io.Serializable;

public class ValueCalculatorParamResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private ValueCalculatorParam valueCalculatorParam;
	private String value;

	public ValueCalculatorParam getValueCalculatorParam() {
		return valueCalculatorParam;
	}

	public void setValueCalculatorParam(ValueCalculatorParam valueCalculatorParam) {
		this.valueCalculatorParam = valueCalculatorParam;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
