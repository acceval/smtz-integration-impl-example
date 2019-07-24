package com.acceval.core.filehandler;

public class ErrorRecord {
	
	private int lineNumber;	
	private String errorMessage;
	private String lineContent;
	
	public ErrorRecord(int lineNumber, String errorMessage) {
		this.lineNumber = lineNumber;		
		this.errorMessage = errorMessage;		
	}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getLineContent() {
		return lineContent;
	}

	public void setLineContent(String lineContent) {
		this.lineContent = lineContent;
	}
	
}
