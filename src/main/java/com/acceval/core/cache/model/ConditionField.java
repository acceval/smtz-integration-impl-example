package com.acceval.core.cache.model;

import java.io.Serializable;

public class ConditionField implements Serializable {
	private static final long serialVersionUID = 1L;

	private ConditionFieldConfig config;
	private String value;
	private String name;
	
	public ConditionFieldConfig getConfig() {
		return config;
	}
	public void setConfig(ConditionFieldConfig config) {
		this.config = config;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}
