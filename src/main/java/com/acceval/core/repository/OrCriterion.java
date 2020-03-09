package com.acceval.core.repository;

import java.util.ArrayList;
import java.util.List;

public class OrCriterion extends Criterion {
	private static final long serialVersionUID = 1L;

	private List<Criterion> criterions = new ArrayList<Criterion>();
	private List<Criteria> criterias = new ArrayList<>();

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

	public OrCriterion(String propertyName, RestrictionType reType, Object searchValue) {
		super();
		Criterion firstC = new Criterion(propertyName, reType, searchValue);
		criterions.add(firstC);
	}

	public OrCriterion appendCriterion(Criterion criterion) {
		criterions.add(criterion);
		return this;
	}

	public OrCriterion appendCriteria(Criteria criteria) {
		criterias.add(criteria);
		return this;
	}

	public void or(Criterion c) {
		criterions.add(c);
	}

	public List<Criterion> getCriterions() {
		return criterions;
	}

	public void setCriterions(List<Criterion> criterions) {
		this.criterions = criterions;
	}

	public List<Criteria> getCriterias() {
		return criterias;
	}

	public void setCriterias(List<Criteria> criterias) {
		this.criterias = criterias;
	}

	@Override
	public OrCriterion clone() {
		return (OrCriterion) super.clone();
	}
}
