package com.acceval.core.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;

public class Criterion implements Serializable, Cloneable {

	private static final long serialVersionUID = -467004656081639233L;
	public static final String STRING = "STRING";
	public static final String DATE = "DATE";

	public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
	public static final String DEFAULT_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

	public static final String DEFAULT_MONGO_DATE_FORMAT = "%d-%m-%Y";
	public static final String DEFAULT_MONGO_DATE_TIME_FORMAT = "%d-%m-%Y %H:%M:%S";

	public static final String SIGN_LESS_OR_EQUAL = "<=";
	public static final String SIGN_GREATER_OR_EQUAL = ">=";
	public static final String SIGN_DELIMITER = ";";

	public enum RestrictionType {
		EQUAL("="), LESS_OR_EQUAL(SIGN_LESS_OR_EQUAL), GREATER_OR_EQUAL(SIGN_GREATER_OR_EQUAL), GREATER(">"), LESS("<"), NOT_EQUAL("<>"),
		IN("in"), NOT_IN("not in"), IS_NULL("is null"), IS_NOT_NULL("is not null"), IS_BLANK("is null");

		private String sign;

		RestrictionType(String sign) {
			this.sign = sign;
		}

		public String getSign() {
			return sign;
		}

		public static RestrictionType getRestrictionTypeBySign(String sign, RestrictionType defaultRestType) {
			for (RestrictionType resType : RestrictionType.values()) {
				if (resType.getSign().equals(sign)) return resType;
			}
			return defaultRestType;
		}
	}

	private boolean caseSensitive = true;
	private boolean exactSearch = true;
	private boolean searchStartWith = false;
	private String propertyName;
	private String alternatePropertyName; // mainly for mongo aggregration, need new name
	private Object searchValue;
	private Object[] searchValues;
	private String searchValueDataType = STRING;

	RestrictionType restrictionType = RestrictionType.EQUAL;
	
	private String paramKey;
	private String nestedConditionKey;

	public Criterion() {

	}

	public Criterion(String propertyName, Object searchValue) {
		this.setPropertyName(propertyName);
		if (searchValue != null && searchValue.getClass().isArray()) {
			if (RestrictionType.EQUAL.equals(this.restrictionType)) {
				this.restrictionType = RestrictionType.IN;
			}
			if (searchValue instanceof long[]) {
				this.setSearchValues(ArrayUtils.toObject((long[]) searchValue));
			} else if (searchValue instanceof int[]) {
				this.setSearchValues(ArrayUtils.toObject((int[]) searchValue));
			} else if (searchValue instanceof double[]) {
				this.setSearchValues(ArrayUtils.toObject((double[]) searchValue));
			} else if (searchValue instanceof float[]) {
				this.setSearchValues(ArrayUtils.toObject((float[]) searchValue));
			} else {
				this.setSearchValues((Object[]) searchValue);
			}
		} else {
			this.setSearchValue(searchValue);
		}
	}

	public Criterion(String propertyName, Object[] searchValues) {
		this.setRestrictionType(RestrictionType.IN);
		this.setPropertyName(propertyName);
		this.setSearchValues(searchValues);
	}

	public Criterion(String propertyName, RestrictionType restrictionType, Object searchValue) {
		this.setPropertyName(propertyName);
		this.setSearchValue(searchValue);
		this.setRestrictionType(restrictionType);
		if (searchValue instanceof java.util.Date) {
			this.setSearchValueDataType(DATE);
		}
	}

	public Criterion(String propertyName, RestrictionType restrictionType, Object[] searchValues) {
		this.setPropertyName(propertyName);
		this.setSearchValues(searchValues);
		this.setRestrictionType(restrictionType);
	}

	public Criterion(String propertyName, Object searchValue, boolean isExactSearch) {
		this.setPropertyName(propertyName);
		this.setSearchValue(searchValue);
		exactSearch = isExactSearch;
		if (isExactSearch) {
			caseSensitive = true;
		}
	}

	public boolean lessThanOrEqual() {
		return RestrictionType.LESS_OR_EQUAL.equals(this.getRestrictionType());
	}

	public boolean less() {
		return RestrictionType.LESS.equals(this.getRestrictionType());
	}

	public boolean notEqual() {
		return RestrictionType.NOT_EQUAL.equals(this.getRestrictionType());
	}

	public boolean greaterThanOrEqual() {
		return RestrictionType.GREATER_OR_EQUAL.equals(this.getRestrictionType());
	}

	public boolean greater() {
		return RestrictionType.GREATER.equals(this.getRestrictionType());
	}

	public boolean in() {
		return RestrictionType.IN.equals(this.getRestrictionType());
	}

