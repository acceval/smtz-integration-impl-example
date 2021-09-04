package com.acceval.core.cache.model;

import java.io.Serializable;

public class SkuProductAltUom implements Serializable {

    private long skuProductAltUomID;
    private long alternateFromUomID;
    private long alternateToUomID;
    private Double conversionFactor;
    private long skuID;

    public SkuProductAltUom() {
    }

    public SkuProductAltUom(long skuProductAltUomID, long alternateFromUomID, long alternateToUomID, Double conversionFactor, long skuID) {
        this.skuProductAltUomID = skuProductAltUomID;
        this.alternateFromUomID = alternateFromUomID;
        this.alternateToUomID = alternateToUomID;
        this.conversionFactor = conversionFactor;
        this.skuID = skuID;
    }

    public long getSkuProductAltUomID() {
        return skuProductAltUomID;
    }

    public void setSkuProductAltUomID(long skuProductAltUomID) {
        this.skuProductAltUomID = skuProductAltUomID;
    }

    public long getAlternateFromUomID() {
        return alternateFromUomID;
    }

    public void setAlternateFromUomID(long alternateFromUomID) {
        this.alternateFromUomID = alternateFromUomID;
    }

    public long getAlternateToUomID() {
        return alternateToUomID;
    }

    public void setAlternateToUomID(long alternateToUomID) {
        this.alternateToUomID = alternateToUomID;
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
