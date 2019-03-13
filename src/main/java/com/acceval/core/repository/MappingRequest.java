package com.acceval.core.repository;

import org.springframework.util.MultiValueMap;

public class MappingRequest {

	private Class<?> mappingClass;
	private MultiValueMap<String, String> mapParams;
	private String mappingField;
	private String destinationField;
	private boolean isCollection;

	public Class<?> getMappingClass() {
		return mappingClass;
	}

	public void setMappingClass(Class<?> mappingClass) {
		this.mappingClass = mappingClass;
	}

	public MultiValueMap<String, String> getMapParams() {
		return mapParams;
	}

	public void setMapParams(MultiValueMap<String, String> mapParams) {
		this.mapParams = mapParams;
	}

	public String getMappingField() {
		return mappingField;
	}

	public void setMappingField(String mappingField) {
		this.mappingField = mappingField;
	}

	public String getDestinationField() {
		return destinationField;
	}

	public void setDestinationField(String destinationField) {
		this.destinationField = destinationField;
	}

	public boolean isCollection() {
		return isCollection;
	}

	public void setCollection(boolean isCollection) {
		this.isCollection = isCollection;
	}

}
