package com.acceval.core.cache.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ConditionFieldConfig implements Serializable, Comparable<ConditionFieldConfig> {

	public enum ConditionFieldType {
		ENTITY,
		CONSTANT,
		RANGE,
		CUSTOMOBJECT,
		REGIONCOUNTRY,
		DATE, ENTITY_REMOTE
	}

	public enum InputType {
		TEXTFIELD, 
		DROPDOWN, 
		AUTOCOMPLETE, 
		DATEFIELD, 
		NUMBERFIELD
	}
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;	
	private String code;

	private ConditionFieldType type;
	private String entityClass;
	private String entityDisplayField;
	private String dataSource;
	private String label1;
	private String label2;
	private String description1;
	private String description2;

	private InputType inputType;
	private String customObject;

	private String serviceUrl;
	
	private RangeGroup rangeGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public ConditionFieldType getType() {
		return type;
	}

	public void setType(ConditionFieldType type) {
		this.type = type;
	}

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
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

	public String getEntityDisplayField() {
		return entityDisplayField;
	}

	public void setEntityDisplayField(String entityDisplayField) {
		this.entityDisplayField = entityDisplayField;
	}
	
	public RangeGroup getRangeGroup() {
		return rangeGroup;
	}

	public void setRangeGroup(RangeGroup rangeGroup) {
		this.rangeGroup = rangeGroup;
	}
	
	public String getCustomObject() {
		return customObject;
	}

	public void setCustomObject(String customeObject) {
		this.customObject = customeObject;
	}

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
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
	 public int hashCode(){
	  if (id != null) return id.hashCode();
	  return -1;
	}

	@Override
	public boolean equals(Object object) {
		boolean isEquals = false;

		if (object != null && object instanceof ConditionFieldConfig) {

			isEquals = this.code == ((ConditionFieldConfig) object).code;
		}

		return isEquals;
	}
	
	@Override
	public int compareTo(ConditionFieldConfig o) {
		
		return name.compareTo(o.getName());
	}
}
