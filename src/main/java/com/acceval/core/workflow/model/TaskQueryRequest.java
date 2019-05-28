package com.acceval.core.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class TaskQueryRequest {
	public final Map<String, Object> variable;

	public TaskQueryRequest(@JsonProperty("variable") Map<String, Object> variable) {
		this.variable = variable;
	}
}
