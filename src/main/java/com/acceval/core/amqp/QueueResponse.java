package com.acceval.core.amqp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.acceval.core.microservice.model.ResponseMessage;

public abstract class QueueResponse implements Serializable {
	
	private static final long serialVersionUID = 9183488518420234451L;

	public enum Status {
		SUCCESS, ERROR, BAD_REQUEST, UNAUTHORIZED, NOT_FOUND
	}
		
	private Status status;
	private List<ResponseMessage> messages;
	
	public QueueResponse() {
		 this.messages = new ArrayList<>();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<ResponseMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ResponseMessage> messages) {
		this.messages = messages;
	}
		
}
