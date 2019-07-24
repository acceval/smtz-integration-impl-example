package com.acceval.core.filehandler;

import java.time.LocalDateTime;
import java.util.List;

public class UploadResult {

	private String name;
	private String fileFunction;
	private String filename;
	private int totalRecord;
	private int successCount;
	private int failureCount;
	private ErrorRecord[] errorRecords;		
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private long processSeconds; 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileFunction() {
		return fileFunction;
	}
	public void setFileFunction(String fileFunction) {
		this.fileFunction = fileFunction;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}
	public ErrorRecord[] getErrorRecords() {
		return errorRecords;
	}
	public void setErrorRecords(ErrorRecord[] errorRecords) {
		this.errorRecords = errorRecords;
	}	
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public long getProcessSeconds() {
		return processSeconds;
	}
	public void setProcessSeconds(long processSeconds) {
		this.processSeconds = processSeconds;
	}
	
}
