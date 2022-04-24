package com.acceval.core.pricing.so;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Component implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum BAR_TYPE {
		FLOAT, TOTAL
	}

	private Long componentID;

	private String code;
	private String name;
	@Enumerated(EnumType.STRING)
	private BAR_TYPE barType;
	private boolean isForceDisplay;

	public Long getComponentID() {
		return componentID;
	}

	public void setComponentID(Long componentID) {
		this.componentID = componentID;
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

	public BAR_TYPE getBarType() {
		return barType;
	}

	public void setBarType(BAR_TYPE barType) {
		this.barType = barType;
	}

	public boolean isForceDisplay() {
		return isForceDisplay;
	}

	public void setForceDisplay(boolean isForceDisplay) {
		this.isForceDisplay = isForceDisplay;
	}

	@Override
	public int hashCode() {
		if (componentID != null) return componentID.hashCode();
		return -1;
	}

	@Override
	public boolean equals(Object object) {
		boolean isEquals = false;

		if (object != null && object instanceof Component) {

			isEquals = this.code == ((Component) object).code;
		}

		return isEquals;
	}

}
