package com.acceval.core.cache.model;

import java.io.Serializable;

public class GlobalUomConversion implements Serializable {
    private long globalUomConversionID;
    private Uom fromUom;
    private Uom toUom;
    private Double conversionFactor;
    private String converterClass;
    private Long salesDivisionID;

    public long getGlobalUomConversionID() {
        return globalUomConversionID;
    }

    public void setGlobalUomConversionID(long globalUomConversionID) {
        this.globalUomConversionID = globalUomConversionID;
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

    public Double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(Double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String getConverterClass() {
        return converterClass;
    }

    public void setConverterClass(String converterClass) {
        this.converterClass = converterClass;
    }

    public Long getSalesDivisionID() {
        return salesDivisionID;
    }

    public void setSalesDivisionID(Long salesDivisionID) {
        this.salesDivisionID = salesDivisionID;
    }
}
