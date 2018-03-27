package com.acceval.core.microservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.acceval.core.microservice.ObjectNotFoundException;
import com.acceval.core.microservice.model.ResponseMessage.MessageType;

public class ResponseWrapper<T> {
	
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

	private T object;
	private List<ResponseMessage> messages = new ArrayList<>();

	public ResponseEntity<ResponseWrapper<T>> buildExceptionResponse(T obj, Throwable ex) {

		if (ex instanceof ObjectNotFoundException) {
			
			this.setObject(obj);
			this.addMessage(MessageType.ERROR, ex.getMessage());
			return ResponseEntity.status(ApplicationHttpStatus.STATUS_OBJECT_NOT_FOUND.getStatus()).body(this);
		} else {
			
			this.setObject(obj);
			this.addMessage(MessageType.ERROR, ex.getMessage());
			return ResponseEntity.status(ApplicationHttpStatus.STATUS_APPLICATION_ERROR.getStatus()).body(this);
		}
	}
	
	public ResponseWrapper() {
		
	}
	
	public ResponseWrapper(T object) {
		super();
		this.object = object;
	}

	public ResponseWrapper(T object, List<ResponseMessage> messages) {
		super();
		this.object = object;
		this.messages = messages;
	}

	public ResponseWrapper(List<ResponseMessage> messages) {
		super();
		this.messages = messages;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
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
