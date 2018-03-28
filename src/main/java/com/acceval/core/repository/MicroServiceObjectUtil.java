package com.acceval.core.repository;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import com.acceval.core.MicroServiceUtilException;
import com.acceval.core.microservice.MicroServiceRequest;
import com.acceval.core.microservice.MicroServiceUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MicroServiceObjectUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServiceObjectUtil.class);

	/**
	 * initialise all fields with MicroServiceField annotation
	 */
	public static void refreshObjectDependency(DiscoveryClient discoveryClient, RestTemplate restTemplate, Object target)
			throws MicroServiceUtilException, Exception {
		refreshObjectDependency(discoveryClient, restTemplate, target, true);
	}

	/**
	 * refreshing Object
	 * 
	 * @param isForceRefresh force refresh? Or do nothing if PK are same
	 * @throws Exception
	 */
	public static void refreshObjectDependency(DiscoveryClient discoveryClient, RestTemplate restTemplate, Object target,
			boolean isForceRefresh) throws MicroServiceUtilException, Exception {

		if (target == null) return;

		if (target instanceof Collection) {
			for (Object obj : (Collection<?>) target) {
				refreshObjectDependency(discoveryClient, restTemplate, obj, isForceRefresh);
			}
		}

		Class<?> classToFind = target.getClass();
		if (!classToFind.getName().equals(Object.class.getName())) {

			ExecutorService executor = Executors.newFixedThreadPool(10);
			Long longStart = System.currentTimeMillis();
			for (Field field : classToFind.getDeclaredFields()) {
				if (field.isAnnotationPresent(MicroServiceField.class)) {
					// multi-thread
					executor.submit(() -> {
						try {
							refreshField(discoveryClient, restTemplate, target, field.getName(), isForceRefresh);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				}

			}
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.MINUTES);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Object Initialised in: " + (System.currentTimeMillis() - longStart) + " ms");
			}
		}
	}

	/**
	 * initialise by field level
	 * 
	 * @param isForceRefresh force refresh? Or do nothing if PK are same
	 */
	public static void refreshField(DiscoveryClient discoveryClient, RestTemplate restTemplate, Object target, String fieldName,
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

			/** validation */
			String msService = fieldAnno.serviceID();
			if (StringUtils.isBlank(msService)) {
				throw new MicroServiceUtilException(MicroServiceObjectUtil.class,
						"MS Application Name not define in POJO for [" + field.getName() + "] field!");
			}
			String msFunction = fieldAnno.function();
			if (StringUtils.isBlank(msFunction)) {
				throw new MicroServiceUtilException(MicroServiceObjectUtil.class,
						"MS Service is not define in POJO for [" + field.getName() + "] field!");
			}
			String mockTarget = fieldAnno.mockTarget();
			if (StringUtils.isBlank(mockTarget)) {
				throw new MicroServiceUtilException(MicroServiceObjectUtil.class,
						"Mock Target is not define for [" + field.getName() + "] field!");
			}

			/** get Primary Key */
			String id = null;
			PropertyDescriptor pdMockTarget = new PropertyDescriptor(mockTarget, classToFind);
			PropertyDescriptor pdFieldPK = new PropertyDescriptor(field.getName(), classToFind);
			Object objID = pdFieldPK.getReadMethod().invoke(target);
			if (objID instanceof Collection) {
				id = StringUtils.join((Collection<?>) objID, ",");
			} else {
				id = objID == null ? null : String.valueOf(objID);
			}
			if (StringUtils.isBlank(id)) {
				// no ID, exit
				Method mockWriteTarget = pdMockTarget.getWriteMethod();
				Object nullObj = null;
				mockWriteTarget.invoke(target, nullObj);
				return;
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
							return; // do nothing if PK are same
						}
					} else {
						Annotation objPrixObjAnno = mockObj.getClass().getAnnotation(MicroServiceObject.class);
						MicroServiceObject prixObjAnno = (MicroServiceObject) objPrixObjAnno;
						PropertyDescriptor pdChildPK = new PropertyDescriptor(prixObjAnno.primaryKey(), mockObj.getClass());
						Method getterChildPK = pdChildPK.getReadMethod();
						Object childObjPK = getterChildPK.invoke(mockObj);
						if (StringUtils.equals(id, String.valueOf(childObjPK))) {
							return; // do nothing if PK are same
						}
					}
				}
			}

			/** MS-ing, Mocking */
			Object childObj = null;
			if (Collection.class.isAssignableFrom(pdMockTarget.getPropertyType())) {
				// special handling for Collection
				String resJson = (String) MicroServiceUtil
						.getForObject(new MicroServiceRequest(discoveryClient, restTemplate, msService, msFunction, id), String.class);
				ObjectMapper maple = new ObjectMapper();
				Field mockfield = ReflectionUtils.findField(classToFind, mockTarget);
				childObj = maple.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(resJson,
						maple.constructType(mockfield.getGenericType()));
			} else {
				childObj = MicroServiceUtil.getForObject(new MicroServiceRequest(discoveryClient, restTemplate, msService, msFunction, id),
						pdMockTarget.getPropertyType());
			}

			/** reflection to set Mocked object */
			pdMockTarget.getWriteMethod().invoke(target, childObj);
		}
	}

	// TODO init Collection, store re-use mapping
}
