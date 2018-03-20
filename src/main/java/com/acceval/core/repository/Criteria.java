package com.acceval.core.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Criteria implements Serializable {

	private static final long serialVersionUID = 6599208881897213365L;

	private int pageSize = 20;
	private int requestedPage = 0;
	private List<Criterion> criterion = new ArrayList<>();
	private List<Order> order = new ArrayList<>();
	//	private Projection[] projection = {}; TODO
	private boolean fetchAll = true;
	private boolean initialise = true;
	private String filterDescription;

	public Criteria() {

	}

	public void removeEmptyCriterion() {
		ArrayList<Criterion> list = new ArrayList<>();
		for (Criterion c : criterion) {
			if (!c.hasSearchValueSpecifed()) {
				list.add(c);
			}

		}
		criterion.removeAll(list);
	}

	public boolean hasSearchProperty(String property) {
		for (Criterion c : criterion) {
			if (c.getPropertyName() != null && c.getPropertyName().equals(property)) {
				return true;
			}

		}
		return false;
	}

	public void removeOrder(String property) {
		List<Order> lst = new ArrayList<>();
		for (Order o : order) {
			if (StringUtils.isNotBlank(o.getProperty()) && o.getProperty().equals(property)) {
				lst.add(o);
			}
		}
	}

	public Criterion removeSearchProperty(String property) {
		for (Criterion c : criterion) {
			if (c.getPropertyName() != null && c.getPropertyName().equals(property)) {
				criterion.remove(c);
				return c;
			}
		}
		return null;
	}

	//	public Projection removeProjection(String property) {
	//		for (int i = 0; i < projection.length; i++) {
	//			Projection c = projection[i];
	//			if (c.getProperty() != null && c.getProperty().equals(property)) {
	//				projection = (Projection[]) ArrayUtils.remove(projection, i);
	//
	//				return c;
	//			}
	//
	//		}
	//		return null;
	//
	//	}

	public Object getSearchValue(String property) {
		for (Criterion c : criterion) {
			if (c.getPropertyName() != null && c.getPropertyName().equals(property)) {
				return c.getSearchValue();
			}
		}
		return null;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setRequestedPage(int requestedPage) {
		this.requestedPage = requestedPage;
	}

	public int getRequestedPage() {
		return this.requestedPage;
	}

	//	public void setProjection(Projection[] projection) {
	//		this.projection = projection;
	//	}
	//
	//	public Projection[] getProjection() {
	//		return projection;
	//	}
	//
	//	public void appendProjection(Projection projection) {
	//		Projection[] objs = new Projection[this.projection.length + 1];
	//
	//		int i = 0;
	//		for (; i < this.projection.length; i++) {
	//			objs[i] = this.projection[i];
	//		}
	//		objs[i] = projection;
	//		this.projection = objs;
	//
	//	}
	//
	//	public boolean hasGroupByProjection(String propertyName) {
	//		for (int i = 0; i < this.projection.length; i++) {
	//			if (this.projection[i].isGroup() && this.projection[i].getProperty().equals(propertyName)) {
	//				return true;
	//
	//			}
	//		}
	//
	//		return false;
	//
	//	}

	public List<Criterion> getCriterion() {
		return criterion;
	}

	public void setCriterion(List<Criterion> criterion) {
		this.criterion = criterion;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public Criteria appendCriterion(Criterion criterion) {
		this.criterion.add(criterion);
		return this;
	}

	public Criteria appendOrder(Order order) {
		this.order.add(order);
		return this;
	}

	public boolean isFetchAll() {
		return fetchAll;
	}

	public void setFetchAll(boolean b) {
		fetchAll = b;
	}

	public boolean isInitialise() {
		return initialise;
	}

	public void setInitialise(boolean b) {
		initialise = b;
	}

	//	public boolean hasWeightedAverageProjection() {
	//		if (this.projection == null) return false;
	//
	//		for (int i = 0; i < projection.length; i++) {
	//			if (projection[i].isWeightedAverage()) {
	//				return true;
	//			}
	//
	//		}
	//		return false;
	//	}

	public Object clone() {
		Criteria criteria = new Criteria();

		if (this.criterion != null) {
			List<Criterion> cloneCriterion = new ArrayList<>();
			for (Criterion c : criterion) {
				cloneCriterion.add((Criterion) c.clone());
			}
			criteria.setCriterion(cloneCriterion);
		}

		criteria.setFetchAll(this.isFetchAll());
		criteria.setInitialise(this.isInitialise());
		criteria.setOrder(this.getOrder());
		criteria.setPageSize(this.getPageSize());
		//		criteria.setProjection(this.getProjection());
		criteria.setRequestedPage(this.getRequestedPage());

		return criteria;
	}

	public String getFilterDescription() {
		return filterDescription;
	}

	public void setFilterDescription(String filterDescription) {
		this.filterDescription = filterDescription;
	}

}