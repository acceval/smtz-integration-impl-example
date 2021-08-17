package com.acceval.core.cache.model;

import java.io.Serializable;

public class Range implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long rangeId;
	private String label;
	private Double minValue;
	private Double maxValue;
	
	private RangeGroup rangeGroup;

	public RangeGroup getRangeGroup() {
		return rangeGroup;
	}
	public void setRangeGroup(RangeGroup rangeGroup) {
		this.rangeGroup = rangeGroup;
	}	
	public Long getRangeId() {
		return rangeId;
	}
	public void setRangeId(Long rangeId) {
		this.rangeId = rangeId;
	}
	public Double getMinValue() {
		return minValue;
	}
	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}
	public Double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
		this.setLabel(this.getLabel());
	}
	public String getLabel() {
		
		if (label == null) {								
			return String.format("%.0f", minValue == null? 0 : minValue) + "-" 
					+ String.format("%.0f", maxValue == null? 0 : maxValue);					
		} else {
			return label;
		}
	}
	public void setLabel(String label) {
		this.label = label;
	}	
}
