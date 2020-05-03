package com.acceval.core.repository;

import java.io.Serializable;

public class Projection implements Serializable {
	private static final long serialVersionUID = 1L;

	private String property;

	public Projection() {
		super();
	}

	public Projection(String property) {
		super();
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
