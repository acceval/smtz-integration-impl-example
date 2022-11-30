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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import com.acceval.core.MicroServiceUtilException;
import com.acceval.core.controller.GenericCommonController;
import com.acceval.core.microservice.MicroServiceRequest;
import com.acceval.core.microservice.MicroServiceUtil;
import com.acceval.core.security.PrincipalUtil;
import com.acceval.core.util.BaseBeanUtil;
import com.acceval.core.util.ClassUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MicroServiceObjectUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServiceObjectUtil.class);

	@Autowired
	private MicroServiceUtil microServiceUtil;

	public void refreshObjectDependency(Object target) throws MicroServiceUtilException, Exception {
		OAuth2RestTemplate restTemplate = BaseBeanUtil.getBean(OAuth2RestTemplate.class);
		DiscoveryClient discoveryClient = (DiscoveryClient) BaseBeanUtil.getBean(DiscoveryClient.class);
		refreshObjectDependency(target, true, restTemplate, discoveryClient);
	}

	public void refreshObjectDependency(Object target, boolean isForceRefresh) throws MicroServiceUtilException, Exception {
		OAuth2RestTemplate restTemplate = BaseBeanUtil.getBean(OAuth2RestTemplate.class);
		DiscoveryClient discoveryClient = (DiscoveryClient) BaseBeanUtil.getBean(DiscoveryClient.class);
		refreshObjectDependency(target, isForceRefresh, restTemplate, discoveryClient);
	}

	/**
	 * refreshing Object
	 * 
	 * @param isForceRefresh force refresh? Or do nothing if PK are same
	 * @throws Exception
	 */
	private void refreshObjectDependency(Object target, boolean isForceRefresh, RestTemplate restTemplate, DiscoveryClient discoveryClient)
			throws MicroServiceUtilException, Exception {

		if (target == null) return;

		if (target instanceof Collection) {
			for (Object obj : (Collection<?>) target) {
				refreshObjectDependency(obj, isForceRefresh, restTemplate, discoveryClient);
			}
		}

		Class<?> classToFind = target.getClass();
		if (!classToFind.getName().equals(Object.class.getName())) {

			ExecutorService executor = Executors.newFixedThreadPool(20);
			Long longStart = System.currentTimeMillis();
			List<Field> allField = new ArrayList<>();
			appendClassField(classToFind, allField);
			for (Field field : allField) {
				if (field.isAnnotationPresent(MicroServiceField.class)) {
					Annotation objFieldAnno = field.getAnnotation(MicroServiceField.class);
					MicroServiceField fieldAnno = (MicroServiceField) objFieldAnno;
					if (Collection.class.isAssignableFrom(field.getType())
							|| StringUtils.isBlank(fieldAnno.mockTarget())) {
						PropertyDescriptor getter = new PropertyDescriptor(field.getName(), field.getDeclaringClass());
						Object childObj = getter.getReadMethod().invoke(target);
						refreshObjectDependency(childObj, isForceRefresh, restTemplate, discoveryClient);
					} else {
						// multi-thread
						String token = PrincipalUtil.getToken();
						String companyID = PrincipalUtil.getCompanyID() != null ? PrincipalUtil.getCompanyID().toString() : null;
//						if(PrincipalUtil.getCompanyID() != null) {
//							companyID = PrincipalUtil.getCompanyID().toString();
//						} else {
//							companyID = target.get
//						}
						if(companyID != null) {
							executor.submit(() -> {
								try {
									refreshField(discoveryClient, restTemplate, token, companyID, target, field.getName(), isForceRefresh);
								} catch (Exception e) {
									LOGGER.error(e.getMessage(), e);
								}
							});
						}
					}
				} else if (Collection.class.isAssignableFrom(field.getType())) {
					String genericTypeName = field.getGenericType().getTypeName();
					String typeName = field.getType().getTypeName();
					if ((genericTypeName.indexOf("com.acceval.sales.model.") > -1
							&& genericTypeName.indexOf("com.acceval.sales.model.so.") == -1)
							|| (typeName.indexOf("com.acceval.sales.model.") > -1
									&& typeName.indexOf("com.acceval.sales.model.so.") == -1)
							|| (genericTypeName.indexOf("com.acceval.shopping.model.") > -1
									&& genericTypeName.indexOf("com.acceval.shopping.model.so.") == -1)
							|| (typeName.indexOf("com.acceval.shopping.model.") > -1
									&& typeName.indexOf("com.acceval.shopping.model.so.") == -1)) {
						try {
							PropertyDescriptor getter = new PropertyDescriptor(field.getName(),
									field.getDeclaringClass());
							Object childObj = getter.getReadMethod().invoke(target);
							if (childObj != null && !childObj.getClass().isEnum()) {
								refreshObjectDependency(childObj, isForceRefresh, restTemplate, discoveryClient);
							}
						} catch (Throwable ex) {
							ex.printStackTrace();
						}
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
	private void appendClassField(Class<?> classToFind, List<Field> allField) {
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
	public boolean refreshField(Object target, String fieldName, boolean isForceRefresh) throws MicroServiceUtilException, Exception {
		OAuth2RestTemplate restTemplate = BaseBeanUtil.getBean(OAuth2RestTemplate.class);
		DiscoveryClient discoveryClient = (DiscoveryClient) BaseBeanUtil.getBean(DiscoveryClient.class);
		String token = PrincipalUtil.getToken();
		String companyID = PrincipalUtil.getCompanyID().toString();

		return refreshField(discoveryClient, restTemplate, token, companyID, target, fieldName, isForceRefresh);
	}

	/**
	 * initialise by field level
	 * 
	 * @param isForceRefresh force refresh? Or do nothing if PK are same
	 * @return object refreshed
	 */
	private boolean refreshField(DiscoveryClient discoveryClient, RestTemplate restTemplate, String token, String companyID, Object target,
			String fieldName, boolean isForceRefresh) throws MicroServiceUtilException, Exception {
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
			// if MS info not from MicroServiceField level, get from MicroServiceObject
			// level
			if (StringUtils.isBlank(msFunction)) {
				msFunction = msObject.module() + "/" + MicroServiceObject.COMMON_GT_OBJ;
				if (msObject.useCommonQuery()) {
					msFunction = MicroServiceObject.COMMON_QUERY;
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

			/**
			 * if not Force Refresh, check between Primary Key to decide Refresh or not
			 * (Performance)
			 */
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
					resJson = (String) microServiceUtil.getForObject(new MicroServiceRequest(token, companyID, msService, msFunction, ""),
							mvm, String.class);
				} else {
					resJson = (String) microServiceUtil.getForObject(new MicroServiceRequest(token, companyID, msService, msFunction, id),
							String.class);
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
					childObj = microServiceUtil.getForObject(new MicroServiceRequest(token, companyID, msService, msFunction, ""), mvm,
							pdMockTarget.getPropertyType());
				} else {
					childObj = microServiceUtil.getForObject(new MicroServiceRequest(token, companyID, msService, msFunction, id),
							pdMockTarget.getPropertyType());
				}
			}

			/** fields recursive */
			if (childObj != null) {
				refreshObjectDependency(childObj, isForceRefresh, restTemplate, discoveryClient);
			}

			/** reflection to set Mocked object */
			pdMockTarget.getWriteMethod().invoke(target, childObj);
			return true;
		}

		return false;
	}

	public void mappingFields(Object targetObj, List<MappingRequest> lstMappingRequest, RestTemplate restTemplate) throws Exception {
		DiscoveryClient discoveryClient = (DiscoveryClient) BaseBeanUtil.getBean(DiscoveryClient.class);

		ExecutorService executor = Executors.newFixedThreadPool(20);

		// multi-thread
		for (MappingRequest mapping : lstMappingRequest) {
			String token = PrincipalUtil.getToken();
			String companyID = PrincipalUtil.getCompanyID().toString();
			executor.submit(() -> {
				Class<?> mappingClass = mapping.getMappingClass();
				if (mappingClass.isAnnotationPresent(MicroServiceObject.class)) {

					MultiValueMap<String, String> mapParam = mapping.getMapParams();
					String strMappingField = mapping.getMappingField();
					String strDestinationField = mapping.getDestinationField();

					Annotation objAnno = mappingClass.getAnnotation(MicroServiceObject.class);
					MicroServiceObject msObject = (MicroServiceObject) objAnno;
					mapParam.add(GenericCommonController.KEY_ENTITY_CLASS, msObject.originEntityClass());
					if (mapping.isCollection()) {
						mapParam.add(GenericCommonController.KEY_IS_COLLECTION, "true");
					}

					try {
						Object mappedObj = microServiceUtil.getForObject(
								new MicroServiceRequest(token, companyID, getServiceID(msObject), MicroServiceObject.COMMON_QUERY, ""),
								mapParam, mappingClass);
						if (mappedObj != null) {
							Object targetField = ClassUtil.getProperty(mappedObj, strMappingField);
							if (targetField != null) {
								ClassUtil.setProperty(targetObj, strDestinationField, targetField);
							}
						}
					} catch (MicroServiceUtilException e) {
						LOGGER.error(e.getMessage(), e);
					}

				} else {
					LOGGER.error("Target class [" + mappingClass.getName() + "] is not MicroServiceObject");
				}
			});
		}
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.MINUTES);
	}

	public void poMappingFields(Object targetObject, Object sourceTargetObject, List<POMappingRequest> mappingRequestLst) {
		for (POMappingRequest rq : mappingRequestLst) {
			try {
				Object value = ClassUtil.getProperty(sourceTargetObject, rq.getSourceObject());
				ClassUtil.setProperty(targetObject, rq.getDestinationObject(), value);
			} catch (MicroServiceUtilException e) {
				e.printStackTrace();
			}
		}
	}

	// TODO init Collection, store re-use mapping

	private String getServiceID(MicroServiceObject microServiceObject) {
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
					case "ecommerce":
						return MicroServiceObject.SRC_BUY_ECOMMERCE;
				}
			}
		}

		return "";
	}
}
