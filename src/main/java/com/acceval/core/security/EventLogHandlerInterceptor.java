package com.acceval.core.security;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.acceval.core.amqp.EventLogQueueSender;
import com.acceval.core.amqp.EventLogRequest;
import com.acceval.core.amqp.EventLogRequest.RequestType;
import com.acceval.core.controller.EventLog;
import com.acceval.core.controller.EventLog.EventAction;

@Component
public class EventLogHandlerInterceptor implements HandlerInterceptor {

	@Autowired
	private EventLogQueueSender eventLogQueueSender;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			String httpMethod = request.getMethod();

			String eventLogUUID = request.getHeader("event-log-id");
			if (StringUtils.isBlank(eventLogUUID)) return true;

			boolean isLog = false;
			EventLogRequest logRequest = new EventLogRequest();
			EventAction eventAction = null;
			String url = null;

			/** controller path */
			RequestMapping beanRM = handlerMethod.getBeanType().getDeclaredAnnotation(RequestMapping.class);
			String[] beanPaths = beanRM.value();
			if (beanPaths != null && beanPaths.length > 0) {
				url = beanPaths[0];
			}

			/** method path */
			String[] values = null;
			RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
			if (requestMapping != null) {
				values = requestMapping.value();
			}
			PostMapping postMapping = method.getDeclaredAnnotation(PostMapping.class);
			if (postMapping != null) {
				values = postMapping.value();
			}
			PutMapping putMapping = method.getDeclaredAnnotation(PutMapping.class);
			if (putMapping != null) {
				values = putMapping.value();
			}
			DeleteMapping deleteMapping = method.getDeclaredAnnotation(DeleteMapping.class);
			if (deleteMapping != null) {
				values = deleteMapping.value();
			}
			PatchMapping patchMapping = method.getDeclaredAnnotation(PatchMapping.class);
			if (patchMapping != null) {
				values = patchMapping.value();
			}
			GetMapping getMapping = method.getDeclaredAnnotation(GetMapping.class);
			if (getMapping != null) {
				values = getMapping.value();
			}
			if (values != null && values.length > 0) {
				url += values[0];
			}

			/** try scan standard CRUD event action */
			if (StringUtils.isNotBlank(url)) {
				String[] splitPattern = url.split("/");
				if (splitPattern.length == 3) {
					if (splitPattern[2].contains("{")) {
						if (RequestMethod.DELETE.toString().equals(httpMethod)) {
							eventAction = EventAction.DELETE;
						} else if (RequestMethod.PUT.toString().equals(httpMethod)) {
							eventAction = EventAction.UPDATE;
						} else if (RequestMethod.GET.toString().equals(httpMethod)) {
							eventAction = EventAction.READ;
						}
					} else if ("search".equals(splitPattern[2])) {
						eventAction = EventAction.SEARCH;
					}
				}
			}

			/** logging defined Event Action */
			EventLog annoEventLog = method.getDeclaredAnnotation(EventLog.class);
			if (annoEventLog != null) {
				if (annoEventLog.eventAction() != null) {
					eventAction = annoEventLog.eventAction();
				}
			}
			if (eventAction != null) {
				isLog = true;
			}

			/** other info */
			CurrentUser currentUser = PrincipalUtil.getCurrentUser();
			if (currentUser != null) {
				logRequest.setUserID(currentUser.getId());
				logRequest.setEmail(currentUser.getEmail());
				logRequest.setCompanyID(currentUser.getCompanyId());
			}
			logRequest.setIpAddress(request.getRemoteAddr());
			logRequest.setHttpMethod(request.getMethod());
			logRequest.setLogTime(new Date());

			/** sending log request */
			if (isLog && StringUtils.isNotBlank(eventLogUUID)) {
				logRequest.setUuid(eventLogUUID);
				logRequest.setRequestType(RequestType.CONTROLLER);
				logRequest.setEventAction(eventAction);
				logRequest.setUrl(url);
				eventLogQueueSender.sendMessage(logRequest);
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}
