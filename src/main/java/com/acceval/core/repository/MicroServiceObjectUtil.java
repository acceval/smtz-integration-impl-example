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
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MicroServiceObjectUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServiceObjectUtil.class);

	/**
	 * initialise all fields with MicroServiceField annotation
	 */
	public static void refreshObjectDependency(DiscoveryClient discoveryClient, RestTemplate restTemplate, Object target) throws Exception {
		refreshObjectDependency(discoveryClient, restTemplate, target, true);
	}

	/**
	 * refreshing Object
	 * 
	 * @param isForceRefresh force refresh? Or do nothing if PK are same
	 * @throws Exception
	 */
	public static void refreshObjectDependency(DiscoveryClient discoveryClient, RestTemplate restTemplate, Object target, boolean isForceRefresh)
			throws Exception {

		if (target == null) return;

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
			boolean isForceRefresh) throws Exception {
		Class<?> classToFind = target.getClass();

		Field field = ReflectionUtils.findField(classToFind, fieldName);
		if (field.isAnnotationPresent(MicroServiceField.class)) {

			Annotation objFieldAnno = field.getAnnotation(MicroServiceField.class);
			MicroServiceField fieldAnno = (MicroServiceField) objFieldAnno;

			/** validation */
			String msService = fieldAnno.serviceID();
			if (StringUtils.isBlank(msService)) {
				throw new Exception("MS Application Name not define in POJO for [" + field.getName() + "] field!");
			}
			String msFunction = fieldAnno.function();
			if (StringUtils.isBlank(msFunction)) {
				throw new Exception("MS Service is not define in POJO for [" + field.getName() + "] field!");
			}
			String objIDField = fieldAnno.primaryKey();
			if (StringUtils.isBlank(objIDField)) {
				throw new Exception("MS Primary Key is not define for [" + field.getName() + "] field!");
			}

			/** get Primary Key */
			String id = null;
			PropertyDescriptor pdFieldPK = new PropertyDescriptor(objIDField, classToFind);
			Method getterTarget = pdFieldPK.getReadMethod();
			Object objID = getterTarget.invoke(target);
			if (objID instanceof Collection) {
				id = StringUtils.join((Collection<?>) objID, ",");
			} else {
				id = String.valueOf(getterTarget.invoke(target));
			}
			if (StringUtils.isBlank(id)) {
				throw new Exception("Unable retrieve ID from " + classToFind.getName());
			}

			PropertyDescriptor pdFieldObj = new PropertyDescriptor(field.getName(), classToFind);

			/** if not Force Refresh, check between Primary Key to decide Refresh or not (Performance) */
			if (!isForceRefresh) {
				Method getterChild = pdFieldObj.getReadMethod();
				Object childObj = getterChild.invoke(target);
				if (childObj != null) {
					if (childObj.getClass().isAnnotationPresent(MicroServiceObject.class)) {
						Annotation objPrixObjAnno = childObj.getClass().getAnnotation(MicroServiceObject.class);
						MicroServiceObject prixObjAnno = (MicroServiceObject) objPrixObjAnno;
						PropertyDescriptor pdChildPK = new PropertyDescriptor(prixObjAnno.primaryKey(), childObj.getClass());
						Method getterChildPK = pdChildPK.getReadMethod();
						Object childObjPK = getterChildPK.invoke(childObj);
						if (StringUtils.equals(id, String.valueOf(childObjPK))) {
							return; // do nothing if PK are same
						}
					} else if (childObj instanceof Collection) {
						// handler collection
						List<Object> lst = new ArrayList<>();
						for (Object objMock : (Collection<?>) childObj) {
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
					}
				}
			}

			/** MS-ing, Mocking */
			List<ServiceInstance> instances = discoveryClient.getInstances(msService);
			if (instances.isEmpty()) throw new Exception(msService + " Service is Not Available!");
			ServiceInstance instance = instances.get(0);
			String host = instance.getHost();
			String url = "http://" + host + ":" + instance.getPort() + "/" + msFunction + "/" + id;
			Object childObj = null;
			if (Collection.class.isAssignableFrom(field.getType())) {
				// special handling for Collection
				String resJson = restTemplate.getForObject(url, String.class);
				ObjectMapper maple = new ObjectMapper();
				childObj = maple.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(resJson,
						maple.constructType(field.getGenericType()));
			} else {
				childObj = restTemplate.getForObject(url, field.getType());
			}

			/** reflection to set Mocked object */
			Method getterWriteTarget = pdFieldObj.getWriteMethod();
			getterWriteTarget.invoke(target, childObj);
		}
	}
}
