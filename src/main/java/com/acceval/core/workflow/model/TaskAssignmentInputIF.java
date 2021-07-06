package com.acceval.core.workflow.model;

public interface TaskAssignmentInputIF {

	final static String ASSIGN_USER_ID = "assignUserID";
	final static String ASSIGN_USER_NAME = "assignUserName";

	enum TaskAssignment {
		REPORT_TO("REPORT_TO", "Report To"),
		DIRECT_TO("DIRECT_TO", "Direct To"),
		DESIGNATION("DESIGNATION", "Designation"),
		CONDITION_TABLE("CONDITION_TABLE", "Condition Table");

		public final String code;
		public final String name;
		private TaskAssignment(String code, String name) {
			this.code = code;
			this.name = name;
		}
	}
	
	TaskAssignment getTaskAssignment();
	
	String getTaskAssignParam1();
	
	String getTaskAssignParam2();
}
