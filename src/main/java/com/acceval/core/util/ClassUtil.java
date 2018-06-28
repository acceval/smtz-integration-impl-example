package com.acceval.core.util;

import java.lang.reflect.InvocationTargetException;
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
}
