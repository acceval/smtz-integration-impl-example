package com.acceval.core.amqp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.acceval.core.audit.AuditType;

public class AuditTrailRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String uuid;
	private LocalDateTime logTime;
	private AuditType auditType;
	private Long userId;
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	private Long companyId;
	private String companyCode;
	
	/** keys */
	private String info1;
	private String info2;
	private String info3;
	
//	private List<String> changedValues;
	private List<AuditTrailChange> auditTrailChanges;

	
	public LocalDateTime getLogTime() {
		return logTime;
	}

	public void setLogTime(LocalDateTime logTime) {
		this.logTime = logTime;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public AuditType getAuditType() {
		return auditType;
	}

	public void setAuditType(AuditType auditType) {
		this.auditType = auditType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public String getInfo3() {
		return info3;
	}

	public void setInfo3(String info3) {
		this.info3 = info3;
	}

	public List<AuditTrailChange> getAuditTrailChanges() {
		return auditTrailChanges;
	}

	public void setAuditTrailChanges(List<AuditTrailChange> auditTrailChanges) {
		this.auditTrailChanges = auditTrailChanges;
	}

}
