package com.acceval.core.microservice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.acceval.core.util.ClassUtil;

/**
 * standard LabelValue to map back to Angular
 */
public class LabelValue implements Comparable<LabelValue> {
	private String label;
	private String value;

	public LabelValue() {
		
	}
	
	public LabelValue(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static List<LabelValue> toLabelValueList(Collection<?> sources, String labelProperty, String valueProperty) {
		List<LabelValue> lst = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(sources)) {
			for (Object obj : sources) {
				if (StringUtils.isBlank(valueProperty) && StringUtils.isBlank(labelProperty)) {
					lst.add(new LabelValue(obj.toString(), obj.toString()));
				} else {
					lst.add(new LabelValue(String.valueOf(ClassUtil.getPropertyValue(obj, labelProperty)),
							String.valueOf(ClassUtil.getPropertyValue(obj, valueProperty))));
				}
			}
		}
		return lst;
	}

	@Override
	public int compareTo(LabelValue other) {

		return this.label.compareTo(other.label);
	}

}
