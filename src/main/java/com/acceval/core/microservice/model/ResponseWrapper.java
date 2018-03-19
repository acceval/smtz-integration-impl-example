package com.acceval.core.microservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.acceval.core.microservice.model.ResponseMessage.MessageType;

public class ResponseWrapper {
	public enum ApplicationHttpStatus {
		STATUS_OBJECT_NOT_FOUND(HttpStatus.NOT_FOUND), STATUS_APPLICATION_ERROR(HttpStatus.BAD_REQUEST);
		private final HttpStatus status;

		private ApplicationHttpStatus(HttpStatus status) {
			this.status = status;
		}

		public HttpStatus getStatus() {
			return status;
		}
	}

	private Object object;
	private List<ResponseMessage> messages = new ArrayList<>();

	public ResponseWrapper(Object object) {
		super();
		this.object = object;
	}

	public ResponseWrapper(Object object, List<ResponseMessage> messages) {
		super();
		this.object = object;
		this.messages = messages;
	}

	public ResponseWrapper(List<ResponseMessage> messages) {
		super();
		this.messages = messages;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public List<ResponseMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ResponseMessage> messages) {
		this.messages = messages;
	}

	public void addMessage(MessageType type, String message) {
		this.messages.add(new ResponseMessage(type, message));
	}

}
