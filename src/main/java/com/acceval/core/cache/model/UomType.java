package com.acceval.core.cache.model;

import java.io.Serializable;

public class UomType implements Serializable {
	private static final long serialVersionUID = -5172463329263248069L;

	private long uomTypeID;
	private String code;
	private String name;
	private String unit;

	public UomType(String uomTypeID) {
		super();
		this.uomTypeID = Long.valueOf(uomTypeID);
	}

	public UomType() {
		super();
	}

	public long getUomTypeID() {
		return uomTypeID;
	}

	public void setUomTypeID(long uomTypeID) {
		this.uomTypeID = uomTypeID;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
