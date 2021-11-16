package com.acceval.core.cache.model;


import java.io.Serializable;


public class ConditionTableInputParameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3645568115487372174L;

	public enum InputParamType {
		DIRECT, CONDITION_TABLE, CONDITION_FIELD
	}

	private Long id;

	private InputParamType type;
	private String parameterName;
	private String parameterCode;

	private String paramConditionFieldId;
	private String paramConditionRecordConfigId;

	private ConditionRecordConfig conditionRecordConfig;

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public ConditionRecordConfig getConditionRecordConfig() {
		return conditionRecordConfig;
	}

	public void setConditionRecordConfig(ConditionRecordConfig conditionRecordConfig) {
		this.conditionRecordConfig = conditionRecordConfig;
	}

	@Override
	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		return -1;
	}

	@Override
	public boolean equals(Object object) {

		boolean isEquals = false;

		if (object != null && object instanceof ConditionTableInputParameter) {

			ConditionTableInputParameter inputParam = (ConditionTableInputParameter) object;

			if (this.getParameterCode() != null && inputParam.getParameterCode() != null) {
				isEquals = this.getParameterCode().equals(inputParam.getParameterCode());
			} else if (this.getParamConditionFieldId() != null && inputParam.getParamConditionFieldId() != null) {
				isEquals = this.getParamConditionFieldId().equals(inputParam.getParamConditionFieldId());
			} else if (this.getParamConditionRecordConfigId() != null
					&& inputParam.getParamConditionRecordConfigId() != null) {
				isEquals = this.getParamConditionRecordConfigId().equals(inputParam.getParamConditionRecordConfigId());
			}
		}

		return isEquals;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InputParamType getType() {
		return type;
	}

	public void setType(InputParamType type) {
		this.type = type;
	}

	public String getParamConditionFieldId() {
		return paramConditionFieldId;
	}

	public void setParamConditionFieldId(String paramConditionFieldId) {
		this.paramConditionFieldId = paramConditionFieldId;
	}

	public String getParamConditionRecordConfigId() {
		return paramConditionRecordConfigId;
	}

	public void setParamConditionRecordConfigId(String paramConditionRecordConfigId) {
		this.paramConditionRecordConfigId = paramConditionRecordConfigId;
	}

}
