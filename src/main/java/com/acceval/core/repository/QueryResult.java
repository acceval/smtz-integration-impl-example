package com.acceval.core.repository;

import java.io.Serializable;
import java.util.List;

public class QueryResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private int total;
	private List<?> results;

	public QueryResult(int total, List<?> results) {
		super();
		if (total == 0) {
			total = results.size();
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

	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}

}
