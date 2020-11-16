package com.acceval.core.audit;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

import com.acceval.core.amqp.AuditLogQueueSender;
import com.acceval.core.amqp.AuditLogRequest;
import com.acceval.core.amqp.AuditLogRequest.RequestType;
import com.acceval.core.controller.AuditLog;
import com.acceval.core.controller.AuditLog.AuditAction;
import com.acceval.core.controller.GenericCommonController;
import com.acceval.core.security.CurrentUser;
import com.acceval.core.security.PrincipalUtil;
import com.acceval.core.util.ClassUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuditLogHandlerInterceptor implements HandlerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuditLogQueueSender auditLogQueueSender;

	@Autowired
	private GenericCommonController genericCommonController;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			String httpMethod = request.getMethod();

			String auditLogUUID = request.getHeader("audit-log-id");
			if (StringUtils.isBlank(auditLogUUID)) return true;

			boolean isLog = false;
			AuditLogRequest logRequest = new AuditLogRequest();
			String url = buildUrl(handlerMethod);

			/** parsing Keys here */
			Map<String, String> mapValues =
					(Map<String, String>) request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
			List<String> lstPathVariable = buildLstPathVariable(url);
			if (lstPathVariable.size() >= 1) {
				logRequest.setKey1(mapValues.get(lstPathVariable.get(0)));
			}
			if (lstPathVariable.size() >= 2) {
				logRequest.setKey2(mapValues.get(lstPathVariable.get(1)));
			}
			if (lstPathVariable.size() >= 3) {
				logRequest.setKey3(mapValues.get(lstPathVariable.get(2)));
			}

			AuditAction auditAction = buildAuditAction(url, httpMethod, method);
			if (auditAction != null) {
				isLog = true;
			}

			/** try jsoning the object for update Audit Action (this is old value) */
			if (AuditLog.AuditAction.UPDATE.equals(auditAction)) {
				try {
					Type returnType = method.getGenericReturnType();// get entity base on return type!
					String typeName = returnType.getTypeName();
					String[] splitTypeName = typeName.split("[<>]");
					String entityName = splitTypeName != null && splitTypeName.length > -1 ? splitTypeName[splitTypeName.length - 1] : null;
					if (entityName != null && entityName.indexOf("com.acceval.") > -1 && lstPathVariable.size() == 1) {
						String pkName = ClassUtil.getPrimaryKeyName(ClassUtil.getClass(entityName));
						if (StringUtils.isNotBlank(pkName)) {
							MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
							mvm.add(GenericCommonController.KEY_IS_COLLECTION, "N");
							mvm.add(GenericCommonController.KEY_ENTITY_CLASS, entityName);
							mvm.add(pkName, logRequest.getKey1());
							Object obj = genericCommonController.query(mvm);

							// Jsoning
							if (obj != null) {
								ObjectMapper objectMapper = new ObjectMapper();
								JsonNode jsonNode = objectMapper.valueToTree(obj);
								logRequest.setJson1(jsonNode.toString());
							}
						}
					}
				} catch (Throwable t) {
					logger.error(t.getMessage(), t);
				}
			}

			/** other info */
			CurrentUser currentUser = PrincipalUtil.getCurrentUser();
			if (currentUser != null && currentUser.getId() != null) {
				logRequest.setUserID(currentUser.getId());
				logRequest.setEmail(currentUser.getEmail());
				logRequest.setCompanyID(currentUser.getCompanyId());
				logRequest.setCompanyCode(currentUser.getCompanyCode());
				logRequest.setSchemaName(currentUser.getSchemaName());
				logRequest.setTimeZone(currentUser.getTimeZone());
			}
			logRequest.setHttpMethod(request.getMethod());
			logRequest.setLogTime(new Date());
			String token = PrincipalUtil.getToken();
			if (StringUtils.isNotBlank(token)) {
				logRequest.setToken(token.replace("Bearer ", ""));
			}

			/** sending log request to MQ */
			if (isLog && StringUtils.isNotBlank(auditLogUUID)) {
				logRequest.setUuid(auditLogUUID);
				logRequest.setRequestType(RequestType.CONTROLLER);
				logRequest.setAuditAction(auditAction);
				logRequest.setUrl(url);
				//				new Thread(() -> {
					auditLogQueueSender.sendMessage(logRequest);
				//				}).start();
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			String httpMethod = request.getMethod();

			String auditLogUUID = request.getHeader("audit-log-id");
			if (StringUtils.isBlank(auditLogUUID)) return;

			boolean isLog = false;
			AuditLogRequest logRequest = new AuditLogRequest();
			String url = buildUrl(handlerMethod);

			/** parsing Keys here */
			Map<String, String> mapValues =
					(Map<String, String>) request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
			List<String> lstPathVariable = buildLstPathVariable(url);
			if (lstPathVariable.size() > 0) {
				String key1 = mapValues.get(lstPathVariable.get(0));

				AuditAction auditAction = buildAuditAction(url, httpMethod, method);

				/** try jsoning the object for update Audit Action (this is new value) */
				if (AuditLog.AuditAction.UPDATE.equals(auditAction)) {
					try {
						Type returnType = method.getGenericReturnType();// get entity base on return type!
						String typeName = returnType.getTypeName();
						String[] splitTypeName = typeName.split("[<>]");
						String entityName =
								splitTypeName != null && splitTypeName.length > -1 ? splitTypeName[splitTypeName.length - 1] : null;
						if (entityName != null && entityName.indexOf("com.acceval.") > -1 && lstPathVariable.size() == 1) {
							String pkName = ClassUtil.getPrimaryKeyName(ClassUtil.getClass(entityName));
							if (StringUtils.isNotBlank(pkName)) {
								MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
								mvm.add(GenericCommonController.KEY_IS_COLLECTION, "N");
								mvm.add(GenericCommonController.KEY_ENTITY_CLASS, entityName);
								mvm.add(pkName, key1);
								Object obj = genericCommonController.query(mvm);

								// Jsoning
								if (obj != null) {
									ObjectMapper objectMapper = new ObjectMapper();
									JsonNode jsonNode = objectMapper.valueToTree(obj);
									logRequest.setJson2(jsonNode.toString());
									isLog = true;
								}
							}
						}
					} catch (Throwable t) {
						logger.error(t.getMessage(), t);
					}
				}
			}

			/** other info */
			CurrentUser currentUser = PrincipalUtil.getCurrentUser();
			if (currentUser != null && currentUser.getId() != null) {
				logRequest.setUserID(currentUser.getId());
				logRequest.setEmail(currentUser.getEmail());
				logRequest.setCompanyID(currentUser.getCompanyId());
				logRequest.setCompanyCode(currentUser.getCompanyCode());
				logRequest.setSchemaName(currentUser.getSchemaName());
				logRequest.setTimeZone(currentUser.getTimeZone());
			}

			/** sending log request to MQ */
			if (isLog && StringUtils.isNotBlank(auditLogUUID)) {
				logRequest.setUuid(auditLogUUID);
				logRequest.setRequestType(RequestType.CONTROLLER);
				//				new Thread(() -> {
					auditLogQueueSender.sendMessage(logRequest);
				//				}).start();
			}
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

	private String buildUrl(HandlerMethod handler) {
		String url = null;
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			/** controller path, for generate URL */
			RequestMapping beanRM = handlerMethod.getBeanType().getDeclaredAnnotation(RequestMapping.class);
			String[] beanPaths = beanRM.value();
			beanPaths = beanPaths == null || beanPaths.length == 0 ? beanRM.path() : beanPaths;
			if (beanPaths != null && beanPaths.length > 0) {
				url = beanPaths[0];
			}

			/** method path, for generate URL */
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

		}

		return url;
	}

	private AuditAction buildAuditAction(String url, String httpMethod, Method method) {
		AuditAction auditAction = null;
		/** try scan standard CRUD Audit Action */
		if (StringUtils.isNotBlank(url)) {
			String[] splitPattern = url.split("/");
			if (splitPattern.length == 3) {
				if (splitPattern[2].contains("{")) {
					if (RequestMethod.DELETE.toString().equals(httpMethod)) {
						auditAction = AuditAction.DELETE;
					} else if (RequestMethod.PUT.toString().equals(httpMethod)) {
						auditAction = AuditAction.UPDATE;
					} else if (RequestMethod.GET.toString().equals(httpMethod)) {
						auditAction = AuditAction.READ;
					}
				} else if ("search".equals(splitPattern[2])) {
					auditAction = AuditAction.SEARCH;

				}
			} else if (splitPattern.length == 2 && RequestMethod.POST.toString().equals(httpMethod)) {
				auditAction = AuditAction.CREATE;
			}
		}

		/** logging defined Audit Action in controller with @AuditLog */
		AuditLog annoAuditLog = method.getDeclaredAnnotation(AuditLog.class);
		if (annoAuditLog != null) {
			if (annoAuditLog.auditAction() != null) {
				auditAction = annoAuditLog.auditAction();
			}
		}

		return auditAction;
	}

	private List<String> buildLstPathVariable(String url) {
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
		return lstPathVariable;
	}
}
