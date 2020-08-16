package com.acceval.core.controller;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class ControllerUtil {

	/**
	 * Auto discover all services from provided MicroService name
	 */
	public static List<FunctionObject> discoveryServices(RequestMappingHandlerMapping requestMappingHandlerMapping, String microService) {
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		List<FunctionObject> lstAC = new ArrayList<>();

		// ALL access, by MicroService
		String[] msSplit = microService.split("-");
		String msDesc = StringUtils.join(
				Stream.of(msSplit).map(str -> str.substring(0, 1).toUpperCase() + str.substring(1)).collect(Collectors.toList()), " ");
		lstAC.add(new FunctionObject(microService, "ALL", "ALL", "ALL", msDesc, "ALL"));

		Set<String> setModule = new HashSet<>();
		for (RequestMappingInfo mapingInfo : map.keySet()) {
			HandlerMethod method = map.get(mapingInfo);
			String urlPattern = mapingInfo.getPatternsCondition().getPatterns().stream().findFirst().orElse("");
			Optional<RequestMethod> firstMethodCod = mapingInfo.getMethodsCondition().getMethods().stream().findFirst();
			String httpMethod = firstMethodCod.isPresent() ? firstMethodCod.get().toString() : "";

			if (StringUtils.isNotBlank(urlPattern)) {
				String[] splitPattern = urlPattern.split("/");
				if (splitPattern.length <= 1) continue;

				String module = splitPattern[1].trim();
				setModule.add(module);

				// trim {}
				StringBuilder newUrlSB = new StringBuilder();
				for (int i = 0; i < splitPattern.length; i++) {
					String str = splitPattern[i];
					if (str.indexOf("{") == 0) {
						newUrlSB.append("{}");
					} else {
						newUrlSB.append(str);
					}
					if (i != splitPattern.length - 1) {
						newUrlSB.append("/");
					}
				}

				if (method.getMethod().isAnnotationPresent(Function.class)) {
					// customise Access Control
					Annotation objACAnno = method.getMethod().getAnnotation(Function.class);
					Function acAnno = (Function) objACAnno;

					lstAC.add(new FunctionObject(microService, module, acAnno.clientAccess(), newUrlSB.toString(), acAnno.description(),
							httpMethod));
				} else {
					// standard CRUD
					if (splitPattern.length == 3 && splitPattern[2].contains("{")) {
						if (RequestMethod.DELETE.toString().equals(httpMethod)) {
							lstAC.add(new FunctionObject(microService, module, "delete", newUrlSB.toString(),
									FunctionObject.CRUD_DELETE_DESC, httpMethod));
						} else if (RequestMethod.PUT.toString().equals(httpMethod)) {
							lstAC.add(new FunctionObject(microService, module, "update", newUrlSB.toString(),
									FunctionObject.CRUD_UPDATE_DESC, httpMethod));
						} else if (RequestMethod.GET.toString().equals(httpMethod)) {
							lstAC.add(new FunctionObject(microService, module, "view", newUrlSB.toString(),
									FunctionObject.CRUD_READ_DESC, httpMethod));
							
						}
					} else if (RequestMethod.GET.toString().equals(httpMethod) && splitPattern.length == 3
							&& "search".equals(splitPattern[2])) {
						lstAC.add(new FunctionObject(microService, module, "view", newUrlSB.toString(),
								FunctionObject.CRUD_READ_DESC,
								httpMethod));
					} else if (splitPattern.length == 2 && RequestMethod.POST.toString().equals(httpMethod)) {
						lstAC.add(new FunctionObject(microService, module, "create", newUrlSB.toString(),
								FunctionObject.CRUD_CREATE_DESC, httpMethod));
					} else {
						// mark as system, use for bypass function access
						lstAC.add(new FunctionObject(microService, module, FunctionObject.SYSTEM, newUrlSB.toString(),
								FunctionObject.SYSTEM, httpMethod));
					}
				}
			}
		}

		// ALL access, by MicroService
		for (String module : setModule) {
			String moduleDesc = module;
			if (module.indexOf("-") > -1) {
				String[] splitModule = module.split("-");
				moduleDesc = StringUtils.join(Stream.of(splitModule).map(str -> str.substring(0, 1).toUpperCase() + str.substring(1))
						.collect(Collectors.toList()), " ");
			} else {
				List<Character> lstChar = new ArrayList<>();
				int index = 0;
				for (char ch : module.toCharArray()) {
					if (index == 0) {
						lstChar.add(Character.toUpperCase(ch));
					} else if (Character.isUpperCase(ch)) {
						lstChar.add(' ');
						lstChar.add(ch);
					} else {
						lstChar.add(ch);
					}
					index++;
				}
				moduleDesc = StringUtils.join(lstChar, "");
			}
			lstAC.add(new FunctionObject(microService, module, "ALL", "ALL", moduleDesc, "ALL"));
		}

		return lstAC;
	}
}
