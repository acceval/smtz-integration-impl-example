package com.acceval.core.amqp.audit;

import java.io.Serializable;
import java.util.Date;

import com.acceval.core.amqp.QueueRequest;

public class PathAuthenticationRequest extends QueueRequest {
	
	private static final long serialVersionUID = -1568354515288058076L;
	private String principal;
	private Date timestamp;
	private String type;
	private String remoteAddress;
	private String tokenType;
	private String tokenValue;
	
	public PathAuthenticationRequest() {		
	}
	
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

}
