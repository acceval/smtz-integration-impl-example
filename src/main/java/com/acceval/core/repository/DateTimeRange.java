package com.acceval.core.repository;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DateTimeRange implements Serializable {
	
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String propertyPath;
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getPropertyPath() {
		return propertyPath;
	}
	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}
	
	
	
}
