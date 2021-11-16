package com.acceval.core.job.logger;

import com.acceval.core.job.JobStatus;

public class JobLoggerRequest {
	
	private Long schedulerJobLogId;	
	private double progress;
	private JobStatus jobStatus;
	private Exception exception;
	private String message;
	private boolean isLogged;
		
	public Long getSchedulerJobLogId() {
		return schedulerJobLogId;
	}
	public void setSchedulerJobLogId(Long schedulerJobLogId) {
		this.schedulerJobLogId = schedulerJobLogId;
	}
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	public JobStatus getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isLogged() {
		return isLogged;
	}
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	
		
}
