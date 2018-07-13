package com.acceval.core.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.acceval.core.model.BaseModel;

public class CollectionUtil {

	/**
	 * target object list and source object list must having the same object
	 * type and having "equals" override method in the object
	 */
	public static <T> Set<T> synToHibernateSet(Set<T> target, Collection<T> source) {
		if (target == null) {
			target = new LinkedHashSet<>();
		}
		synProcess(target, source);
		return target;
	}

	public static <T> List<T> synToHibernateSet(List<T> target, Collection<T> source) {
		if (target == null) {
			target = new ArrayList<>();
		}

		synProcess(target, source);

		// re-arrange
		List<T> tempList = new ArrayList<>();
		for (T obj : target) {
			if (obj != null) {
				tempList.add(obj);
			}
		}
		target.clear();
		for (T obj : tempList) {
			target.add(obj);
		}

		return target;
	}

	private static <T> Collection<T> synProcess(Collection<T> target, Collection<T> source) {
		if (source != null && !source.isEmpty()) {
			for (T objSource : source) {
				for (T objTarget : target) {
					if (objSource.equals(objTarget)) {
						String pkProperty = ClassUtil.getPrimaryKeyName(objTarget);
						if (StringUtils.isNotBlank(pkProperty)) {
							ClassUtil.copyPropertiesWithFilter(objTarget, objSource, new String[] { pkProperty });
							if (objTarget instanceof BaseModel) {
								((BaseModel) objTarget).setModified(LocalDateTime.now());
							}
						} else {
							ClassUtil.copyProperties(objTarget, objSource);
						}
					}
				}
				boolean isAdded = target.add(objSource);
				if (isAdded && objSource instanceof BaseModel) {
					((BaseModel) objSource).setCreated(LocalDateTime.now());
				}
			}

			// remove item
			List<T> lstToRemove = new ArrayList<>();
			for (T objTarget : target) {
				int index = 0;
				for (T objSource : source) {
					index++;
					if (objTarget.equals(objSource)) {
						break;
					}
					if (index == source.size()) {
						lstToRemove.add(objTarget);
					}
				}
			}
			for (T removeItem : lstToRemove) {
				target.remove(removeItem);
			}
		} else {
			target.clear();
		}

		return target;
	}
}
