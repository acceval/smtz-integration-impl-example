package com.acceval.core.repository;

import java.util.ArrayList;
import java.util.List;

public class OrCriterion extends Criterion {
	private static final long serialVersionUID = 1L;

	List<Criterion> criterions = new ArrayList<Criterion>();

	public OrCriterion() {
		super();
	}

	public OrCriterion(String propertyName, Object searchValue) {
		super();
		Criterion firstC = new Criterion(propertyName, searchValue);
		criterions.add(firstC);
	}

	public OrCriterion(String propertyName, Object[] searchValues) {
		super();
		Criterion firstC = new Criterion(propertyName, searchValues);
		criterions.add(firstC);
	}

	public OrCriterion appendCriterion(Criterion criterion) {
		criterions.add(criterion);
		return this;
	}

	public void or(Criterion c) {
		criterions.add(c);
	}

	public List<Criterion> getCriterions() {
		return criterions;
	}

	@Override
	public OrCriterion clone() {
		return (OrCriterion) super.clone();
	}
}
