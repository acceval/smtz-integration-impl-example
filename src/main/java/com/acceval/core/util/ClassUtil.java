package com.acceval.core.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.proxy.HibernateProxy;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import com.acceval.core.MicroServiceUtilException;
import com.acceval.core.model.BaseModel;
import com.acceval.core.repository.QueryResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Defaults;

public class ClassUtil {
	private static Logger Logger = LoggerFactory.getLogger(ClassUtil.class);

	public static void populateJsonMapToObj(ObjectMapper objectMapper, Object target, Map<String, Object> mapValues) {
		populateJsonMapToObj(objectMapper, target, mapValues, null);
	}

	public static void populateJsonMapToObj(ObjectMapper objectMapper, Object target, Map<String, Object> mapValues,
			String... ignoreFields) {
		if (target == null || mapValues == null) return;

		List<String> ignoreField = new ArrayList<>();
		if (ignoreFields != null && ignoreFields.length > 0) {
			ignoreField.addAll(Arrays.asList(ignoreFields));
		}

		List<Field> lstCustomField = null;

		for (String key : mapValues.keySet()) {
			if (ignoreField.contains(key)) continue;

			Object value = mapValues.get(key);
			try {
				PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(target, key);
				if (pd == null) {
					/** customised field mapping for annotation JsonProperty */
					if (lstCustomField == null) {
						lstCustomField = getDeclaredFields(target.getClass());
					}
					for (Field f : lstCustomField) {
						JsonProperty annoJsonProp = f.getAnnotation(JsonProperty.class);
						if (annoJsonProp != null && key.equals(annoJsonProp.value())) {
							key = f.getName();
							pd = PropertyUtils.getPropertyDescriptor(target, key);
							break;
						}
					}

					if (pd == null) continue; // don't know why null
				}
				Class<?> propertyClass = pd.getPropertyType();
				if (Collection.class.isAssignableFrom(propertyClass)) {
					// collection Json to Object
					Collection col = (Collection) getProperty(target, key);
					if (col == null) {
						if (List.class.isAssignableFrom(propertyClass)) {
							col = new ArrayList();
						} else if (Set.class.isAssignableFrom(propertyClass)) {
							col = new LinkedHashSet();
						} else {
							col = (Collection) propertyClass.newInstance();
						}
					}
					col.clear();
					Field field = getDeclaredField(target.getClass(), key);
					ParameterizedType listType = (ParameterizedType) field.getGenericType();
					Class<?> collClass = (Class<?>) listType.getActualTypeArguments()[0];
					boolean isAcceObj = collClass.getName().indexOf("com.acceval") > -1;
					Collection convertedValue = (Collection) mapValues.get(key);
					if (convertedValue != null) {
						for (Object o : convertedValue) {
							if (isAcceObj) {
								Object collObj = getClassObject(collClass.getName());
								populateJsonMapToObj(objectMapper, collObj, (Map) o);
								col.add(collObj);
							} else {
								if(collClass.isAssignableFrom(Long.class) && o.getClass().isAssignableFrom(Integer.class)) {
									col.add(Long.valueOf((Integer)o));
								}else {
									col.add(o);
								}
								
							}
						}
						PropertyUtils.setProperty(target, key, col);
					} else {
						PropertyUtils.setProperty(target, key, null);
					}
				} else {
					Object convertedValue = objectMapper.convertValue(value, propertyClass);
					try {
						PropertyUtils.setProperty(target, key, convertedValue);
					} catch (NoSuchMethodException nsEx) {
						// do nothing... some special getter (non-property in Class) cause it
					}
				}
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | MicroServiceUtilException
					| NoSuchFieldException | SecurityException | InstantiationException e1) {
				Logger.error(e1.getMessage(), e1);
			}
		}

	}

	/**
	 * will scan thru super class
	 */
	public static Field getDeclaredField(Class<?> target, String property) throws SecurityException, NoSuchFieldException {
		try {
			return target.getDeclaredField(property);
		} catch (NoSuchFieldException e) {
			// no such field here, try super class
			Class<?> superClass = target.getSuperclass();
			if (superClass == null || Object.class.getName().equals(superClass.getName())) {
				throw e;
			}
			return getDeclaredField(superClass, property);
		}
	}

	/**
	 * will scan thru super class
	 */
	public static List<Field> getDeclaredFields(Class<?> target) throws SecurityException {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(target.getDeclaredFields()));
		if (target.getSuperclass() != null) {
			fields.addAll(getDeclaredFields(target.getSuperclass()));
		}

