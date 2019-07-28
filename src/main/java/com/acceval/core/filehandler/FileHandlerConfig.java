package com.acceval.core.filehandler;

public class FileHandlerConfig {
		
	private String name;	
	private String description;
	private String fileFunction;	
	private String fileTemplateClass;
	private String entityClass;
	private String downloadTemplateUrl;
	private String fileUploadUrl;
	private int ignoreLines;
	private boolean isSampleTemplate;
	
	public String getFileFunction() {
		return fileFunction;
	}
	public void setFileFunction(String fileFunction) {
		this.fileFunction = fileFunction;
	}	
	public String getFileTemplateClass() {
		return fileTemplateClass;
	}
	public void setFileTemplateClass(String fileTemplateClass) {
		this.fileTemplateClass = fileTemplateClass;
	}
	public String getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	public String getDownloadTemplateUrl() {
		return downloadTemplateUrl;
	}
	public void setDownloadTemplateUrl(String downloadTemplateUrl) {
		this.downloadTemplateUrl = downloadTemplateUrl;
	}
	public String getFileUploadUrl() {
		return fileUploadUrl;
	}
	public void setFileUploadUrl(String fileUploadUrl) {
		this.fileUploadUrl = fileUploadUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIgnoreLines() {
		return ignoreLines;
	}
	public void setIgnoreLines(int ignoreLines) {
		this.ignoreLines = ignoreLines;
	}
	public boolean isSampleTemplate() {
		return isSampleTemplate;
	}
	public void setSampleTemplate(boolean isSampleTemplate) {
		this.isSampleTemplate = isSampleTemplate;
	}	
	
}
