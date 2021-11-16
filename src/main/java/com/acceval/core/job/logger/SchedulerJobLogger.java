package com.acceval.core.job.logger;

public interface SchedulerJobLogger {

	void logProgress(Long schedulerJobLogId, double progress);
	
	void logProgress(Long schedulerJobLogId, int currentCount, int totalCount);
	
	void logSuccess(Long schedulerJobLogId);
	
	void logError(Long schedulerJobLogId);
	
	void logError(Long schedulerJobLogId, String message);
	
	void logError(Long schedulerJobLogId, Exception exception);
	
	void setServiceClientName(String clientName);
}
