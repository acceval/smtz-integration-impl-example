package com.acceval.core.filehandler;

public class ErrorRecord {
	
	private int lineNumber;
	private Exception exception;
	private Object errorRecord;
	
	public ErrorRecord(int lineNo, Exception ex, Object errRecord) {
		this.lineNumber = lineNo;
		this.exception = ex;
		this.errorRecord = errRecord;
	}
		
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public Object getErrorRecord() {
		return errorRecord;
	}
	public void setErrorRecord(Object errorRecord) {
		this.errorRecord = errorRecord;
	}
	
}
