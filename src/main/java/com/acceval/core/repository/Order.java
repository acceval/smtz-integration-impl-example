package com.acceval.core.repository;

import java.io.Serializable;

public class Order implements Serializable {

	private static final long serialVersionUID = 1170659228884573690L;

	private final static boolean IS_ASCENDING = true;
	private boolean order = IS_ASCENDING;
	private String property;

	public Order() {
		super();
	}

	public Order(String prop, boolean ord) {
		property = prop;
		order = ord;
	}

	public boolean getIsAscending() {
		return order;
	}

	public boolean isAscending() {
		return getIsAscending();
	}

	public void setIsAscending(boolean b) {
		order = b;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String p) {
		property = p;
	}

	public String getLastProperty() {
		int lastIndex = getProperty().lastIndexOf(".");
		if (lastIndex == -1) return getProperty();
		return getProperty().substring(lastIndex + 1);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder().append("<Order").append(" Property=" + this.getProperty())
				.append(" IsAscending=" + this.getIsAscending()).append(" />");
		return sb.toString();
	}

}
