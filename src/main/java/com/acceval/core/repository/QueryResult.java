package com.acceval.core.repository;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.acceval.core.MicroServiceUtilException;
import com.acceval.core.microservice.model.LabelValue;
import com.acceval.core.util.ClassUtil;

public class QueryResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private int total;
	private List<T> results;

	public QueryResult() {
		super();
	}

	public QueryResult(List<T> results) {
		super();
		this.total = results.size();
		this.results = results;
	}

	public QueryResult(int total, List<T> results) {
		super();
		if (total == 0) {
			this.total = results.size();
		} else {
			this.total = total;
		}
		this.results = results;
	}

	public static void resolveLabelValue(QueryResult queryResult, String sourceProperty, String targetProperty,
			List<LabelValue> datasource) {
		if (queryResult == null || CollectionUtils.isEmpty(queryResult.getResults())) return;

		for (Object targetObj : queryResult.getResults()) {
			String strValue = String.valueOf(ClassUtil.getPropertyValue(targetObj, sourceProperty));
			if (StringUtils.isNotBlank(strValue)) {
				for (LabelValue labelValue : datasource) {
					if (strValue.equals(labelValue.getValue())) {
						try {
							ClassUtil.setProperty(targetObj, targetProperty, labelValue.getLabel());
						} catch (MicroServiceUtilException e) {
						}
						break;
					}
				}
			}
		}
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

}
