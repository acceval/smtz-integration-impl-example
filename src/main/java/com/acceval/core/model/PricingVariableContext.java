package com.acceval.core.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class PricingVariableContext implements Serializable, Cloneable {

	private static final long serialVersionUID = 3457134321135281269L;


	private Map<String, Object> variableMap = Collections.synchronizedMap(new HashMap<>());

	@Override
	public PricingVariableContext clone() {
		try {
			PricingVariableContext context = (PricingVariableContext) super.clone();

			Map<String, Object> nMap = Collections.synchronizedMap(new HashMap<>());

			for (Iterator<String> itr = variableMap.keySet().iterator(); itr.hasNext();) {
				String key = itr.next();

				nMap.put(key, variableMap.get(key));
			}
			context.variableMap = nMap;

			return context;
		} catch (CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
	}

	public void setContext(PricingVariableContext context) {
		if (context != null) {
			this.variableMap.putAll(context.getVariableMap());
		}
	}

	public void setVariable(String key, Object value) {
		variableMap.put(key, value);
	}

	public void setVariable(String key, double value) {
		setVariable(key, new Double(value));
	}

	public void setVariable(String key, long value) {
		setVariable(key, new Long(value));
	}

	public void removeVariable(String key) {
		if (variableMap.containsKey(key)) variableMap.remove(key);
	}

	public <T> T getVariable(String key) {
		return (T) variableMap.get(key);
	}

	public String getVariableAsString(String key) {
		return getVariable(key) != null ? String.valueOf(getVariable(key)) : null;
	}

	public Date getVariableAsDate(String key) {
		Object data = getVariable(key);

		Date retVal = null;

		if (!(data instanceof Date)) {
			if (data instanceof String) {
				try {
					retVal = new SimpleDateFormat("dd/MM/yyyy").parse((String) data);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} else {
			retVal = (Date) data;
		}

		return retVal;
	}

	public double getVariableAsDouble(String key) {
		Object value = getVariable(key);

		if (value instanceof Double) {
			return (Double) value;
		}

		if (value == null) {
			return 0;
		}

		if (StringUtils.isBlank(String.valueOf(value))) {
			return 0;
		}
		Double retVal = Double.parseDouble(String.valueOf(value));

		return retVal == null ? 0 : retVal;
	}

	public long getVariableAsLong(String key) {

		Object value = getVariable(key);
		if (value == null) {
			return 0;
		}
		if (value instanceof Long) {
			return (Long) value;
		}

		if (StringUtils.isNotBlank(String.valueOf(value))) {
			return Long.parseLong(String.valueOf(value).trim());
		}
		return 0l;
	}

	public boolean getVariableAsBoolean(String key) {
		Object value = getVariable(key);
		if (value == null) {
			return false;
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}

		return false;
	}

	public Map<String, Object> getVariableMap() {
		return variableMap;
	}

	public void setVariableMap(Map<String, Object> variableMap) {
		this.variableMap = variableMap;
	}

}
