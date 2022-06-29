package com.acceval.core.cache.model;

import java.io.Serializable;

public class Uom implements Serializable {
	private static final long serialVersionUID = 1L;

	private long uomID;
	private String code;
	private String name;
	private String shortName;
	private UomType uomType;
	
	public com.acceval.core.cache.model.Uom toCoreUom() {
		com.acceval.core.cache.model.Uom c = new com.acceval.core.cache.model.Uom();
		c.setCode(getCode());
		c.setName(getName());
		c.setShortName(getShortName());
		c.setUomID(getUomID());
		return c;
	}

	public Uom(String uomID) {
		super();
		this.uomID = Long.valueOf(uomID);
	}

	public Uom() {
		super();
	}

	public long getUomID() {
		return uomID;
	}

	public void setUomID(long uomID) {
		this.uomID = uomID;
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

	public UomType getUomType() {
		return uomType;
	}

	public void setUomType(UomType uomType) {
		this.uomType = uomType;
	}
}
