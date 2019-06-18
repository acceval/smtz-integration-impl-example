package com.acceval.core.workflow.model;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskView<T> {
	public final String id;
	public final String processInstanceId;
	public final String name;
	public final Map<String, Object> variables;
	public final T dataObject;

	public final String assignee;
	public final String taskDescription;

	public TaskView(
			@JsonProperty("id") String id,
			@JsonProperty("processInstanceId") String processInstanceId,
			@JsonProperty("name") String name,
			@JsonProperty("variables") Map<String, Object> variables,
			@JsonProperty("dataObject") T dataObject,
			@JsonProperty("assignee") String assignee, 
			@JsonProperty("taskDescription") String taskDescription) {
		this.id = id;
		this.processInstanceId = processInstanceId;
		this.name = name;
		this.variables = variables;
		this.dataObject = dataObject;
		this.assignee = assignee;
		this.taskDescription = taskDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TaskView<?> taskView = (TaskView<?>) o;
		return Objects.equals(id, taskView.id) &&
				Objects.equals(processInstanceId, taskView.processInstanceId) &&
				Objects.equals(name, taskView.name) &&
				Objects.equals(variables, taskView.variables) &&
				Objects.equals(dataObject, taskView.dataObject) &&
				Objects.equals(assignee, taskView.assignee);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, processInstanceId, name, variables, dataObject, assignee);
	}
}