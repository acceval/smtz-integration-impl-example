package com.acceval.core.cache.model;

import com.acceval.core.cache.model.ConditionFieldConfig.InputType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ConditionValueConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public enum ConditionValueType {
		NUMBER, PERCENTAGE, AMOUNT, UNIT_AMOUNT, QUANTITY, ENTITY, CONSTANT, FORMULA, REGIONCOUNTRY
	}

	private Long id;

	private String name;
	private String code;
	private String columnName;
	private int displayOrder;
	private boolean isMandatory;

	private ConditionRecordConfig conditionRecordConfig;

	private ConditionValueType type;

	private int decimalPoint;

	private String entityClass;
	private String entityDisplayField;

	private String dataSource;
	private InputType inputType;
	private String serviceUrl;

	private Currency defaultCurrency;
	private Uom defaultUom;
	
	private String category;

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

	public ConditionValueType getType() {
		return type;
	}

	public void setType(ConditionValueType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public int getDecimalPoint() {
		return decimalPoint;
	}

	public void setDecimalPoint(int decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	public Currency getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(Currency defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public Uom getDefaultUom() {
		return defaultUom;
	}

	public void setDefaultUom(Uom defaultUom) {
		this.defaultUom = defaultUom;
	}

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public String getEntityDisplayField() {
		return entityDisplayField;
	}

	public void setEntityDisplayField(String entityDisplayField) {
		this.entityDisplayField = entityDisplayField;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public boolean isNumericValue() {

		switch (type) {
			case NUMBER:
				return true;
			case PERCENTAGE:
				return true;
			case AMOUNT:
				return true;
			case UNIT_AMOUNT:
				return true;
			case QUANTITY:
				return true;
			case ENTITY:
				return false;
			case CONSTANT:
				return false;
			case FORMULA:
				return false;	
		}

		return true;

	}

	public boolean isContainsCurrency() {

		switch (type) {
			case NUMBER:
				return false;
			case PERCENTAGE:
				return false;
			case AMOUNT:
				return true;
			case UNIT_AMOUNT:
				return true;
			case QUANTITY:
				return false;
			case ENTITY:
				return false;
			case CONSTANT:
				return false;
			case FORMULA:
				return false;	
		}

		return false;
	}

	public boolean isContainsUom() {

		switch (type) {
			case NUMBER:
				return false;
			case PERCENTAGE:
				return false;
			case AMOUNT:
				return false;
			case UNIT_AMOUNT:
				return true;
			case QUANTITY:
				return true;
			case ENTITY:
				return false;
			case CONSTANT:
				return false;
			case FORMULA:
				return false;
		}

		return false;
	}

	@JsonIgnore
	public boolean isRemote() {

		if (this.entityClass != null && !this.entityClass.contains("com.acceval.masterdata.model")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean equals(Object object) {

		boolean isEquals = false;

		if (object != null && object instanceof ConditionValueConfig) {

			ConditionValueConfig config = (ConditionValueConfig) object;

			if (this.getColumnName() != null && config.getColumnName() != null) {

				isEquals = this.getColumnName().equals(config.getColumnName());
			}
		}

		return isEquals;
	}

	@Override
	public int hashCode() {
		if (id != null) return id.hashCode();
		return -1;
	}

}
