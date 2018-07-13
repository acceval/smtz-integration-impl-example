package com.acceval.core.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClassUtil {
	private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

	public static void populateJsonMapToObj(ObjectMapper objectMapper, Object target, Map<String, Object> mapValues) {
		if (target == null || mapValues == null) return;

		for (String key : mapValues.keySet()) {
			Object value = mapValues.get(key);
			try {
				Class<?> propertyClass = PropertyUtils.getPropertyDescriptor(target, key).getPropertyType();
				Object convertedValue = objectMapper.convertValue(value, propertyClass);
				PropertyUtils.setProperty(target, key, convertedValue);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
				logger.error(e1.getMessage(), e1);
			}
		}

	}

	public static void copyProperties(Object dest, Object orig) {
		try {
			PropertyUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * copy properties with filter
	 */
	public static void copyPropertiesWithFilter(Object dest, Object orig, String[] filter) {
		if (filter == null) {
			filter = new String[0];
		}
		List<String> ignoreFiels = Arrays.asList(filter);
		Map<String, Object> mapProp = null;
		try {
			mapProp = PropertyUtils.describe(orig);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		for (String propKey : mapProp.keySet()) {
			if ("class".equals(propKey) || ignoreFiels.contains(propKey)) {
				continue;
			}
			try {
				Object value = PropertyUtils.getProperty(orig, propKey);
				PropertyUtils.setProperty(dest, propKey, value);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static String getPrimaryKeyName(Object object) {
		Field fields[] = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			Annotation annotations[] = field.getDeclaredAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof javax.persistence.Id) return field.getName();
			}
			//only getter methods can have persistence annotations, setters cannot
			Method getter = null;
			try {
				getter = new PropertyDescriptor(field.getName(), object.getClass()).getReadMethod();
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (getter != null) {
				Annotation getterAnnotations[] = getter.getDeclaredAnnotations();
				for (Annotation annotation : getterAnnotations) {
					if (annotation instanceof javax.persistence.Id) return field.getName();
				}
			}
		}
		return null; //no primary key found
	}
}
