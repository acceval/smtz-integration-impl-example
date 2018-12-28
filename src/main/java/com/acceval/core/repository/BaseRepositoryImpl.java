package com.acceval.core.repository;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.acceval.core.model.AuthUser;
import com.acceval.core.model.BaseEntity;
import com.acceval.core.model.BaseEntity.STATUS;
import com.acceval.core.model.BaseModel;
import com.acceval.core.repository.Criterion.RestrictionType;

public abstract class BaseRepositoryImpl<T> implements BaseRepository<T> {

	public static String _PAGE = "_page";
	public static String _PAGESIZE = "_pageSize";
	public static String _SORT = "_sort";
	public static String _FETCHALL = "_fetchAll";

	public static String _ASC = "asc";
	public static String _DESC = "desc";

	public static String[] STD_DATEFORMAT = new String[] { "yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy", "yyyy/MM/dd" };

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseRepositoryImpl.class);

	protected abstract EntityManagerFactory getEntityManagerFactory();

	protected abstract EntityManager getEntityManager();

	protected abstract Class<?> getTargetClass();

	public List<T> findAll() {

		MultiValueMap<String, String> mapParam = new LinkedMultiValueMap<>();
		QueryResult<T> queryResult = this.queryByMapParam(mapParam);
		return queryResult.getResults();
	}

	@Override
	public boolean isExists(Object obj) {

		Field[] fields = obj.getClass().getDeclaredFields();
		MultiValueMap<String, String> mapParam = new LinkedMultiValueMap<String, String>();

		for (Field field : fields) {

			if (field.isAnnotationPresent(UniqueKey.class)) {

				UniqueKey uniqueKey = field.getAnnotation(UniqueKey.class);

				if (uniqueKey.attr().length() > 0) {

					Field attrField = null;

					if (uniqueKey.attr().contains(".")) {

						StringTokenizer tokenizer = new StringTokenizer(uniqueKey.attr(), ".");

						Object childObj = null;

						while (tokenizer.hasMoreTokens()) {

							String fieldName = tokenizer.nextToken();
							try {

								if (childObj == null) {
									attrField = obj.getClass().getDeclaredField(fieldName);

									attrField.setAccessible(true);

									childObj = attrField.get(obj);
								} else {
									attrField = childObj.getClass().getDeclaredField(fieldName);

									attrField.setAccessible(true);

									childObj = attrField.get(childObj);
								}

							} catch (NoSuchFieldException | SecurityException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException | IllegalAccessException e) {
								e.printStackTrace();
							}
						}

						if (childObj instanceof Date) {
							mapParam.set(uniqueKey.attr(), new SimpleDateFormat("dd/MM/yyyy").format(childObj));
						} else {
							mapParam.set(uniqueKey.attr(), String.valueOf(childObj));
						}

					} else {

						attrField = field;

						attrField.setAccessible(true);

						try {

							Object value = attrField.get(obj);
							if (value instanceof Date) {
								mapParam.set(uniqueKey.attr(), new SimpleDateFormat("dd/MM/yyyy").format(value));
							} else {
								mapParam.set(uniqueKey.attr(), String.valueOf(value));
							}

						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				} else {

					field.setAccessible(true);
					try {
						Object value = field.get(obj);
						if (value instanceof Date) {
							mapParam.set(field.getName(), new SimpleDateFormat("dd/MM/yyyy").format(value));
						} else {
							mapParam.set(field.getName(), String.valueOf(value));
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}

		QueryResult<T> result = this.queryByMapParam(mapParam);

		if (result.getTotal() > 0) {

			Object existingObj = result.getResults().get(0);
			Field[] existingFields = existingObj.getClass().getDeclaredFields();

			for (Field field : existingFields) {
				if (field.isAnnotationPresent(Id.class)) {
					try {
						field.setAccessible(true);
						Object value = field.get(existingObj);
						Field idField = obj.getClass().getDeclaredField(field.getName());
						idField.setAccessible(true);
						idField.set(obj, value);
						break;
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public QueryResult<T> queryByCriteria(Criteria acceCriteria) {
		return queryByCriteria(acceCriteria, getTargetClass());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public QueryResult queryByCriteria(Criteria acceCriteria, Class<?> targetClass) {

		CriteriaBuilder builder = getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<?> criteria = builder.createQuery(targetClass);

		Root<?> root = criteria.from(targetClass);
		criteria.select((Selection) root);
		List<Predicate> lstPredicate = new ArrayList<>();
		Map<String, Join<?, ?>> mapDefinedPath = new HashMap<>();

		/** criteria */
		for (Criterion criterion : acceCriteria.getCriterion()) {
			String property = getMapPropertyResolver().containsKey(criterion.getPropertyName())
					? getMapPropertyResolver().get(criterion.getPropertyName())
					: criterion.getPropertyName();
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
					int year = 0;
					int month = 0;
					int day = 0;
					if (value instanceof String) {
						try {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(DateUtils.parseDateStrictly((String) value, STD_DATEFORMAT));
							year = calendar.get(Calendar.YEAR);
							month = calendar.get(Calendar.MONTH) + 1;
							day = calendar.get(Calendar.DAY_OF_MONTH);
						} catch (ParseException e) {
							LOGGER.error(e.getMessage(), e);
						}
					} else if (value instanceof Date) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime((Date) value);
						year = calendar.get(Calendar.YEAR);
						month = calendar.get(Calendar.MONTH) + 1;
						day = calendar.get(Calendar.DAY_OF_MONTH);
					} else if (value instanceof LocalDate) {
						LocalDate vLocalDate = (LocalDate) value;
						year = vLocalDate.getYear();
						month = vLocalDate.getMonthValue();
						day = vLocalDate.getDayOfMonth();
					} else if (value instanceof LocalDateTime) {
						LocalDateTime vLocalDate = (LocalDateTime) value;
						year = vLocalDate.getYear();
						month = vLocalDate.getMonthValue();
						day = vLocalDate.getDayOfMonth();
					}
					lstPredicate.add(builder.equal(builder.function("year", Integer.class, path), year));
					lstPredicate.add(builder.equal(builder.function("month", Integer.class, path), month));
					lstPredicate.add(builder.equal(builder.function("day", Integer.class, path), day));
				} else {
					lstPredicate.add(builder.equal(path, value));
				}
			} else if (Criterion.RestrictionType.IN.equals(restrictionType)) {

				lstPredicate.add(path.in(Arrays.asList(values)));

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
			// unknown
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
				Path orderPath = getPath(root, order.getProperty(), mapDefinedPath);
				if (order.getIsAscending()) {
					lstOrder.add(builder.asc(orderPath));
				} else {
					lstOrder.add(builder.desc(orderPath));
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

		List<T> result = query.getResultList();
		
		if (!acceCriteria.isFetchAll()) {
			queryResult = new QueryResult(total, result);
		} else {
			queryResult = new QueryResult(result.size(), result);
		}

		return queryResult;
	}

	@Override
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam) {
		return queryByMapParam(mapParam, getTargetClass());
	}

	/**
	 * convert map to criteria and inquiry, normally use for Search Form, with
	 * pagination
	 */
	@Override
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass) {
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
		boolean isFetchAll = mapParam.get(_FETCHALL) != null ? Boolean.parseBoolean(mapParam.getFirst(_FETCHALL)) : false;
		List<String> lstSort = mapParam.get(_SORT);

		try {
			if (targetClass.newInstance() instanceof BaseEntity) {
				mapParam.put("recordStatus", Arrays.asList(STATUS.ACTIVE.name()));
			}
			if (targetClass.newInstance() instanceof BaseModel) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth != null) {
					Object principal = auth.getPrincipal();
					if (principal != null && principal instanceof AuthUser) {
						AuthUser authUser = (AuthUser) principal;
						if (authUser.getCompanyId() != null) {
							String[] values = { String.valueOf(authUser.getCompanyId()) };
							mapParam.put("companyId", Arrays.asList(values));
						}
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Criteria acceCriteria = new Criteria();
		acceCriteria.setRequestedPage(page);
		acceCriteria.setPageSize(pageSize);
		acceCriteria.setFetchAll(isFetchAll);

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

			if (_PAGE.equals(key) || _PAGESIZE.equals(key) || _SORT.equals(key) || _FETCHALL.equals(key) 
					|| (mapParam.getFirst(key) != null && StringUtils.trim(mapParam.getFirst(key)).length() == 0)) {
				continue;
			}

			try {
				String resolveKey = getMapPropertyResolver().containsKey(key) ? getMapPropertyResolver().get(key) : key;
				Class<?> attrClass = getPath(root, resolveKey).getJavaType();

				if (mapParam.getFirst(key) == null) {
					lstCrriterion.add(new Criterion(resolveKey, RestrictionType.IS_NULL, mapParam.getFirst(key)));
				} else if (ClassUtils.isAssignable(attrClass, Long.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Long.valueOf(mapParam.getFirst(key))));
				} else if (attrClass.isAssignableFrom(String.class)) {
					List<String> searchValue = mapParam.get(key);

					if (searchValue.size() > 1) {
						Object searchValues = searchValue.toArray();
						lstCrriterion.add(new Criterion(resolveKey, searchValues));
					} else {
						lstCrriterion.add(new Criterion(resolveKey, "%" + mapParam.getFirst(key).toLowerCase() + "%", false));
					}
				} else if (ClassUtils.isAssignable(attrClass, Double.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Double.parseDouble(mapParam.getFirst(key))));
				} else if (ClassUtils.isAssignable(attrClass, Float.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Float.parseFloat(mapParam.getFirst(key))));
				} else if (ClassUtils.isAssignable(attrClass, Integer.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Integer.parseInt(mapParam.getFirst(key))));
				} else if (ClassUtils.isAssignable(attrClass, Date.class) || ClassUtils.isAssignable(attrClass, LocalDate.class)
						|| ClassUtils.isAssignable(attrClass, LocalDateTime.class)) {
					try {
						lstCrriterion.add(new Criterion(resolveKey, DateUtils.parseDateStrictly(mapParam.getFirst(key), STD_DATEFORMAT)));
					} catch (ParseException e) {
						LOGGER.error("Date Parsing Error.", e);
					}
				} else if (ClassUtils.isAssignable(attrClass, Enum.class)) {
					lstCrriterion
							.add(new Criterion(resolveKey, Enum.valueOf(attrClass.asSubclass(Enum.class), mapParam.getFirst(key)), true));
				} else {
					LOGGER.error("[" + attrClass.getName() + "] is not support in Acceval Base Criteria Search yet!");
				}
			} catch (Exception e) {
				if (e.getMessage().indexOf("Unable to locate Attribute  with the the given name") == -1) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		acceCriteria.setCriterion(lstCrriterion);

		return acceCriteria;
	}

	protected Path<?> getPath(Root<?> root, String property) throws IllegalStateException, IllegalArgumentException {
		String[] splitProperty = property.split("[.]");
		Path<?> path = null;
		for (String pro : splitProperty) {
			if (path == null) {
				path = root.get(pro);
			} else {
				path = path.get(pro);
			}
		}
		return path;
	}

	/**
	 * by calling this, the JPA should auto join all the tables in query
	 */
	protected Path<?> getPath(Root<?> root, String property, Map<String, Join<?, ?>> mapDefinedPath)
			throws IllegalStateException, IllegalArgumentException {
		if (property.indexOf(".") == -1) {
			return getPath(root, property);
		}

		String[] splitProperty = property.split("[.]");
		Join<?, ?> join = null;
		StringBuilder reappendProp = new StringBuilder();
		for (int i = 0; i < splitProperty.length; i++) {
			String pro = splitProperty[i];
			if (i == 0) {
				reappendProp.append(pro);
				Join<?, ?> defPath = mapDefinedPath.get(pro);
				join = defPath == null ? root.join(pro, JoinType.LEFT) : defPath;
				mapDefinedPath.put(pro, join);
			} else if (i != splitProperty.length - 1) {
				reappendProp.append("." + pro);
				Join<?, ?> defPath = mapDefinedPath.get(reappendProp.toString());
				join = defPath == null ? join.join(pro, JoinType.LEFT) : defPath;
				mapDefinedPath.put(reappendProp.toString(), join);
			} else {
				return join.get(pro);
			}
		}

		return join;
	}

	/**
	 * provide Map to translate short property to full property path eg.
	 * customerID => customer.customerID
	 */
	protected Map<String, String> getMapPropertyResolver() {
		return new HashMap<>();
	}

	@Override
	public T findOneByRecordStatus(STATUS status, Long id) {
		String idField = null;
		Class<?> entity = getTargetClass();

		for (Field field : entity.getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				idField = field.getName();
				break;
			}
		}

		Query query = prepareSelectQuery(status, idField, id);
		return (T) query.getSingleResult();
	}

	@Override
	public List<T> findAllByRecordStatus(STATUS status) {
		Query query = prepareSelectQuery(status, null, null);
		return query.getResultList();
	}

	private Query prepareSelectQuery(STATUS status, String idField, Long id) {
		String selectQuery = "SELECT e FROM " + getTargetClass().getSimpleName() + " e WHERE e.recordStatus = :status";
		if (StringUtils.isNotEmpty(idField) && id != null) {
			selectQuery += " AND e." + idField + " = :id";
		}
		Query query = getEntityManager().createQuery(selectQuery, getTargetClass());
		query.setParameter("status", status == null ? STATUS.ACTIVE : status);
		if (id != null) query.setParameter("id", id);

		return query;
	}

	@Transactional
	@Modifying
	@Override
	public void softDelete(T entity) {
		Long id = null;
		String idField = null;
		Field[] existingFields = entity.getClass().getDeclaredFields();

		for (Field field : existingFields) {
			if (field.isAnnotationPresent(Id.class)) {
				try {
					field.setAccessible(true);
					id = (Long) field.get(entity);
					idField = field.getName();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		String updateSql =
				"UPDATE " + entity.getClass().getSimpleName() + " e SET e.recordStatus = 'ARCHIVE' WHERE e." + idField + " = " + id;
		Query query = getEntityManager().createQuery(updateSql);
		int updatedCount = query.executeUpdate();
	}

	@Transactional
	@Modifying
	@Override
	public void softDelete(List<T> entities) {
		for (T entity : entities) {
			softDelete(entity);
		}
	}
}
