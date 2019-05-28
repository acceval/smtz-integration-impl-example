package com.acceval.core.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CompleteTaskRequest {
	public final Map<String, Object> variable;

	public CompleteTaskRequest(
			@JsonProperty("variable") Map<String, Object> variable) {
		this.variable = variable;
	}
}
