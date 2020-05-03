package com.acceval.core.model;

import java.time.LocalDateTime;
import java.util.List;

import com.acceval.core.microservice.model.LabelValue;
import com.acceval.core.model.company.BaseCompanyModel;

public class Document extends BaseCompanyModel {
	
	private String documentName;
	private String filename;
	private String documentUrl;
	private double fileSize;
	private String contentType;
	private List<LabelValue> scannedTexts;
	
	public LocalDateTime getUploadTime() {
		return this.getCreated();
	}
	public void setUploadTime(LocalDateTime time) {
		this.setCreated(time);		
	}
	public String getUploadUser() {
		return this.getCreatedBy();
	}
	public void setUploadUser(String username) {
		this.setCreatedBy(username);
	}
	
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDocumentUrl() {
		return documentUrl;
	}
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public List<LabelValue> getScannedTexts() {
		return scannedTexts;
	}
	public void setScannedTexts(List<LabelValue> scannedTexts) {
		this.scannedTexts = scannedTexts;
	}	
}