	public boolean notIn() {
		return RestrictionType.NOT_IN.equals(this.getRestrictionType());
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public boolean getCaseSensitive() {
		return caseSensitive;
	}

	public boolean isCaseSensitive() {
		return this.getCaseSensitive();
	}

	public void setExactSearch(boolean exactSearch) {
		this.exactSearch = exactSearch;

	}

	public boolean getExactSearch() {
		return this.exactSearch;
	}

	public boolean isExactSearch() {
		return this.getExactSearch();
	}

	public void setSearchValue(Object value) {
		this.searchValue = value;
	}

	public Object getSearchValue() {
		return this.searchValue;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public RestrictionType getRestrictionType() {
		return restrictionType;
	}

	public void setRestrictionType(RestrictionType restrictionType) {
		this.restrictionType = restrictionType;
	}

	public boolean isSearchStartWith() {
		return searchStartWith;
	}

	public void setSearchStartWith(boolean searchStartWith) {
		this.searchStartWith = searchStartWith;
	}

	public Object[] getSearchValues() {
		return searchValues;
	}

	public boolean hasSearchValues() {
		return this.getSearchValues() != null && this.getSearchValues().length > 0;
	}

	public boolean hasSearchValueSpecifed() {
		return hasSearchValues() || (this.searchValue != null && !searchValue.toString().trim().equals(""));
	}

	public void setSearchValues(Object[] searchValues) {
		this.searchValues = searchValues;
	}

	public String getSearchValueDataType() {
		return searchValueDataType;
	}

	public void setSearchValueDataType(String searchValueDataType) {
		this.searchValueDataType = searchValueDataType;
	}

	public boolean isSearchValueDataTypeDate() {
		return DATE.equalsIgnoreCase(this.getSearchValueDataType());
	}

	public String getAlternatePropertyName() {
		return alternatePropertyName;
	}

	public void setAlternatePropertyName(String alternatePropertyName) {
		this.alternatePropertyName = alternatePropertyName;
	}
	
	public String getNestedConditionKey() {
		return nestedConditionKey;
	}

	public void setNestedConditionKey(String nestedConditionKey) {
		this.nestedConditionKey = nestedConditionKey;
	}
		
	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public static void buildDefaultSortingDesc(MultiValueMap<String, String> mapParam, String... properties) {
		buildDefaultSorting(mapParam, BaseRepositoryImpl._DESC, properties);
	}

	public static void buildDefaultSortingAsc(MultiValueMap<String, String> mapParam, String... properties) {
		buildDefaultSorting(mapParam, BaseRepositoryImpl._ASC, properties);
	}

	private static void buildDefaultSorting(MultiValueMap<String, String> mapParam, String order, String... properties) {
		List<String> lstSort = mapParam.get(BaseRepositoryImpl._SORT);
		for (String property : properties) {
			boolean isPropertyFound = false;
			if (CollectionUtils.isNotEmpty(lstSort)) {
				for (String sort : lstSort) {
					isPropertyFound = isPropertyFound || sort.contains(property + ",");
				}
			}
			if (!isPropertyFound) {
				mapParam.add(BaseRepositoryImpl._SORT, property + "," + order);
			}
		}
	}

	public static void buildGreaterEqualDateRestriction(MultiValueMap<String, String> mapParam, String key, String property) {
		buildDateRestriction(mapParam, key, property, Criterion.SIGN_GREATER_OR_EQUAL);
	}

	public static void buildLessEqualDateRestriction(MultiValueMap<String, String> mapParam, String key, String property) {
		buildDateRestriction(mapParam, key, property, Criterion.SIGN_LESS_OR_EQUAL);
	}

	private static void buildDateRestriction(MultiValueMap<String, String> mapParam, String key, String property, String sign) {
		if (StringUtils.isBlank(key)) {
			key = property;
		}
		List<String> lstPropValue = mapParam.get(key);
		if (CollectionUtils.isNotEmpty(lstPropValue)) {
			for (String strDate : lstPropValue) {
				if (StringUtils.isNotBlank(strDate)) {
					mapParam.add(property, sign + Criterion.SIGN_DELIMITER + strDate);
				}
			}
		}
		mapParam.remove(key);
	}

	public static void buildGreaterEqualDateRestriction(MultiValueMap<String, String> mapParam, String property) {
		buildDateRestriction(mapParam, property, Criterion.SIGN_GREATER_OR_EQUAL);
	}

	public static void buildLessEqualDateRestriction(MultiValueMap<String, String> mapParam, String property) {
		buildDateRestriction(mapParam, property, Criterion.SIGN_LESS_OR_EQUAL);
	}

	private static void buildDateRestriction(MultiValueMap<String, String> mapParam, String property, String sign) {
		if (mapParam.getFirst(property) != null) {
			String strDate = mapParam.getFirst(property);
			if (StringUtils.isNotBlank(strDate)) {
				mapParam.set(property, sign + Criterion.SIGN_DELIMITER + strDate);
			}
		}
	}

	public static void buildTransientSorting(MultiValueMap<String, String> mapParam, String key, String... sortProperties) {
		List<String> sort = mapParam.get(BaseRepositoryImpl._SORT);
		if (CollectionUtils.isNotEmpty(sort)) {
			List<String> newSort = new ArrayList<>();
			for (String s : sort) {
				if (s.indexOf(key + ",") > -1) {
					String sortOption = StringUtils.substring(s, s.indexOf(","));
					for (String sortProp : sortProperties) {
						newSort.add(sortProp + sortOption);
					}
				} else {
					newSort.add(s);
				}
			}
			mapParam.replace(BaseRepositoryImpl._SORT, newSort);
		}
	}

	@Override
	public Criterion clone() {
		try {
			return (Criterion) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
