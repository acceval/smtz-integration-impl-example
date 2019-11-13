package com.acceval.core.amqp;

import java.io.Serializable;
import java.util.Date;

import com.acceval.core.controller.AuditLog.AuditAction;
import com.acceval.core.model.ServicePackage;

public class AuditLogRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum RequestType {
		PRE_ZUUL, POST_ZUUL, CONTROLLER, SERVICE, FORGET_PASSWORD, LOGIN, REQUEST_BODY_ADV
	}

	private String uuid;
	private long userID;
	private String email;
	private long companyID;
	private String ipAddress;
	private String token;
	private String url;
	private String httpMethod;
	private int httpStatus;
	private Date logTime;
	private AuditAction auditAction;
	private String companyCode;				
	private String schemaName;
	private String timeZone;
	private ServicePackage servicePackage;

	/** keys */
	private String key1;
	private String key2;
	private String key3;

	/** params */
	private String param1;
	private String param2;
	private String param3;

	/** jsons */
	private String json1;
	private String json2;
	private String json3;

	private RequestType requestType;// use for listener

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public AuditAction getAuditAction() {
		return auditAction;
	}

	public void setAuditAction(AuditAction auditAction) {
		this.auditAction = auditAction;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getJson1() {
		return json1;
	}

	public void setJson1(String json1) {
		this.json1 = json1;
	}

	public String getJson2() {
		return json2;
	}

	public void setJson2(String json2) {
		this.json2 = json2;
	}

	public String getJson3() {
		return json3;
	}

	public void setJson3(String json3) {
		this.json3 = json3;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public ServicePackage getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(ServicePackage servicePackage) {
		this.servicePackage = servicePackage;
	}
	
	
}