		return fields;
	}

	public static void copyNotNullProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
		copyNotNullPropertiesWithFilter(dest, orig);
	}

	public static void copyNotNullPropertiesWithFilter(Object dest, Object orig, String... filter)
			throws IllegalAccessException, InvocationTargetException {
		final PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(orig);
		final List<String> ignoreFiels = Arrays.asList(filter);
		for (PropertyDescriptor origDescriptor : origDescriptors) {
			final String name = origDescriptor.getName();

			if (ignoreFiels.contains(name)) continue;

			if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
				try {
					final Object value = PropertyUtils.getSimpleProperty(orig, name);

					if (value == null) continue;

					boolean primitive = origDescriptor.getPropertyType().isPrimitive();
					if (primitive) {
						Object defaultValue = Defaults.defaultValue(origDescriptor.getPropertyType());
						if (value.equals(defaultValue)) continue;
					}

					PropertyUtils.setSimpleProperty(dest, name, value);
				} catch (final NoSuchMethodException e) {
					if (Logger.isDebugEnabled()) {
						Logger.debug("Error writing to '" + name + "' on class '" + dest.getClass() + "'", e);
					}
				}
			}
		}
	}

	public static void copyProperties(Object dest, Object orig) {
		try {
			PropertyUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
	}

	/**
	 * copy properties with filter
	 */
	public static void copyPropertiesWithFilter(Object dest, Object orig, String... filter) {
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

				// Collection persistence safe
				if (value instanceof Collection) {
					Object dbColl = getProperty(dest, propKey);
					if (dbColl instanceof Set) {
						value = CollectionUtil.synToHibernateSet((Set) dbColl, (Collection<?>) value);
					} else if (dbColl instanceof List) {
						value = CollectionUtil.synToHibernateSet((List) dbColl, (Collection<?>) value);
					}
				}

				PropertyUtils.setProperty(dest, propKey, value);
			} catch (NoSuchMethodException e) {
				continue;
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static Field getPrimaryKeyField(Object object) {
		return getPrimaryKeyField(object.getClass());
	}

	public static Field getPrimaryKeyField(Class<?> clazz) {
		Field fields[] = clazz.getDeclaredFields();
		for (Field field : fields) {
			Annotation annotations[] = field.getDeclaredAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof javax.persistence.Id) return field;
			}
			// only getter methods can have persistence annotations, setters
			// cannot
			Method getter = null;
			try {
				getter = new PropertyDescriptor(field.getName(), clazz).getReadMethod();
			} catch (IntrospectionException e) {
				// do nothing, not all property has getter
			}
			if (getter != null) {
				Annotation getterAnnotations[] = getter.getDeclaredAnnotations();
				for (Annotation annotation : getterAnnotations) {
					if (annotation instanceof javax.persistence.Id) return field;
				}
			}
		}
		return null; // no primary key found
	}

	public static String getPrimaryKeyName(Object object) {
		Field field = getPrimaryKeyField(object);

		if (field == null) return null;

		return field.getName();
	}

	public static String getPrimaryKeyName(Class<?> clzz) {
		Field field = getPrimaryKeyField(clzz);

		if (field == null) return null;

		return field.getName();
	}

	public static Class<?> getClass(String clazzName) throws MicroServiceUtilException {
		try {
			if (clazzName == null) throw new IllegalArgumentException("Clazzame [" + "] clazzName is null");
			Class<?> clazz = Class.forName(clazzName);
			return clazz;
		} catch (ClassNotFoundException e) {
			throw new MicroServiceUtilException(e.getMessage());
		}

	}

	public static Object getClassObject(String clazzName) throws MicroServiceUtilException {
		if (clazzName == null) throw new IllegalArgumentException("Clazzame [" + "] clazzName is null");
		try {
			Object obj = Class.forName(clazzName).newInstance();
			return obj;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			Logger.error(e.getMessage(), e);
			throw new MicroServiceUtilException(e.getMessage());
		}
	}

	public static Object getClassObject(String clazzName, Object... constructorParam) throws MicroServiceUtilException {
		if (clazzName == null) throw new IllegalArgumentException("Clazzame [" + "] clazzName is null");
		try {
			Class<?> clazz = Class.forName(clazzName);
			Class<?>[] consClass = new Class[constructorParam.length];
			int i = 0;
			for (Object obj : constructorParam) {
				consClass[i++] = obj.getClass();
			}
			Constructor<?> constructor = clazz.getConstructor(consClass);
			return constructor.newInstance(constructorParam);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			Logger.error(e.getMessage(), e);
			throw new MicroServiceUtilException(e.getMessage());
		}
	}

	public static String[] getClassPojoProperties(String className, int level) throws MicroServiceUtilException {
		return getClassPojoProperties(className, level, false);
	}

	/**
	 * 
	 * @param className
	 * @param level
	 *            if level <= 0, go until the end of the property pojo. if level
	 *            = 1, only current level. if level > 1, this level property +
	 *            deeper level property if there is any.
	 * @return
	 */
	public static String[] getClassPojoProperties(String className, int level, boolean includeSuperclass) throws MicroServiceUtilException {
		List<String> fields = new ArrayList<>();

		List<Class<?>> classArray = new ArrayList<>();
		Class<?> clazz = getClass(className);
		classArray.add(clazz);

		if (includeSuperclass) {
			while (clazz.getSuperclass() != null) {
				clazz = clazz.getSuperclass();
				classArray.add(clazz);
			}
		}

		for (Iterator<Class<?>> it = classArray.iterator(); it.hasNext();) {
			Class<?> cls = (Class<?>) it.next();
			Field[] fs = cls.getDeclaredFields();

			boolean recursive = false;
			for (int i = 0; i < fs.length; i++) {
				Field field = fs[i];

				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				if (field.getType().isPrimitive()) {
					fields.add(field.getName());
					continue;
				}

				if (level <= 0) {
					recursive = true;
				} else if (level == 1) {
					recursive = false;
				} else {
					recursive = true;
				}

				if (recursive && field.getType().getSuperclass() != null
						&& field.getType().getSuperclass().getName().equals(BaseModel.class.getName())) {
					String[] temp = getClassPojoProperties(field.getType().getName(), level - 1, includeSuperclass);
					for (int u = 0; u < temp.length; u++) {
						String tempField = temp[u];
						fields.add(field.getName() + "." + tempField);
					}
				} else {
					// prevent infinited loop
					if (!field.getType().equals(cls)) {
						fields.add(field.getName());
					}
				}
			}
		}

		Collections.sort(fields);
		String[] retVal = new String[fields.size()];
		fields.toArray(retVal);

		return retVal;
	}

	public static Object invokeMethod(Object target, String methodName, Class<?>[] clazz, Object[] objects)
			throws MicroServiceUtilException {
		if (target == null || methodName == null) {
			throw new MicroServiceUtilException("target or methodName not allow to be null!");
		}

		try {
			return MethodUtils.invokeMethod(target, methodName, objects);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new MicroServiceUtilException(e);
		}
	}

	public static Object invokeMethod(Class<?> target, String methodName, Class<?>[] clazz, Object[] objects)
			throws MicroServiceUtilException {
		try {
			Method method = target.getMethod(methodName, clazz);

			return method.invoke(null, objects);
		} catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException
				| InvocationTargetException e) {
			throw new MicroServiceUtilException(e);
		}
	}

	public static String getGetterMethodName(Class<?> clz, String propName) {
		char[] propNameChars = propName.toCharArray();
		propNameChars[0] = Character.toUpperCase(propNameChars[0]);
		String getterName = "get" + new String(propNameChars);

		Method[] methods = clz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			if (methodName.equals(getterName)) return methodName;
		}

		return null;
	}

	public static Method getGetterMethod(Class<?> clz, String propName) {
		Method getter = null;
		try {
			getter = new PropertyDescriptor(propName, clz).getReadMethod();
			return getter;
		} catch (IntrospectionException e) {
			Logger.error(e.getMessage(), e);
		}

		return null;
	}

	public static Object getPropertyValue(Object target, String property) {
		try {
			return invokeMethod(target, getGetterMethodName(target.getClass(), property), null, null);
		} catch (MicroServiceUtilException e) {
			Logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * compare to getPropertyValue, this method will instance new Object
	 */
	public static Object getProperty(Object target, String property) throws MicroServiceUtilException {
		if (target instanceof HibernateProxy) {
			target = ((HibernateProxy) target).getHibernateLazyInitializer().getImplementation();
		}

		if (target == null) return null;

		try {
			return BeanUtilsBean.getInstance().getPropertyUtils().getNestedProperty(target, property);
		} catch (NestedNullException nullEx) {
			return null;
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new MicroServiceUtilException(e);
		}
	}

	public static void setProperty(Object target, String property, Object value) throws MicroServiceUtilException {
		try {
			if (value instanceof Double) {
				value = NumberUtil.zeroIfNullorNaN((Double) value);
			}

			Class targetClass = getPropertyClass(target.getClass(), property);
			if (targetClass.isEnum() && value instanceof String) {
				value = Enum.valueOf(targetClass, String.valueOf(value));
			}

			BeanUtilsBean.getInstance().getPropertyUtils().setNestedProperty(target, property, value);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new MicroServiceUtilException(e);
		}
	}

	public static Class<?> getPropertyClass(String clzName, String propName)
			throws SecurityException, NoSuchMethodException, MicroServiceUtilException {
		while (propName.indexOf(".") >= 0) {
			clzName = getPropertyClass(clzName, propName.substring(0, propName.indexOf("."))).getName();
			propName = propName.substring(propName.indexOf(".") + 1, propName.length());

		}
		return getPropertyClass(getClass(clzName), propName);
	}

	public static Class<?> getPropertyClass(Class<?> cls, String propName) throws SecurityException, NoSuchMethodException {
		char[] propNameChars = propName.toCharArray();
		propNameChars[0] = Character.toUpperCase(propNameChars[0]);
		String getterName = "get" + new String(propNameChars);

		try {
			Method getterMethod = cls.getMethod(getterName, (Class[]) null);
			return getterMethod.getReturnType();
		} catch (NoSuchMethodException e) {
			getterName = "is" + new String(propNameChars);
			Method getterMethod = cls.getMethod(getterName, (Class[]) null);
			return getterMethod.getReturnType();
		}
	}

	public static String getPrimitiveClassName(String className) {
		if ("long".equals(className)) {
			className = "java.lang.Long";
		} else if ("boolean".equals(className)) {
			className = "java.lang.Boolean";
		} else if ("byte".equals(className)) {
			className = "java.lang.Byte";
		} else if ("char".equals(className)) {
			className = "java.lang.Character";
		} else if ("short".equals(className)) {
			className = "java.lang.Short";
		} else if ("int".equals(className)) {
			className = "java.lang.Integer";
		} else if ("float".equals(className)) {
			className = "java.lang.Float";
		} else if ("double".equals(className)) {
			className = "java.lang.Double";
		}
		return className;
	}

	public static boolean isDateProperty(String clzName, String propName) {
		try {
			Class<?> clz = getPropertyClass(clzName, propName);
			if (clz.isPrimitive()) {
				return false;
			}

			try {
				Object obj = clz.newInstance();
				if (obj instanceof Date || obj instanceof LocalDate || obj instanceof LocalDateTime) {
					return true;
				}
			} catch (InstantiationException e) {
				// do nothing
			} catch (IllegalAccessException e) {
				// do nothing
			}

			String className = clz.getName();
			if (className.equals(Date.class.getName())) {
				return true;
			}
		} catch (SecurityException | NoSuchMethodException | MicroServiceUtilException e1) {
			Logger.error(e1.getMessage(), e1);
		}

		return false;
	}

	public static boolean isLongProperty(String clzName, String propName) {
		try {
			Class<?> clz = getPropertyClass(clzName, propName);
			if (clz.getName().indexOf("long") >= 0) return true;
			if (clz.getName().indexOf("Long") >= 0) return true;
		} catch (SecurityException | NoSuchMethodException | MicroServiceUtilException e1) {
			Logger.error(e1.getMessage(), e1);
		}
		return false;
	}

	public static boolean isIntegerProperty(String clzName, String propName) {
		try {
			Class<?> clz = getPropertyClass(clzName, propName);
			String className = getPrimitiveClassName(clz.getName());
			if ("java.lang.Integer".equals(className)) {
				return true;
			} else if ("java.lang.Short".equals(className)) {
				return true;
			}
		} catch (SecurityException | NoSuchMethodException | MicroServiceUtilException e1) {
			Logger.error(e1.getMessage(), e1);
		}
		return false;
	}

	public static boolean isDoubleProperty(String clzName, String propName) {
		try {
			Class<?> clz = getPropertyClass(clzName, propName);
			String className = getPrimitiveClassName(clz.getName());
			if ("java.lang.Double".equals(className)) {
				return true;
			} else if ("java.lang.Float".equals(className)) {
				return true;
			}
		} catch (SecurityException | NoSuchMethodException | MicroServiceUtilException e1) {
			Logger.error(e1.getMessage(), e1);
		}
		return false;
	}

	public static boolean isStringProperty(String clzName, String propName) {
		try {
			Class<?> clz = getPropertyClass(clzName, propName);
			if (clz.isAssignableFrom(String.class)) {
				return true;
			}
		} catch (SecurityException | NoSuchMethodException | MicroServiceUtilException e1) {
			Logger.error(e1.getMessage(), e1);
		}
		return false;
	}

	/**
	 * getAllClassMetadataByClasses retrieves all classes which are sub types of
	 * 
	 * @param classes
	 *            the parent classes to search for
	 * @return a map with the key being one of the class passed into this method
	 */
	public static Map<Class<?>, Set<Class<?>>> getAllSubtypeOfClasses(String packageToScan, Class... classes) {
		Map<Class<?>, Set<Class<?>>> allClasses = new HashMap<>(classes.length);

		Reflections reflections = new Reflections(packageToScan, new SubTypesScanner());

		for (Class aClass : classes) {
			Set<Class<?>> subTypesOf = reflections.getSubTypesOf(aClass);

			for (Iterator<Class<?>> iterator = subTypesOf.iterator(); iterator.hasNext();) {
				Class<?> subType = iterator.next();

				if (subType.isInterface()) {
					iterator.remove();
				}
			}

			allClasses.put(aClass, subTypesOf);
		}

		return allClasses;
	}

	public static Object cloneNewObjectWithFields(Object target, List<String> displayFields) {

		Object newObj = null;
		try {
			newObj = ClassUtil.getClassObject(target.getClass().getName());
		} catch (MicroServiceUtilException e1) {
			Logger.error(e1.getMessage(), e1);
		}

		for (String t : displayFields) {
			try {
				Object nvalue = ClassUtil.getProperty(target, t);
				if (t.indexOf(".") > -1) {
					try {
						PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(newObj, t.replace(t.substring(t.indexOf(".")), ""));
						if (pd == null) continue; // don't know why null
						Class<?> propertyClass = pd.getPropertyType();
						Object newInstanceValue = ClassUtil.getClassObject(propertyClass.getName());
						ClassUtil.setProperty(newObj, t.replace(t.substring(t.indexOf(".")), ""), newInstanceValue);
						ClassUtil.setProperty(newInstanceValue, t.substring(t.indexOf(".") + 1), nvalue);
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						Logger.error(e.getMessage(), e);
					}
				} else {
					ClassUtil.setProperty(newObj, t, nvalue);
				}
			} catch (MicroServiceUtilException e) {
				Logger.error(e.getMessage(), e);
			}
		}
		List<Field> existingFields = new ArrayList<>(Arrays.asList(target.getClass().getDeclaredFields()));
		boolean idFound = false;
		Class currentClass = target.getClass();
		do {
			for (Field field : existingFields) {
				if (field.isAnnotationPresent(Id.class)
						|| field.isAnnotationPresent(org.springframework.data.annotation.Id.class)) {
					idFound = true;
					try {
						field.setAccessible(true);
						Object value = field.get(target);
						Field idField = currentClass.getDeclaredField(field.getName());
						idField.setAccessible(true);
						idField.set(newObj, value);
						break;
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
							| SecurityException e) {
						Logger.error(e.getMessage(), e);
					}
				}
			}
			if (!idFound) {
				currentClass = currentClass.getSuperclass();
				existingFields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
			}
		} while (!idFound && currentClass.getSuperclass() != null);

		return newObj;
	}

	public static void slimDownQueryResult(QueryResult queryResult, MultiValueMap<String, String> mapParam) {
		// Filter result
		List<String> displayFields = new ArrayList<>();
		if (mapParam.containsKey("displayFields")) {
			displayFields = Arrays.asList(mapParam.getFirst("displayFields").split(","));
			mapParam.remove("displayFields");
		}
		if (displayFields != null && displayFields.size() > 0) {
			List<Object> newResult = new ArrayList<>();
			for (Object obj : queryResult.getResults()) {
				Object newObj = (Object) ClassUtil.cloneNewObjectWithFields(obj, displayFields);
				if (newObj == null) {
					newResult.add(obj);
				} else {
					newResult.add(newObj);
				}
			}
			queryResult.setResults(newResult);
		}
	}
}
