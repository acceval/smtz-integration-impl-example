package com.acceval.core.notification;

import java.io.Serializable;

public class NotificationParam implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;	
	private String paramCode;
	private String paramValue;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

}
