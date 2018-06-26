package com.acceval.core.microservice.model;

/**
 * standard LabelValue to map back to Angular
 */
public class LabelValue implements Comparable<LabelValue> {
	private String label;
	private String value;

	public LabelValue() {
		
	}
	
	public LabelValue(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(LabelValue other) {

		return this.label.compareTo(other.label);
	}

}
