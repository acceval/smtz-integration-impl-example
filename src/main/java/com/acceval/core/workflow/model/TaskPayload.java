package com.acceval.core.workflow.model;

import java.io.Serializable;

public class TaskPayload implements Serializable{
	private static final long serialVersionUID = 1L;

	private String taskDescription;

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

}
