package com.acceval.core.microservice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.acceval.core.util.ClassUtil;

/**
 * standard LabelValue to map back to Angular
 */
public class LabelValue implements Comparable<LabelValue> {
	private String label;
	private String label2;
	private String label3;
	private String label4;
	private String value;
	private String code;

	public LabelValue() {
		
	}

	public LabelValue(String value) {
		this.label = value;
		this.value = value;
	}
	
	public LabelValue(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}

	public LabelValue(String label, String code, String value) {
		super();
		this.label = label;
		this.code = code;
		this.value = value;
	}

	// TODO use varargs later
    public LabelValue(String label, String code, String label2, String label3, String label4, String value) {
        this.label = label;
		this.code = code;
        this.label2 = label2;
        this.label3 = label3;
        this.label4 = label4;
        this.value = value;
    }

	public String getCode() { return code; }

	public void setCode(String code) { this.code = code; }

    public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getLabel3() {
        return label3;
    }

    public void setLabel3(String label3) {
        this.label3 = label3;
    }

    public String getLabel4() {
        return label4;
    }

    public void setLabel4(String label4) {
        this.label4 = label4;
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

	public static List<LabelValue> toLabelValueList(Map<?, ?> map) {
		List<LabelValue> lst = new ArrayList<>();
		for (Object key : map.keySet()) {
			lst.add(new LabelValue(map.get(key).toString(), key.toString()));
		}
		return lst;
	}

	@Override
	public int compareTo(LabelValue other) {

		return this.label.compareTo(other.label);
	}

}
