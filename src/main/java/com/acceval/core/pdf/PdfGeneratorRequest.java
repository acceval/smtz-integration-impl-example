package com.acceval.core.pdf;

import java.util.List;
import java.util.Map;

public class PdfGeneratorRequest {
	
	private String pdfName;
	private String bodyTemplate;
	private String headerTemplate;
	private String footerTemplate;
	private String htmlBody;
	private String htmlHeader;
	private String htmlFooter;
	private String stylesheet;
	private List<String> imagePaths;
	private Map<String, Object> variableContext;
	private Long companyId;
	
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public String getBodyTemplate() {
		return bodyTemplate;
	}
	public void setBodyTemplate(String bodyTemplate) {
		this.bodyTemplate = bodyTemplate;
	}
	public String getHeaderTemplate() {
		return headerTemplate;
	}
	public void setHeaderTemplate(String headerTemplate) {
		this.headerTemplate = headerTemplate;
	}
	public String getFooterTemplate() {
		return footerTemplate;
	}
	public void setFooterTemplate(String footerTemplate) {
		this.footerTemplate = footerTemplate;
	}
	public String getHtmlBody() {
		return htmlBody;
	}
	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}
	public String getHtmlHeader() {
		return htmlHeader;
	}
	public void setHtmlHeader(String htmlHeader) {
		this.htmlHeader = htmlHeader;
	}
	public String getHtmlFooter() {
		return htmlFooter;
	}
	public void setHtmlFooter(String htmlFooter) {
		this.htmlFooter = htmlFooter;
	}
	public Map<String, Object> getVariableContext() {
		return variableContext;
	}
	public void setVariableContext(Map<String, Object> variableContext) {
		this.variableContext = variableContext;
	}	
	public String getStylesheet() {
		return stylesheet;
	}
	public void setStylesheet(String stylesheet) {
		this.stylesheet = stylesheet;
	}
	public List<String> getImagePaths() {
		return imagePaths;
	}
	public void setImagePaths(List<String> imagePaths) {
		this.imagePaths = imagePaths;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}		
	
}
