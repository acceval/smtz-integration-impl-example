package com.acceval.core.workflow.model;

import java.io.Serializable;
import java.util.Date;

public class HistoryTask implements Serializable {
	private static final long serialVersionUID = 1L;

	private String activityName;
	private Date startTime;
	private Date endTime;
	private String assignee;
	private String assigneeName;
	private long durationInMillis;
	private String durationInString;
	private String decision;
	private String reason;

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public long getDurationInMillis() {
		return durationInMillis;
	}

	public void setDurationInMillis(long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}

	public String getDurationInString() {
		return durationInString;
	}

	public void setDurationInString(String durationInString) {
		this.durationInString = durationInString;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
