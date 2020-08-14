package com.acceval.core.controller;

public class FunctionObject {
	public static final String CRUD_CREATE_DESC = "Create";
	public static final String CRUD_READ_DESC = "Read";
	public static final String CRUD_UPDATE_DESC = "Update";
	public static final String CRUD_DELETE_DESC = "Delete";

	public static final String CRUD_CREATE = "CREATE";
	public static final String CRUD_READ = "READ";
	public static final String CRUD_UPDATE = "UPDATE";
	public static final String CRUD_DELETE = "DELETE";
	public static final String SYSTEM = "SYSTEM";// mark as system, use for bypass function access

	public static String ACCESS_ALL = "ALL";

	private String microService; // group
	private String module; // group
	private String clientAccess; // access used in client
	private String serverUrl; // server
	private String description;
	private String httpMethod; // for standard CRUD, server

	public FunctionObject() {
		super();
	}

	public FunctionObject(String microService, String module, String clientAccess, String serverUrl, String description,
			String httpMethod) {
		super();
		this.microService = microService;
		this.module = module;
		this.clientAccess = clientAccess;
		this.serverUrl = serverUrl;
		this.description = description;
		this.httpMethod = httpMethod;
	}

	public String getMicroService() {
		return microService;
	}

	public void setMicroService(String microService) {
		this.microService = microService;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getClientAccess() {
		return clientAccess;
	}

	public void setClientAccess(String clientAccess) {
		this.clientAccess = clientAccess;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

}
