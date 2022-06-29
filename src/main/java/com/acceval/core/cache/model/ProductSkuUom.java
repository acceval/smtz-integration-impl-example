package com.acceval.core.cache.model;

import java.io.Serializable;

public class ProductSkuUom implements Serializable {
	private static final long serialVersionUID = 1L;

	private long productSkuUomID;
	private Uom fromUom;
	private Uom toUom;

	private double conversionFactor;
	
	public ProductSkuUom toCoreProductSkuUom() {
		com.acceval.core.cache.model.ProductSkuUom c = new com.acceval.core.cache.model.ProductSkuUom();
		c.setConversionFactor(getConversionFactor());
		c.setFromUom(getFromUom() == null ? null : getFromUom().toCoreUom());
		c.setToUom(getToUom() == null ? null : getToUom().toCoreUom());
		return c;
	}

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
