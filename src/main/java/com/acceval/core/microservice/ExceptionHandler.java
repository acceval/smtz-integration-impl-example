package com.acceval.core.microservice;

import org.springframework.http.ResponseEntity;

import com.acceval.core.microservice.model.ResponseMessage.MessageType;
import com.acceval.core.microservice.model.ResponseWrapper;
import com.acceval.core.microservice.model.ResponseWrapper.ApplicationHttpStatus;

public class ExceptionHandler {

	public static ResponseEntity<ResponseWrapper> buildExceptionResponse(Object obj, Throwable ex) {

		if (ex instanceof ObjectNotFoundException) {
			ResponseWrapper wrapper = new ResponseWrapper(obj);
			wrapper.addMessage(MessageType.ERROR, ex.getMessage());
			return ResponseEntity.status(ApplicationHttpStatus.STATUS_OBJECT_NOT_FOUND.getStatus()).body(wrapper);
		} else {
			ResponseWrapper wrapper = new ResponseWrapper(obj);
			wrapper.addMessage(MessageType.ERROR, ex.getMessage());
			return ResponseEntity.status(ApplicationHttpStatus.STATUS_APPLICATION_ERROR.getStatus()).body(wrapper);
		}
	}

}
