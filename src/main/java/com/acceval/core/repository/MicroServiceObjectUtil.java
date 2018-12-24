package com.acceval.core.repository;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import com.acceval.core.MicroServiceUtilException;
import com.acceval.core.controller.GenericCommonController;
import com.acceval.core.microservice.MicroServiceRequest;
import com.acceval.core.microservice.MicroServiceUtil;
import com.acceval.core.util.BaseBeanUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MicroServiceObjectUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServiceObjectUtil.class);

	/**
	 * initialise all fields with MicroServiceField annotation
	 */
	public static void refreshObjectDependency(Object target) throws MicroServiceUtilException, Exception {
		refreshObjectDependency(target, true);
	}

	/**
	 * refreshing Object
	 * 
	 * @param isForceRefresh force refresh? Or do nothing if PK are same
	 * @throws Exception
	 */
	public static void refreshObjectDependency(Object target, boolean isForceRefresh) throws MicroServiceUtilException, Exception {

		if (target == null) return;

		OAuth2RestTemplate restTemplate = BaseBeanUtil.getBean(OAuth2RestTemplate.class);
		DiscoveryClient discoveryClient = (DiscoveryClient) BaseBeanUtil.getBean(DiscoveryClient.class);

		if (target instanceof Collection) {
			for (Object obj : (Collection<?>) target) {
				refreshObjectDependency(obj, isForceRefresh);
			}
		}

		Class<?> classToFind = target.getClass();
		if (!classToFind.getName().equals(Object.class.getName())) {

			ExecutorService executor = Executors.newFixedThreadPool(10);
			Long longStart = System.currentTimeMillis();
			List<Field> allField = new ArrayList<>();
			appendClassField(classToFind, allField);
			for (Field field : allField) {
				if (field.isAnnotationPresent(MicroServiceField.class)) {
					if (Collection.class.isAssignableFrom(field.getType())) {
						PropertyDescriptor getter = new PropertyDescriptor(field.getName(), field.getDeclaringClass());
						Object childObj = getter.getReadMethod().invoke(target);
						refreshObjectDependency(childObj, isForceRefresh);
					} else {
						// multi-thread
						executor.submit(() -> {
							try {
								refreshField(discoveryClient, restTemplate, target, field.getName(), isForceRefresh);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
					}
				} else if ((Collection.class.isAssignableFrom(field.getType())
						&& field.getGenericType().getTypeName().indexOf("com.acceval.sales.model.") > -1
						&& field.getGenericType().getTypeName().indexOf("com.acceval.sales.model.so.") == -1)
						|| (field.getType().getTypeName().indexOf("com.acceval.sales.model.") > -1
								&& field.getType().getTypeName().indexOf("com.acceval.sales.model.so.") == -1)) {
					PropertyDescriptor getter = new PropertyDescriptor(field.getName(), field.getDeclaringClass());
					Object childObj = getter.getReadMethod().invoke(target);
					if (childObj != null && !childObj.getClass().isEnum()) {
						refreshObjectDependency(childObj, isForceRefresh);
					}
				}
			}

			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.MINUTES);
			if (LOGGER.isDebugEnabled()) {
				if ((System.currentTimeMillis() - longStart) > 500) {
					LOGGER.debug("Object Initialised in (more than 500 ms): " + (System.currentTimeMillis() - longStart) + " ms");
				}
			}
		}
	}

	/**
	 * discover Field from super class
	 */
	private static void appendClassField(Class<?> classToFind, List<Field> allField) {
		if (!Object.class.getName().equals(classToFind.getSuperclass().getName())) {
			appendClassField(classToFind.getSuperclass(), allField);
		}
		allField.addAll(Arrays.asList(classToFind.getDeclaredFields()));
	}

	/**
	 * initialise by field level
	 * 
	 * @param isForceRefresh force refresh? Or do nothing if PK are same
	 * @return object refreshed
	 */
	public static boolean refreshField(Object target, String fieldName, boolean isForceRefresh)
			throws MicroServiceUtilException, Exception {
		OAuth2RestTemplate restTemplate = BaseBeanUtil.getBean(OAuth2RestTemplate.class);
		DiscoveryClient discoveryClient = (DiscoveryClient) BaseBeanUtil.getBean(DiscoveryClient.class);

		return refreshField(discoveryClient, restTemplate, target, fieldName, isForceRefresh);
	}

	/**
	 * initialise by field level
	 * 
	 * @param isForceRefresh force refresh? Or do nothing if PK are same
	 * @return object refreshed
	 */
	public static boolean refreshField(DiscoveryClient discoveryClient, RestTemplate restTemplate, Object target, String fieldName,
			boolean isForceRefresh) throws MicroServiceUtilException, Exception {
		Class<?> classToFind = target.getClass();

		Field field = ReflectionUtils.findField(classToFind, fieldName);
		if (field == null) {
			throw new MicroServiceUtilException(MicroServiceObjectUtil.class,
					"Field [" + fieldName + "] not found for [" + classToFind.getName() + "]");
		}
		if (field.isAnnotationPresent(MicroServiceField.class)) {

			Annotation objFieldAnno = field.getAnnotation(MicroServiceField.class);
			MicroServiceField fieldAnno = (MicroServiceField) objFieldAnno;

			String msService = fieldAnno.serviceID();
			String msFunction = fieldAnno.function();
			String mockTarget = fieldAnno.mockTarget();
			if (StringUtils.isBlank(mockTarget)) {
				throw new MicroServiceUtilException(MicroServiceObjectUtil.class,
						"Mock Target is not define for [" + field.getName() + "] field! Class [" + classToFind.getName() + "]");
			}
			PropertyDescriptor pdMockTarget = new PropertyDescriptor(mockTarget, classToFind);
			if (!pdMockTarget.getPropertyType().isAnnotationPresent(MicroServiceObject.class)) {
				throw new MicroServiceUtilException(MicroServiceObjectUtil.class,
						"MicroServiceObject is not define for [" + field.getName() + "] field! Class [" + classToFind.getName() + "]");
			}
			Annotation objAnno = pdMockTarget.getPropertyType().getAnnotation(MicroServiceObject.class);
			MicroServiceObject msObject = (MicroServiceObject) objAnno;
			// if MS info not from MicroServiceField level, get from MicroServiceObject level
			if (StringUtils.isBlank(msFunction)) {
				msFunction = msObject.module() + "/" + MicroServiceObject.COMMON_GT_OBJ;
				if (msObject.useCommonQuery()) {
					msFunction = "common/query";
				}
			}
			if (StringUtils.isBlank(msService)) {
				msService = getServiceID(msObject);
			}

			/** validation */
			if (StringUtils.isBlank(msService)) {
				throw new MicroServiceUtilException(MicroServiceObjectUtil.class,
						"MS Service ID not define in POJO for [" + field.getName() + "] field! Class [" + classToFind.getName() + "]");
			}
			if (StringUtils.isBlank(msFunction)) {
				throw new MicroServiceUtilException(MicroServiceObjectUtil.class,
						"MS Function is not define in POJO for [" + field.getName() + "] field! Class [" + classToFind.getName() + "]");
			}

			/** get Primary Key */
			String id = null;
			PropertyDescriptor pdFieldPK = new PropertyDescriptor(field.getName(), classToFind);
			Object objID = pdFieldPK.getReadMethod().invoke(target);
			if (objID instanceof Collection) {
				id = objID == null ? null : StringUtils.join((Collection<?>) objID, ",");
			} else {
				id = objID == null ? null : String.valueOf(objID);
			}
			if (StringUtils.isBlank(id) || "0".equals(id)) {
				// no ID, exit
				Method mockWriteTarget = pdMockTarget.getWriteMethod();
				Object mockObj = pdMockTarget.getReadMethod().invoke(target);
				Object nullObj = null;
				mockWriteTarget.invoke(target, nullObj);
				if (mockObj != null) {
					return true; // updated to null
				} else {
					return false;
				}
			}

			/** if not Force Refresh, check between Primary Key to decide Refresh or not (Performance) */
			if (!isForceRefresh) {
				Object mockObj = pdMockTarget.getReadMethod().invoke(target);
				if (mockObj != null) {
					if (mockObj instanceof Collection) {
						// handler collection
						List<Object> lst = new ArrayList<>();
						for (Object objMock : (Collection<?>) mockObj) {
							if (!objMock.getClass().isAnnotationPresent(MicroServiceObject.class)) continue;
							Annotation objPrixObjAnno = objMock.getClass().getAnnotation(MicroServiceObject.class);
							MicroServiceObject prixObjAnno = (MicroServiceObject) objPrixObjAnno;
							PropertyDescriptor pdChildPK = new PropertyDescriptor(prixObjAnno.primaryKey(), objMock.getClass());
							Method getterChildPK = pdChildPK.getReadMethod();
							Object childObjPK = getterChildPK.invoke(objMock);
							lst.add(childObjPK);
						}
						if (StringUtils.equals(id, StringUtils.join(lst, ","))) {
							return false; // do nothing if PK are same
						}
					} else {
						Annotation objPrixObjAnno = mockObj.getClass().getAnnotation(MicroServiceObject.class);
						MicroServiceObject prixObjAnno = (MicroServiceObject) objPrixObjAnno;
						PropertyDescriptor pdChildPK = new PropertyDescriptor(prixObjAnno.primaryKey(), mockObj.getClass());
						Method getterChildPK = pdChildPK.getReadMethod();
						Object childObjPK = getterChildPK.invoke(mockObj);
						if (StringUtils.equals(id, String.valueOf(childObjPK))) {
							return false; // do nothing if PK are same
						}
					}
				}
			}

			/** MS-ing, Mocking */
			Object childObj = null;
			if (Collection.class.isAssignableFrom(pdMockTarget.getPropertyType())) {
				// special handling for Collection
				String resJson;
				if (msObject.useCommonQuery()) {
					MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
					mvm.add(msObject.primaryKey(), id);
					mvm.add(GenericCommonController.KEY_ENTITY_CLASS, msObject.originEntityClass());
					mvm.add(GenericCommonController.KEY_IS_COLLECTION, "true");
					resJson = (String) MicroServiceUtil.getForObject(
							new MicroServiceRequest(discoveryClient, restTemplate, msService, msFunction, ""), mvm, String.class);
				} else {
					resJson = (String) MicroServiceUtil
							.getForObject(new MicroServiceRequest(discoveryClient, restTemplate, msService, msFunction, id), String.class);
				}
				ObjectMapper maple = new ObjectMapper();
				Field mockfield = ReflectionUtils.findField(classToFind, mockTarget);
				childObj = maple.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(resJson,
						maple.constructType(mockfield.getGenericType()));
			} else {
				if (msObject.useCommonQuery()) {
					MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
					mvm.add(msObject.primaryKey(), id);
					mvm.add(GenericCommonController.KEY_ENTITY_CLASS, msObject.originEntityClass());
					childObj =
							MicroServiceUtil.getForObject(new MicroServiceRequest(discoveryClient, restTemplate, msService, msFunction, ""),
									mvm, pdMockTarget.getPropertyType());
				} else {
					childObj =
							MicroServiceUtil.getForObject(new MicroServiceRequest(discoveryClient, restTemplate, msService, msFunction, id),
									pdMockTarget.getPropertyType());
				}
			}

			/** fields recursive */
			if (childObj != null) {
				refreshObjectDependency(childObj, isForceRefresh);
			}

			/** reflection to set Mocked object */
			pdMockTarget.getWriteMethod().invoke(target, childObj);
			return true;
		}

		return false;
	}

	// TODO init Collection, store re-use mapping

	private static String getServiceID(MicroServiceObject microServiceObject) {
		String entityClass = microServiceObject.originEntityClass();
		if (StringUtils.isNotBlank(entityClass)) {
			String[] split = entityClass.split("[.]");
			if (split.length >= 3) {
				switch (split[2]) {
					case "masterdata":
						return MicroServiceObject.SRC_MASTERDATA;
					case "identitymanagement":
						return MicroServiceObject.SRC_IDENTITY;
					case "industryanalysis":
						return MicroServiceObject.SRC_INDUSTRY_ANALYSIS;
					case "pricing":
						return MicroServiceObject.SRC_PRICING;
					case "etl":
						return MicroServiceObject.SRC_ETL;
					case "pricingpower":
						return MicroServiceObject.SRC_PRICING_POWER;
				}
			}
		}

		return "";
	}
}
