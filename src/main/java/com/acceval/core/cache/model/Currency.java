package com.acceval.core.cache.model;

import java.io.Serializable;

public class Currency implements Serializable {
	private static final long serialVersionUID = 6282982036991988520L;

	private long currencyID;
	private String code;
	private String name;
	private String shortName;
	private String suffix;
	private String prefix;

	public Currency(String currencyID) {
		super();
		this.currencyID = Long.valueOf(currencyID);
	}

	public Currency() {
		super();
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
