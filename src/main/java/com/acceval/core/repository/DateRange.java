package com.acceval.core.repository;

import java.io.Serializable;
import java.time.LocalDate;

public class DateRange implements Serializable {
	
	private LocalDate startDate;
	private LocalDate endDate;
	private String propertyPath;
	
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getPropertyPath() {
		return propertyPath;
	}
	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}
	
	
}
