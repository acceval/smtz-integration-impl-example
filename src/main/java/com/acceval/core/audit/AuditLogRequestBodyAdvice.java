package com.acceval.core.audit;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import com.acceval.core.amqp.AuditLogQueueSender;

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
		// purpose of remark: new object will recapture in post EventLogHandlerInterceptor
		//		String logEventID = PrincipalUtil.getEventLogUUID();
		//		if (StringUtils.isNotBlank(logEventID)) {
		//			EventLogRequest logRequest = new EventLogRequest();
		//			logRequest.setRequestType(RequestType.REQUEST_BODY_ADV);
		//			logRequest.setUuid(logEventID);
		//			ObjectMapper objectMapper = new ObjectMapper();
		//			logRequest.setJson2(objectMapper.valueToTree(body).toString());
		//			eventLogQueueSender.sendMessage(logRequest);
		//		}

		return body;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}

}
