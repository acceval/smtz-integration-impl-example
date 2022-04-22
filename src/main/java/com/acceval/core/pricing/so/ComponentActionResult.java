package com.acceval.core.pricing.so;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * refer to com.acceval.pricing.model.ComponentActionResult
 */
public class ComponentActionResult implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String KEY_RESULT_SUCCESS = "RESULT_SUCCESS";

	private ComponentActionInput componentActionInput;

	private double value;
	private String resultMsg;
	private Map<String, String> mapActionValue = new HashMap<>();

	public ComponentActionInput getComponentActionInput() {
		return componentActionInput;
	}

	public void setComponentActionInput(ComponentActionInput componentActionInput) {
		this.componentActionInput = componentActionInput;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Map<String, String> getMapActionValue() {
		return mapActionValue;
	}

	public void setMapActionValue(Map<String, String> mapActionValue) {
		this.mapActionValue = mapActionValue;
	}
}
