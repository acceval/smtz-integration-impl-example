package com.acceval.core.cache.model;

import java.io.Serializable;

public class ConditionFieldWrapper implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private ConditionRecordConfig conditionRecordConfig;
	private ConditionFieldConfig conditionFieldConfig;
	
	private int displayOrder;
	private String displayName;
	private boolean isMandatory;
	private String conversionParameter;
	private boolean isSkipQuery;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public ConditionRecordConfig getConditionRecordConfig() {
		return conditionRecordConfig;
	}
	public void setConditionRecordConfig(ConditionRecordConfig conditionRecordConfig) {
		this.conditionRecordConfig = conditionRecordConfig;
	}
	public ConditionFieldConfig getConditionFieldConfig() {
		return conditionFieldConfig;
	}
	public void setConditionFieldConfig(ConditionFieldConfig conditionFieldConfig) {
		this.conditionFieldConfig = conditionFieldConfig;
	}	
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	
	public boolean isSkipQuery() {
		return isSkipQuery;
	}
	public void setSkipQuery(boolean isSkipQuery) {
		this.isSkipQuery = isSkipQuery;
	}
	public String getConversionParameter() {
		return conversionParameter;
	}
	public void setConversionParameter(String conversionParameter) {
		this.conversionParameter = conversionParameter;
	}
	@Override
	 public int hashCode(){
	  if (id != null) return id.hashCode();
	  return -1;
	}
	
	@Override
    public boolean equals(Object object) {
		
        boolean isEquals = false;

        if (object != null && object instanceof ConditionFieldWrapper) {
        	
        		ConditionFieldWrapper wrapper = (ConditionFieldWrapper) object;
        		
        		if (this.conditionFieldConfig.getCode() != null && wrapper.conditionFieldConfig.getCode() != null) {
        			
        			isEquals = this.conditionFieldConfig.getCode().equals(wrapper.conditionFieldConfig.getCode());
        		}        		
        }

        return isEquals;
    }
}
