package com.acceval.core.pricing.so;

import java.io.Serializable;
import java.lang.reflect.Method;

public class BaseVCMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	private String key;
	private String propertyName;
	private String defaultValue;
	private boolean contextToObj = false;

	private Method customProperty;

	public BaseVCMapping() {
		super();
	}

	public BaseVCMapping(String key, String propertyName, String defaultValue, boolean contextToObj) {
		super();
		this.key = key;
		this.propertyName = propertyName;
		this.defaultValue = defaultValue;
		this.contextToObj = contextToObj;
	}

	public BaseVCMapping(String key, String propertyName) {
		super();
		this.key = key;
		this.propertyName = propertyName;
	}

	public BaseVCMapping(String key, Method customProperty) {
		super();
		this.key = key;
		this.customProperty = customProperty;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isContextToObj() {
		return contextToObj;
	}

	public void setContextToObj(boolean contextToObj) {
		this.contextToObj = contextToObj;
	}

	public Method getCustomProperty() {
		return customProperty;
	}

	public void setCustomProperty(Method customProperty) {
		this.customProperty = customProperty;
	}

}
