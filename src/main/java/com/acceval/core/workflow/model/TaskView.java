package com.acceval.core.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public class TaskView<T> {
	public final String id;
	public final String processInstanceId;
	public final Map<String, Object> variables;
	public final T dataObject;

	public final String assignee;

	public TaskView(
			@JsonProperty("id") String id,
			@JsonProperty("processInstanceId") String processInstanceId,
			@JsonProperty("variables") Map<String, Object> variables,
			@JsonProperty("dataObject") T dataObject,
			@JsonProperty("assignee") String assignee) {
		this.id = id;
		this.processInstanceId = processInstanceId;
		this.variables = variables;
		this.dataObject = dataObject;
		this.assignee = assignee;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TaskView<?> taskView = (TaskView<?>) o;
		return Objects.equals(id, taskView.id) &&
				Objects.equals(processInstanceId, taskView.processInstanceId) &&
				Objects.equals(variables, taskView.variables) &&
				Objects.equals(dataObject, taskView.dataObject) &&
				Objects.equals(assignee, taskView.assignee);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, processInstanceId, variables, dataObject, assignee);
	}
}
