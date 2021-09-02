package com.acceval.core.cache.model;

import java.io.Serializable;

public class SkuProductAltUom implements Serializable {

    private long skuProductAltUomID;
    private Uom alternateFromUom;
    private Uom alternateToUom;
    private Double conversionFactor;
    private long skuID;

    public long getSkuProductAltUomID() {
        return skuProductAltUomID;
    }

    public void setSkuProductAltUomID(long skuProductAltUomID) {
        this.skuProductAltUomID = skuProductAltUomID;
    }

    public Uom getAlternateFromUom() {
        return alternateFromUom;
    }

    public void setAlternateFromUom(Uom alternateFromUom) {
        this.alternateFromUom = alternateFromUom;
    }

    public Uom getAlternateToUom() {
        return alternateToUom;
    }

    public void setAlternateToUom(Uom alternateToUom) {
        this.alternateToUom = alternateToUom;
    }

    public Double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(Double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public long getSkuID() {
        return skuID;
    }

    public void setSkuID(long skuID) {
        this.skuID = skuID;
    }
}
