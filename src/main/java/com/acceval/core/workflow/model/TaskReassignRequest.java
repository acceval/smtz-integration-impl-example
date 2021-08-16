package com.acceval.core.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskReassignRequest {
	public final String userId;

	public TaskReassignRequest(@JsonProperty("userId") String userId) {
		this.userId = userId;
	}
}
