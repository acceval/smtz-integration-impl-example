package com.acceval.core.security;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
			beanPaths = beanPaths == null || beanPaths.length == 0 ? beanRM.path() : beanPaths;
			if (beanPaths != null && beanPaths.length > 0) {
				url = beanPaths[0];
			}

			/** method path */
			String[] values = null;
			RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
			if (requestMapping != null) {
				values = requestMapping.value();
				values = values == null || values.length == 0 ? requestMapping.path() : values;
			}
			PostMapping postMapping = method.getDeclaredAnnotation(PostMapping.class);
			if (postMapping != null) {
				values = postMapping.path();
				values = values == null || values.length == 0 ? postMapping.path() : values;
			}
			PutMapping putMapping = method.getDeclaredAnnotation(PutMapping.class);
			if (putMapping != null) {
				values = putMapping.path();
				values = values == null || values.length == 0 ? putMapping.path() : values;
			}
			DeleteMapping deleteMapping = method.getDeclaredAnnotation(DeleteMapping.class);
			if (deleteMapping != null) {
				values = deleteMapping.path();
				values = values == null || values.length == 0 ? deleteMapping.path() : values;
			}
			PatchMapping patchMapping = method.getDeclaredAnnotation(PatchMapping.class);
			if (patchMapping != null) {
				values = patchMapping.path();
				values = values == null || values.length == 0 ? patchMapping.path() : values;
			}
			GetMapping getMapping = method.getDeclaredAnnotation(GetMapping.class);
			if (getMapping != null) {
				values = getMapping.path();
				values = values == null || values.length == 0 ? getMapping.path() : values;
			}
			if (values != null && values.length > 0) {
				url += values[0];
			}

			/** parsing Keys here */
			Map<String, String> mapValues =
					(Map<String, String>) request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
			List<String> lstPathVariable = new ArrayList<>();
			int index = -1;
			Integer start = null;
			Integer end = null;
			while (++index < url.length()) {
				switch (url.charAt(index)) {
					case '{':
						start = index;
						break;
					case '}':
						end = index;
						break;
					default:
						break;
				}
				if (start != null && end != null) {
					lstPathVariable.add(url.substring(start + 1, end));
					start = null;
					end = null;
				}
			}
			if (lstPathVariable.size() >= 1) {
				logRequest.setKey1(mapValues.get(lstPathVariable.get(0)));
			}
			if (lstPathVariable.size() >= 2) {
				logRequest.setKey1(mapValues.get(lstPathVariable.get(1)));
			}
			if (lstPathVariable.size() >= 3) {
				logRequest.setKey1(mapValues.get(lstPathVariable.get(2)));
			}

			/** try scan standard CRUD Event Action */
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
				} else if (splitPattern.length == 2 && RequestMethod.POST.toString().equals(httpMethod)) {
					eventAction = EventAction.CREATE;
				}
			}

			/** logging defined Event Action in controller with @EventLog */
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
			if (currentUser != null && currentUser.getId() != null) {
				logRequest.setUserID(currentUser.getId());
				logRequest.setEmail(currentUser.getEmail());
				logRequest.setCompanyID(currentUser.getCompanyId());
			}
			logRequest.setIpAddress(request.getLocalAddr());
			logRequest.setHttpMethod(request.getMethod());
			logRequest.setLogTime(new Date());
			String token = PrincipalUtil.getToken();
			if (StringUtils.isNotBlank(token)) {
				logRequest.setToken(token.replace("Bearer ", ""));
			}

			/** sending log request to MQ */
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
