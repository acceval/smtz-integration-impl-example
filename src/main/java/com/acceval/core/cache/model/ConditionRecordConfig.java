package com.acceval.core.cache.model;

import com.acceval.core.workflow.model.TaskAssignmentInputIF;

import java.io.Serializable;
import java.util.Set;

public class ConditionRecordConfig implements Serializable, TaskAssignmentInputIF {

	public enum ConditionRecordType {
        POLICY, COSTING, PLANNED_COST, MASTER, LIST_PRICE, MARKET_LIST_PRICE, QUANTITY_MANAGEMENT, RULES, MATRIX,
        RECORD_DECIDER, ARCHIVED
	}

	public enum DataGroupLevel {
		ORGANIZATION, DIVISION, DEPARTMENT, TEAM, ALL
	}

	public enum DateType {
		DATE_FROM_TO, DATE, NO_DATE
	}

	public enum DeciderType {
		PRICING_TECHNIQUE, HEADER_ITEM_CLASS, WORKFLOW, VOLUME_ALLOCATION
	}
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String code;

	private ConditionRecordType type;
	private DateType dateType;
	private boolean hasValidDate;
	private String validatorClass;

//	private WorkflowDef workflowDef;

	private DataGroupLevel dataGroupLevel;

	private Set<ConditionFieldWrapper> conditionFieldWrappers;
	private Set<ConditionValueConfig> conditionValueConfigs;

	private TaskAssignment taskAssignment;
	private String taskAssignParam1;
	private String taskAssignParam2;

	// warning msg
	private String monitorClass;
	private String longMsg;
	private String shortMsg;

	public String getMonitorClass() {
		return monitorClass;
	}

	public void setMonitorClass(String monitorClass) {
		this.monitorClass = monitorClass;
	}

	public TaskAssignment getTaskAssignment() {
		return taskAssignment;
	}

	public void setTaskAssignment(TaskAssignment taskAssignment) {
		this.taskAssignment = taskAssignment;
	}

	public String getTaskAssignParam1() {
		return taskAssignParam1;
	}

	public void setTaskAssignParam1(String taskAssignParam1) {
		this.taskAssignParam1 = taskAssignParam1;
	}

	public String getTaskAssignParam2() {
		return taskAssignParam2;
	}

	public void setTaskAssignParam2(String taskAssignParam2) {
		this.taskAssignParam2 = taskAssignParam2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DataGroupLevel getDataGroupLevel() {
		return dataGroupLevel;
	}

	public void setDataGroupLevel(DataGroupLevel dataGroupLevel) {
		this.dataGroupLevel = dataGroupLevel;
	}

	public Set<ConditionFieldWrapper> getConditionFieldWrappers() {
		return conditionFieldWrappers;
	}

	public void setConditionFieldWrappers(Set<ConditionFieldWrapper> conditionFieldWrappers) {
		this.conditionFieldWrappers = conditionFieldWrappers;
	}

	public Set<ConditionValueConfig> getConditionValueConfigs() {
		return conditionValueConfigs;
	}

	public void setConditionValueConfigs(Set<ConditionValueConfig> conditionValueConfigs) {
		this.conditionValueConfigs = conditionValueConfigs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ConditionRecordType getType() {
		return type;
	}

	public void setType(ConditionRecordType type) {
		this.type = type;
	}

	public DateType getDateType() {
		return dateType;
	}

	public void setDateType(DateType dateType) {
		this.dateType = dateType;
		if (this.dateType != DateType.NO_DATE) {
			this.hasValidDate = true;
		} else {
			this.hasValidDate = false;
		}
	}

	public boolean isHasValidDate() {
		return hasValidDate;
	}

	public void setHasValidDate(boolean hasValidDate) {
		this.hasValidDate = hasValidDate;
	}

	public String getValidatorClass() {
		return validatorClass;
	}

	public void setValidatorClass(String validatorClass) {
		this.validatorClass = validatorClass;
	}

	public String getLongMsg() {
		return longMsg;
	}

	public void setLongMsg(String longMsg) {
		this.longMsg = longMsg;
	}

	public String getShortMsg() {
		return shortMsg;
	}

	public void setShortMsg(String shortMsg) {
		this.shortMsg = shortMsg;
	}
}
