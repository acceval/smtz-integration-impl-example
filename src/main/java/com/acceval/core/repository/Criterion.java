package com.acceval.core.repository;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;

public class Criterion implements Serializable {

	private static final long serialVersionUID = -467004656081639233L;
	public static final String STRING = "STRING";
	public static final String DATE = "DATE";

	enum RestrictionType {
		EQUAL("="), LESS_OR_EQUAL("<="), GREATER_OR_EQUAL(">="), GREATER(">"), LESS("<"), NOT_EQUAL("<>"), IN("in"), NOT_IN("not in"),
		IS_NULL("is null"), IS_NOT_NULL("is not null");

		private String sign;
		RestrictionType(String sign) {
			this.sign = sign;
		}

		public String getSign() {
			return sign;
		}
	}

	private boolean caseSensitive = true;
	private boolean exactSearch = true;
	private boolean searchStartWith = false;
	private String propertyName;
	private Object searchValue;
	private Object[] searchValues;
	private String searchValueDataType = STRING;

	RestrictionType restrictionType = RestrictionType.EQUAL;

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

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
