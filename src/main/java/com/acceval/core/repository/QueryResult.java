package com.acceval.core.repository;

import java.io.Serializable;
import java.util.List;

public class QueryResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private int total;
	private List<T> results;

	public QueryResult() {
		super();
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
