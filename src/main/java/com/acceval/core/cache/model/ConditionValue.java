package com.acceval.core.cache.model;

import java.io.Serializable;

public class ConditionValue implements Serializable {
	private static final long serialVersionUID = 1L;

	private ConditionValueConfig config;
	private String value;
	private String name;
	private Long currencyId;
	private Currency currency;
	private Long uomId;
	private Uom uom;
	
	public ConditionValueConfig getConfig() {
		return config;
	}
	public void setConfig(ConditionValueConfig config) {
		this.config = config;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public Long getUomId() {
		return uomId;
	}
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public Uom getUom() {
		return uom;
	}
	public void setUom(Uom uom) {
		this.uom = uom;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
