package com.acceval.core.cache.model;

import java.io.Serializable;
import java.util.Set;

public class RangeGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long rangeGroupId;
	
	private String name;
	private String code;
	private String description;
	private int precision;
	
	private Uom uom;
	private Set<Range> ranges;

	public Long getRangeGroupId() {
		return rangeGroupId;
	}

	public void setRangeGroupId(Long rangeGroupId) {
		this.rangeGroupId = rangeGroupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public Uom getUom() {
		return uom;
	}

	public void setUom(Uom uom) {
		this.uom = uom;
	}

	public Set<Range> getRanges() {
		return ranges;
	}

	public void setRanges(Set<Range> ranges) {
		this.ranges = ranges;
	}
	
}
