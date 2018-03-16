package com.acceval.core.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import com.acceval.core.repository.Criterion.RestrictionType;

public abstract class BaseRepositoryImpl implements BaseRepository {

	public static String _PAGE = "_page";
	public static String _PAGESIZE = "_pageSize";
	public static String _SORT = "_sort";

	public static String _ASC = "asc";
	public static String _DESC = "desc";

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseRepositoryImpl.class);

	protected abstract EntityManagerFactory getEntityManagerFactory();
	
	protected abstract EntityManager getEntityManager();
	
	protected abstract Class<?> getTargetClass();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public QueryResult queryByCriteria(Criteria acceCriteria, Class<?> targetClass) {

		CriteriaBuilder builder = getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<?> criteria = builder.createQuery(targetClass);

		Root<?> root = criteria.from(targetClass);
		criteria.select((Selection) root);
		List<Predicate> lstPredicate = new ArrayList<>();

		/** criteria */
		for (Criterion criterion : acceCriteria.getCriterion()) {
			String property = criterion.getPropertyName();
			Object value = criterion.getSearchValue();
			Object[] values = criterion.getSearchValues();
			RestrictionType restrictionType = criterion.getRestrictionType();

			Path path = getPath(root, property);
			Class<?> attrClass = path.getJavaType();

			// Null
			if (Criterion.RestrictionType.IS_NULL.equals(restrictionType)) {
				lstPredicate.add(builder.isNull(path));
			} else if (Criterion.RestrictionType.IS_NOT_NULL.equals(restrictionType)) {
				lstPredicate.add(builder.isNotNull(path));
			}
			// LIKE
			else if (!criterion.isExactSearch() && value instanceof String) {
				lstPredicate.add(builder.like(builder.lower(path), "%" + value.toString().toLowerCase() + "%"));
			}
			// EQUAL
			else if (Criterion.RestrictionType.EQUAL.equals(restrictionType)) {
				// handle Date
				if (ClassUtils.isAssignable(attrClass, Date.class) || ClassUtils.isAssignable(attrClass, LocalDateTime.class)
						|| ClassUtils.isAssignable(attrClass, LocalDate.class)) {
					if (value instanceof String) {
						lstPredicate.add(builder.equal(
								builder.function("TO_CHAR", String.class, path, builder.literal("dd/MM/yyyy")), value));
					} else if (value instanceof Date) {
						lstPredicate.add(
								builder.equal(builder.function("TO_CHAR", String.class, path, builder.literal("dd/MM/yyyy")),
										new SimpleDateFormat("dd/MM/yyyy").format(value)));
					} else if (value instanceof LocalDate) {
						LocalDate vLocalDate = (LocalDate) value;
						lstPredicate.add(
								builder.equal(builder.function("TO_CHAR", String.class, path, builder.literal("dd/MM/yyyy")),
										vLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
					} else if (value instanceof LocalDateTime) {
						LocalDateTime vLocalDate = (LocalDateTime) value;
						lstPredicate.add(
								builder.equal(builder.function("TO_CHAR", String.class, path, builder.literal("dd/MM/yyyy")),
										vLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
					}
				} else {
					lstPredicate.add(builder.equal(path, value));
				}
			} else if (Criterion.RestrictionType.IN.equals(restrictionType)) {
				lstPredicate.add(builder.in(path.in(values)));
			} else if (Criterion.RestrictionType.NOT_IN.equals(restrictionType)) {
				lstPredicate.add(builder.not(path.in(values)));
			}
			// GREAT/LESS
			else if (Criterion.RestrictionType.GREATER.equals(restrictionType)) {
				if (value instanceof LocalDate) {
					lstPredicate.add(builder.greaterThan(path, (LocalDate) value));
				} else if (value instanceof Date) {
					lstPredicate.add(builder.greaterThan(path, (Date) value));
				} else if (value instanceof LocalDateTime) {
					lstPredicate.add(builder.greaterThan(path, (LocalDateTime) value));
				} else if (value instanceof Number) {
					lstPredicate.add(builder.gt(path, (Number) value));
				}
			} else if (Criterion.RestrictionType.GREATER_OR_EQUAL.equals(restrictionType)) {
				if (value instanceof LocalDate) {
					lstPredicate.add(builder.greaterThanOrEqualTo(path, (LocalDate) value));
				} else if (value instanceof Date) {
					lstPredicate.add(builder.greaterThanOrEqualTo(path, (Date) value));
				} else if (value instanceof LocalDateTime) {
					lstPredicate.add(builder.greaterThanOrEqualTo(path, (LocalDateTime) value));
				} else if (value instanceof Number) {
					lstPredicate.add(builder.ge(path, (Number) value));
				}
			} else if (Criterion.RestrictionType.LESS.equals(restrictionType)) {
				if (value instanceof LocalDate) {
					lstPredicate.add(builder.lessThan(path, (LocalDate) value));
				} else if (value instanceof Date) {
					lstPredicate.add(builder.lessThan(path, (Date) value));
				} else if (value instanceof LocalDateTime) {
					lstPredicate.add(builder.lessThan(path, (LocalDateTime) value));
				} else if (value instanceof Number) {
					lstPredicate.add(builder.lt(path, (Number) value));
				}
			} else if (Criterion.RestrictionType.LESS_OR_EQUAL.equals(restrictionType)) {
				if (value instanceof LocalDate) {
					lstPredicate.add(builder.lessThanOrEqualTo(path, (LocalDate) value));
				} else if (value instanceof Date) {
					lstPredicate.add(builder.lessThanOrEqualTo(path, (Date) value));
				} else if (value instanceof LocalDateTime) {
					lstPredicate.add(builder.lessThanOrEqualTo(path, (LocalDateTime) value));
				} else if (value instanceof Number) {
					lstPredicate.add(builder.le(path, (Number) value));
				}
			}
			//unknown
			else {
				LOGGER.error("Criterion for [" + criterion.getPropertyName() + "] failed!");
			}
		}
		if (!lstPredicate.isEmpty()) {
			criteria.where(lstPredicate.stream().toArray(Predicate[]::new));
		}

		/** TODO projection? */

		/** order */
		if (acceCriteria.getOrder() != null) {
			List<javax.persistence.criteria.Order> lstOrder = new ArrayList<>();
			for (Order order : acceCriteria.getOrder()) {
				if (order.getIsAscending()) {
					lstOrder.add(builder.asc(root.get(order.getProperty())));
				} else {
					lstOrder.add(builder.desc(root.get(order.getProperty())));
				}
			}
			if (CollectionUtils.isNotEmpty(lstOrder)) {
				criteria.orderBy(lstOrder);
			}
		}

		/** start query */
		QueryResult queryResult = null;
		Query query = getEntityManager().createQuery(criteria);
		int total = 0;
		if (!acceCriteria.isFetchAll()) {
			int page = acceCriteria.getRequestedPage();
			int pageSize = acceCriteria.getPageSize();

			CriteriaQuery<Long> cCount = builder.createQuery(Long.class);
			cCount.multiselect(builder.count(cCount.from(targetClass)));
			if (!lstPredicate.isEmpty()) {
				cCount.where(lstPredicate.stream().toArray(Predicate[]::new));
			}

			query.setFirstResult(page * pageSize);
			query.setMaxResults(pageSize);
			total = getEntityManager().createQuery(cCount).getSingleResult().intValue();
		}

		List<?> result = query.getResultList();
		queryResult = new QueryResult(total, result);

		return queryResult;
	}

	@Override
	public QueryResult queryByMapParam(MultiValueMap<String, String> mapParam) {
		return queryByMapParam(mapParam, getTargetClass());
	}

	/**
	 * convert map to criteria and inquiry, normally use for Search Form, with pagination
	 */
	@Override
	public QueryResult queryByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass) {
		return queryByCriteria(getCriteriaByMapParam(mapParam, targetClass), targetClass);
	}

	/**
	 * convert map to criteria
	 */
	@Override
	public Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam) {
		return getCriteriaByMapParam(mapParam, getTargetClass());
	}

	/**
	 * convert map to criteria
	 */
	@Override
	public Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass) {
		int page = mapParam.get(_PAGE) != null ? Integer.parseInt(mapParam.getFirst(_PAGE)) : 0;
		int pageSize = mapParam.get(_PAGESIZE) != null ? Integer.parseInt(mapParam.getFirst(_PAGESIZE)) : 0;
		List<String> lstSort = mapParam.get(_SORT);

		Criteria acceCriteria = new Criteria();
		acceCriteria.setRequestedPage(page);
		acceCriteria.setPageSize(pageSize);
		acceCriteria.setFetchAll(false);

		CriteriaBuilder builder = getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<?> criteria = builder.createQuery(targetClass);
		Root<?> root = criteria.from(targetClass);

		// order
		if (CollectionUtils.isNotEmpty(lstSort)) {
			List<Order> lstOrder = new ArrayList<>();
			for (String strOrder : lstSort) {
				String[] strOrderSplit = strOrder.split(",");
				lstOrder.add(new Order(strOrderSplit[0], _ASC.equals(strOrderSplit[1])));
			}
			acceCriteria.setOrder(lstOrder);
		}

		List<Criterion> lstCrriterion = new ArrayList<>();
		for (String key : mapParam.keySet()) {
			if (_PAGE.equals(key) || _PAGESIZE.equals(key) || _SORT.equals(key) || StringUtils.isBlank(mapParam.getFirst(key))) continue;

			try {
				String resolveKey = getMapPropertyResolver().containsKey(key) ? getMapPropertyResolver().get(key) : key;
				Class<?> attrClass = getPath(root, resolveKey).getJavaType();
				if (ClassUtils.isAssignable(attrClass, Long.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Long.valueOf(mapParam.getFirst(key))));
				} else if (attrClass.isAssignableFrom(String.class)) {
					lstCrriterion.add(new Criterion(resolveKey, "%" + mapParam.getFirst(key).toLowerCase() + "%", false));
				} else if (ClassUtils.isAssignable(attrClass, Double.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Double.parseDouble(mapParam.getFirst(key))));
				} else if (ClassUtils.isAssignable(attrClass, Integer.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Integer.parseInt(mapParam.getFirst(key))));
				} else if (ClassUtils.isAssignable(attrClass, Date.class)) {
					try {
						lstCrriterion.add(
								new Criterion(resolveKey, DateUtils.parseDateStrictly(mapParam.getFirst(key), "dd/MM/yyyy", "yyyy/MM/dd")));
					} catch (ParseException e) {
						LOGGER.error("Date Parsing Error.", e);
					}
				} else {
					LOGGER.error("[" + attrClass.getName() + "] is not support in Acceval Base Criteria Search yet!");
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		acceCriteria.setCriterion(lstCrriterion);

		return acceCriteria;
	}

	protected Path<?> getPath(Root<?> root, String property) throws IllegalStateException, IllegalArgumentException {
		String[] splitProperty = property.split("[.]");
		Path<?> path = null;
		for (String pro : splitProperty) {
			if (path == null)
				path = root.get(pro);
			else
				path = path.get(pro);
		}

		return path;
	}

	protected Map<String, String> getMapPropertyResolver() {
		return new HashMap<>();
	}
}
