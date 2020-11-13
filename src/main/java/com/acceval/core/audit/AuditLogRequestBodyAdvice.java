package com.acceval.core.audit;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import com.acceval.core.amqp.AuditLogQueueSender;
import com.acceval.core.amqp.AuditLogRequest;
import com.acceval.core.amqp.AuditLogRequest.RequestType;
import com.acceval.core.security.PrincipalUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class AuditLogRequestBodyAdvice implements RequestBodyAdvice {

	@Autowired
	private AuditLogQueueSender auditLogQueueSender;

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		return inputMessage;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {

		// send log request
		String logEventID = PrincipalUtil.getAuditLogUUID();
		if (StringUtils.isNotBlank(logEventID)) {
			AuditLogRequest logRequest = new AuditLogRequest();
			logRequest.setRequestType(RequestType.REQUEST_BODY_ADV);
			logRequest.setUuid(logEventID);
			ObjectMapper objectMapper = new ObjectMapper();
			logRequest.setJson1(objectMapper.valueToTree(body).toString());
			new Thread(() -> {
				auditLogQueueSender.sendMessage(logRequest);
			}).start();
		}

		return body;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}

}
