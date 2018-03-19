package com.acceval.core.microservice.model;

public class ResponseMessage {
	public enum MessageType {
		INFO, ERROR, WARNING
	}

	private MessageType type;
	private String message;

	public ResponseMessage(MessageType type, String message) {
		super();
		this.type = type;
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
