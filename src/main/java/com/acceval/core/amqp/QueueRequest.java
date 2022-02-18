package com.acceval.core.amqp;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;



public abstract class QueueRequest implements Serializable {
	
	private static final long serialVersionUID = 3571507840264400123L;
	private int priority;	
	private boolean isSecret;
	@JsonIgnore
	private String requestName;	
	
	public String getRequestName() {
		
		return this.getClass().getSimpleName();
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isSecret() {
		return isSecret;
	}

	public void setSecret(boolean isSecret) {
		this.isSecret = isSecret;
	}    
		
}
