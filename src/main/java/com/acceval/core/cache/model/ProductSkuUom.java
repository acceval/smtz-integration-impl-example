package com.acceval.core.cache.model;

import java.io.Serializable;

public class ProductSkuUom implements Serializable {
	private static final long serialVersionUID = 1L;

	private Uom fromUom;
	private Uom toUom;

	private double conversionFactor;

	public Uom getFromUom() {
		return fromUom;
	}

	public void setFromUom(Uom fromUom) {
		this.fromUom = fromUom;
	}

	public Uom getToUom() {
		return toUom;
	}

	public void setToUom(Uom toUom) {
		this.toUom = toUom;
	}

	public double getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
}
