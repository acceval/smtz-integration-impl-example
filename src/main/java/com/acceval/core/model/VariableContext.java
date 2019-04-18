package com.acceval.core.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class VariableContext implements Serializable, Cloneable {

	private static final long serialVersionUID = 3457134321135281269L;
	
	public static final String CURRENCY_CODE = "CURRENCY_CODE";
	public static final String UOM_CODE = "UOM_CODE";
	public static final String QUANTITY = "QUANTITY";
	public static final String PRODUCT = "PRODUCT";
	public static final String GRADE = "GRADE";
	public static final String PERCENTAGE_APPLY_INPUT_PARAM = "PERCENTAGE_APPLY_INPUT_PARAM";
	public static final String VALID_FROM = "VALID_FROM";
	public static final String VALID_TO = "VALID_TO";
	public static final String CURRENCY_EXCHANGE_RATE_TYPE = "CURRENCY_EXCHANGE_RATE_TYPE";
	public static final String COMPANY_ID = "COMPANY_ID";
	public static final String COMPANY_CODE = "COMPANY_CODE";
	
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	// Pricing Technique Condition Table Code
	public static final String PRICETECH_DECIDER = "PRICING_TECHNIQUE_DECIDER";
	
	public static final String FLOOR_PRICE_TECHNIQUE_DECIDER = "FLOOR_PRICE_TECHNIQUE_DECIDER";

	// Deal Mgt Object
	public static final String OBJ_SALES_DOC = "salesDoc";
	public static final String OBJ_SALES_ITEM = "salesItem";
	public static final String OBJ_SALES_SCENARIO = "salesScenario";

	private Map<String, Object> variableMap = Collections.synchronizedMap(new HashMap<>());
	private Long companyId;
	private String companyCode;

	@Override
	public VariableContext clone() {
		try {
			VariableContext context = (VariableContext) super.clone();

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

	public void setContext(VariableContext context) {
		if (context != null) {
			this.variableMap.putAll(context.getVariableMap());
		}
	}

	public void setVariable(String key, Object value) {
		if (value instanceof LocalDate) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
			variableMap.put(key, ((LocalDate) value).format(formatter));
		} else {
			variableMap.put(key, value);
		}
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
		Object value = getVariable(key);
		if (value instanceof String) {
			return (String) value;
		} else if (value != null) {
			return String.valueOf(value);
		}
		return null;
	}

	public LocalDate getVariableAsDate(String key) {
		Object data = getVariable(key);

		LocalDate retVal = null;

		if (!(data instanceof LocalDate)) {
			if (data instanceof String) {
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
				retVal = LocalDate.parse((String) data, formatter);			
			}
		} else {
			retVal = (LocalDate) data;
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

	public int getVariableAsInteger(String key) {
		Object value = getVariable(key);
		if (value instanceof Integer) {
			return (Integer) value;
		}

		if (value == null) {
			return 0;
		}
		if (StringUtils.isBlank(String.valueOf(value))) {
			return 0;
		}
		Integer retVal = Integer.parseInt(String.valueOf(value));
		return retVal == null ? 0 : retVal;
	}

	public Long getVariableAsLong(String key) {

		Object value = getVariable(key);
		if (value == null) {
			return null;
		}
		if (value instanceof Long) {
			return (Long) value;
		}

		if (StringUtils.isNotBlank(String.valueOf(value))) {
			return Long.parseLong(String.valueOf(value).trim());
		}
		return null;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		setVariable(COMPANY_ID, companyId);
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		setVariable(COMPANY_CODE, companyCode);
		this.companyCode = companyCode;
	}
	
	public Map<String, Object> getNonBaseModelMap() {
		Map<String, Object> map = new HashMap<>();
		for (String key : getVariableMap().keySet()) {
			if (!(getVariableMap().get(key) instanceof BaseModel)) {
				map.put(key, getVariableMap().get(key));
			}
		}
		return map;
	}
}
