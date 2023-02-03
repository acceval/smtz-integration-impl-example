package com.acceval.core.workflow.model;

import java.time.LocalDateTime;
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
	public String assigneeName;
	public final String taskDescription;

	public final LocalDateTime createTime;
	public final LocalDateTime dueDate;

	public String workflowStatus;

	public String taskOwnerName;
	public String taskOwnerDepartment;
	public String dealTaskDescription;
	public String documentType;
	public String decision;
	public String reason;
	public Long detailsId;
	public String remark;
	
	public TaskView(
			@JsonProperty("id") String id,
			@JsonProperty("processInstanceId") String processInstanceId,
			@JsonProperty("name") String name,
			@JsonProperty("variables") Map<String, Object> variables,
			@JsonProperty("dataObject") T dataObject,
			@JsonProperty("assignee") String assignee, 
			@JsonProperty("taskDescription") String taskDescription,
			@JsonProperty("createTime") LocalDateTime createTime,
			@JsonProperty("dueDate") LocalDateTime dueDate) {
		this.id = id;
		this.processInstanceId = processInstanceId;
		this.name = name;
		this.variables = variables;
		this.dataObject = dataObject;
		this.assignee = assignee;
		this.taskDescription = taskDescription;
		this.createTime = createTime;
		this.dueDate = dueDate;
	}
	
	public String getDealTaskDescription() {
		return dealTaskDescription;
	}

	public void setDealTaskDescription(String dealTaskDescription) {
		this.dealTaskDescription = dealTaskDescription;
	}

	public String getTaskOwnerDepartment() {
		return taskOwnerDepartment;
	}

	public void setTaskOwnerDepartment(String taskOwnerDepartment) {
		this.taskOwnerDepartment = taskOwnerDepartment;
	}

	public String getTaskOwnerName() {
		return taskOwnerName;
	}

	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
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

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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

	public Long getDetailsId() {
		return detailsId;
	}

	public void setDetailsId(Long detailsId) {
		this.detailsId = detailsId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
