package com.acceval.core.repository;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.acceval.core.model.BaseEntity;
import com.acceval.core.model.BaseEntity.STATUS;
import com.acceval.core.model.BaseModel;
import com.acceval.core.model.TenantData;
import com.acceval.core.repository.Criterion.RestrictionType;
import com.acceval.core.security.PrincipalUtil;
import com.acceval.core.util.ClassUtil;
import com.acceval.core.util.TemplateUtil;

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

//	protected abstract EntityManager getEntityManager();

	protected abstract Class<?> getTargetClass();
	
	
	public Long generateId() {
		
		Class<?> baseEntity = this.checkParentBaseEntity(this.getTargetClass());
		String sequenceName = TemplateUtil.getEntityCode(baseEntity) + "_seq";
		
		String nextVal = "select nextval ('" + sequenceName + "')";
				
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
		
		BigInteger value = null;
		
		try {
			Query seqQuery = entityManager.createNativeQuery(nextVal);
			
			value = (BigInteger) seqQuery.getSingleResult();
			
		} finally {
			if (entityManager != null) {
				entityManager.close();
            }
		}
		
		long sequenceNo = value.longValue();
		
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String result = localDate.format(formatter);
		

		return Long.valueOf(result + StringUtils.leftPad(String.valueOf(sequenceNo), 7, '0'));
		
	}

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
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, List<DateRange> dateRanges) {

		Criteria criteria = this.getCriteriaByMapParam(mapParam, getTargetClass());
		for (DateRange dateRange : dateRanges) {

			LocalDateTime startDate = LocalDateTime.of(dateRange.getStartDate().getYear(), dateRange.getStartDate().getMonthValue(),
					dateRange.getStartDate().getDayOfMonth(), 0, 0, 0);

			if (dateRange.getEndDate() != null) {
				LocalDateTime endDate = LocalDateTime.of(dateRange.getEndDate().getYear(), dateRange.getEndDate().getMonthValue(),
						dateRange.getEndDate().getDayOfMonth(), 0, 0);
				endDate = endDate.plusDays(1);

				Criterion startCriterion = new Criterion(dateRange.getPropertyPath(), RestrictionType.GREATER_OR_EQUAL, startDate);
				startCriterion.setSearchValueDataType(Criterion.DATE);
				criteria.appendCriterion(startCriterion);

				Criterion endCriterion = new Criterion(dateRange.getPropertyPath(), RestrictionType.LESS, endDate);
				startCriterion.setSearchValueDataType(Criterion.DATE);
				criteria.appendCriterion(endCriterion);

			} else {
				Criterion startCriterion = new Criterion(dateRange.getPropertyPath(), RestrictionType.GREATER_OR_EQUAL, startDate);
				startCriterion.setSearchValueDataType(Criterion.DATE);
				criteria.appendCriterion(startCriterion);

			}
		}

		return this.queryByCriteria(criteria, getTargetClass());
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

		/** append company criteria for BaseModel */
		if (BaseModel.class.isAssignableFrom(getTargetClass()) && !TenantData.class.isAssignableFrom(getTargetClass())
				&& acceCriteria.getCriterion() != null) {
			boolean companyKeyFound =
					acceCriteria.getCriterion().stream().filter(c -> "companyId".equals(c.getPropertyName())).findFirst().isPresent();
			if (!companyKeyFound) {
				acceCriteria.appendCriterion(new Criterion("companyId", PrincipalUtil.getCompanyID()));
			}
		}

		/** criteria */
		for (Criterion criterion : acceCriteria.getCriterion()) {
			Predicate[] arrPre = buildPredicate(criterion, root, builder);
			if (arrPre != null && arrPre.length > 0) {
				for (Predicate predic : arrPre) {
					lstPredicate.add(predic);
				}
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
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
		
		try {
			Query query = entityManager.createQuery(criteria);
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
				total = entityManager.createQuery(cCount).getSingleResult().intValue();
			}
		

			List<T> result = query.getResultList();
	
			if (!acceCriteria.isFetchAll()) {
				queryResult = new QueryResult(total, result);
			} else {
				queryResult = new QueryResult(result.size(), result);
			}
			
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		return queryResult;
	}

	protected Predicate[] buildPredicate(Criterion criterion, Root<?> root, CriteriaBuilder builder) {

		if (criterion instanceof OrCriterion) {
			OrCriterion orCriterion = (OrCriterion) criterion;
			List<Predicate> lstPredicate = new ArrayList<>();
			for (Criterion c : orCriterion.getCriterions()) {
				Predicate[] arrPre = buildPredicate(c, root, builder);
				if (arrPre != null && arrPre.length > 0) {
					for (Predicate predic : arrPre) {
						lstPredicate.add(predic);
					}
				}
			}
			Predicate or = builder.or(lstPredicate.stream().toArray(Predicate[]::new));
			return new Predicate[] { or };
		}

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
			return new Predicate[] { builder.isNull(path) };
		} else if (Criterion.RestrictionType.IS_NOT_NULL.equals(restrictionType)) {
			return new Predicate[] { builder.isNotNull(path) };
		}
		// LIKE
		else if (!criterion.isExactSearch() && value instanceof String) {
			return new Predicate[] { builder.like(builder.lower(path), "%" + value.toString().toLowerCase() + "%") };
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
				return new Predicate[] { builder.equal(builder.function("year", Integer.class, path), year),
						builder.equal(builder.function("month", Integer.class, path), month),
						builder.equal(builder.function("day", Integer.class, path), day) };
			} else {
				return new Predicate[] { builder.equal(path, value) };
			}
		}
		// NOT EQUAL
		else if (Criterion.RestrictionType.NOT_EQUAL.equals(restrictionType)) {
			// handle Date
			if (ClassUtils.isAssignable(attrClass, Date.class)
					|| ClassUtils.isAssignable(attrClass, LocalDateTime.class)
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
				return new Predicate[] { builder.notEqual(builder.function("year", Integer.class, path), year),
						builder.notEqual(builder.function("month", Integer.class, path), month),
						builder.notEqual(builder.function("day", Integer.class, path), day) };
			} else {
				return new Predicate[] { builder.notEqual(path, value) };
			}
		} else if (Criterion.RestrictionType.IN.equals(restrictionType)) {
			return new Predicate[] { path.in(Arrays.asList(values)) };
		} else if (Criterion.RestrictionType.NOT_IN.equals(restrictionType)) {
			return new Predicate[] { builder.not(path.in(values)) };
		}
		// GREAT/LESS
		else if (Criterion.RestrictionType.GREATER.equals(restrictionType)) {
			if (value instanceof LocalDate) {
				return new Predicate[] { builder.greaterThan(path, (LocalDate) value) };
			} else if (value instanceof Date) {
				return new Predicate[] { builder.greaterThan(path, (Date) value) };
			} else if (value instanceof LocalDateTime) {
				return new Predicate[] { builder.greaterThan(path, (LocalDateTime) value) };
			} else if (value instanceof Number) {
				return new Predicate[] { builder.gt(path, (Number) value) };
			}
		} else if (Criterion.RestrictionType.GREATER_OR_EQUAL.equals(restrictionType)) {
			if (value instanceof LocalDate) {
				return new Predicate[] { builder.greaterThanOrEqualTo(path, (LocalDate) value) };
			} else if (value instanceof Date) {
				return new Predicate[] { builder.greaterThanOrEqualTo(path, (Date) value) };
			} else if (value instanceof LocalDateTime) {
				return new Predicate[] { builder.greaterThanOrEqualTo(path, (LocalDateTime) value) };
			} else if (value instanceof Number) {
				return new Predicate[] { builder.ge(path, (Number) value) };
			}
		} else if (Criterion.RestrictionType.LESS.equals(restrictionType)) {
			if (value instanceof LocalDate) {
				return new Predicate[] { builder.lessThan(path, (LocalDate) value) };
			} else if (value instanceof Date) {
				return new Predicate[] { builder.lessThan(path, (Date) value) };
			} else if (value instanceof LocalDateTime) {
				return new Predicate[] { builder.lessThan(path, (LocalDateTime) value) };
			} else if (value instanceof Number) {
				return new Predicate[] { builder.lt(path, (Number) value) };
			}
		} else if (Criterion.RestrictionType.LESS_OR_EQUAL.equals(restrictionType)) {
			if (value instanceof LocalDate) {
				return new Predicate[] { builder.lessThanOrEqualTo(path, (LocalDate) value) };
			} else if (value instanceof Date) {
				return new Predicate[] { builder.lessThanOrEqualTo(path, (Date) value) };
			} else if (value instanceof LocalDateTime) {
				return new Predicate[] { builder.lessThanOrEqualTo(path, (LocalDateTime) value) };
			} else if (value instanceof Number) {
				return new Predicate[] { builder.le(path, (Number) value) };
			}
		}
		// unknown
		else {
			LOGGER.error("Criterion for [" + criterion.getPropertyName() + "] failed!");
		}

		return null;
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
		boolean isFetchAll = mapParam.get(_FETCHALL) != null ? Boolean.parseBoolean(mapParam.getFirst(_FETCHALL)) : (pageSize <= 0);
		List<String> lstSort = mapParam.get(_SORT);

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

				if (mapParam.getFirst(key) == null || mapParam.getFirst(key).equalsIgnoreCase("isNull")) {
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

		// base entity
		if (BaseEntity.class.isAssignableFrom(targetClass)) {
			acceCriteria.appendCriterion(new Criterion("recordStatus", STATUS.ACTIVE, true));
		}
		if (BaseModel.class.isAssignableFrom(targetClass) && !(TenantData.class.isAssignableFrom(targetClass))) {
			Long companyId = PrincipalUtil.getCompanyID();
			if (companyId != null) {
				acceCriteria.appendCriterion(new Criterion("companyId", companyId));
			}
		}

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

		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
		Object result = null;
		try {
			Query query = prepareSelectQuery(entityManager, status, idField, id);
			result = query.getSingleResult();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		
		return (T) result;
	}

	@Override
	public List<T> findAllByRecordStatus(STATUS status) {
		
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
		
		List<T> result = null; 
		try {
			Query query = prepareSelectQuery(entityManager, status, null, null);
			result = query.getResultList();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return result;
	}

	private Query prepareSelectQuery(EntityManager entityManager, STATUS status, String idField, Long id) {
		
		String selectQuery = "SELECT e FROM " + getTargetClass().getSimpleName() + " e WHERE e.recordStatus = :status";
		if (StringUtils.isNotEmpty(idField) && id != null) {
			selectQuery += " AND e." + idField + " = :id";
		}
		
		Query query = entityManager.createQuery(selectQuery, getTargetClass());
		query.setParameter("status", status == null ? STATUS.ACTIVE : status);
		if (id != null) query.setParameter("id", id);

		return query;
	}

//	@Transactional
//	@Modifying
	@Override
	public void softDelete(T entity) {
		Long id = null;
		String idField = null;
		List<Field> existingFields = ClassUtil.getDeclaredFields(entity.getClass());
		Class<?> parentEntity = checkParentBaseEntity(entity.getClass());

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

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
		
//		@SuppressWarnings("null")
		
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
		
		try {
			
			EntityTransaction tx = entityManager.getTransaction();
			tx.begin();
			String updateSql = "UPDATE " + (parentEntity != null ? parentEntity.getSimpleName() : entity.getClass().getSimpleName())
					+ " e SET e.recordStatus = 'ARCHIVE', e.dateArchived ='" + LocalDateTime.now().format(formatter) + "' WHERE e." + idField + " = " + id;
			Query query = entityManager.createQuery(updateSql);
	//		this.getEntityManager().joinTransaction();
			query.executeUpdate();
			
			tx.commit();
			
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}
	
	private Class<?> checkParentBaseEntity(Class<?> clazz){
		
		if (clazz == null) {
			return null;
		}
		
		if (clazz.getSuperclass().equals(BaseEntity.class)) {
			if (clazz.isAnnotationPresent(Entity.class)) {
				return clazz;
			} else {
				return null;
			}			
		} else {
			return checkParentBaseEntity(clazz.getSuperclass());
		}
	}

//	@Transactional
//	@Modifying
	@Override
	public void softDelete(List<T> entities) {
		for (T entity : entities) {
			softDelete(entity);
		}
	}
}
