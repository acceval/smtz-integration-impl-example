package com.acceval.core.repository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.util.MultiValueMap;

import com.acceval.core.model.BaseEntity;
import com.acceval.core.model.BaseEntity.STATUS;
import com.acceval.core.model.company.BaseCompanyEntity;
import com.acceval.core.model.company.BaseCompanyModel;
import com.acceval.core.repository.Criterion.RestrictionType;
import com.acceval.core.security.PrincipalUtil;
import com.acceval.core.util.ClassUtil;
import com.acceval.core.util.DateUtil;
import com.acceval.core.util.TimeZoneUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public abstract class BaseMongoRepositoryImpl<T> implements BaseMongoRepository<T> {

	public static String _PAGE = "_page";
	public static String _PAGESIZE = "_pageSize";
	public static String _SORT = "_sort";
	public static String _FETCHALL = "_fetchAll";
	public static String _DISPLAYFIELD = "displayFields";

	public static String _ASC = "asc";
	public static String _DESC = "desc";

	public static String[] STD_DATEFORMAT = new String[] { "yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss",
			"dd-MM-yyyy HH:mm:ss", "dd/MM/yyyy HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseMongoRepositoryImpl.class);

	@Autowired
	protected MongoTemplate mongoTemplate;

	protected abstract Class<?> getTargetClass();

	@Override
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam) {

		return queryByMapParam(mapParam, this.getTargetClass());
	}

	
	/**
	 * convert map to criteria and inquiry, normally use for Search Form, with
	 * pagination
	 */
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass) {

		Criteria criteria = this.getCriteriaByMapParam(mapParam, targetClass);
		QueryResult<T> queryResult = this.queryByCriteria(criteria, targetClass);
		ClassUtil.slimDownQueryResult(queryResult, mapParam);

		return queryResult;
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
	public Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass) {
		return getCriteriaByMapParam(mapParam, targetClass, false);
	}

	/**
	 * convert map to criteria
	 */
	public Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass, boolean isOrCriteria) {

		int page = mapParam.get(_PAGE) != null ? Integer.parseInt(mapParam.getFirst(_PAGE)) : 0;
		int pageSize = mapParam.get(_PAGESIZE) != null ? Integer.parseInt(mapParam.getFirst(_PAGESIZE)) : 0;
		boolean isFetchAll = mapParam.get(_FETCHALL) != null ? Boolean.parseBoolean(mapParam.getFirst(_FETCHALL)) : false;
		List<String> lstSort = mapParam.get(_SORT);

		Criteria acceCriteria = new Criteria();
		acceCriteria.setRequestedPage(page);
		acceCriteria.setPageSize(pageSize);
		acceCriteria.setFetchAll(isFetchAll);

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

			if (_PAGE.equals(key) || _PAGESIZE.equals(key) || _SORT.equals(key) || _FETCHALL.equals(key) || _DISPLAYFIELD.equals(key)
					|| (mapParam.getFirst(key) != null && StringUtils.trim(mapParam.getFirst(key)).length() == 0)) {
				continue;
			}

			if ("_class".equals(key)) {
				lstCrriterion.add(new Criterion(key, mapParam.getFirst(key)));
				continue;
			}

			try {
				String resolveKey = this.getMapPropertyResolver().containsKey(key) ? getMapPropertyResolver().get(key) : key;
				Field field = this.getField(targetClass, resolveKey);
				if (field == null) {
					continue;
				}
				Class<?> attrClass = field.getType();

				if (mapParam.getFirst(key) == null) {
					lstCrriterion.add(new Criterion(resolveKey, RestrictionType.IS_NULL, mapParam.getFirst(key)));
				} else if (ClassUtils.isAssignable(attrClass, Long.class, true)) {
					List<String> searchValue = mapParam.get(key);
					if (searchValue.size() > 1) {
						Object[] searchValues = searchValue.toArray();
						List<Long> lstLong = new ArrayList<>();
						for (Object objValue : searchValues) {
							if (objValue != null) {
								String strValue = objValue.toString();
								if (StringUtils.isNotBlank(strValue)) {
									try {
										lstLong.add(Long.valueOf(strValue));
									} catch (Throwable t) {
									}
								}
							}
						}
						if (!lstLong.isEmpty()) {
							lstCrriterion.add(new Criterion(resolveKey, lstLong.toArray(new Long[lstLong.size()])));
						}
					} else {
						lstCrriterion.add(new Criterion(resolveKey, Long.valueOf(mapParam.getFirst(key))));
					}
				} else if (attrClass.isAssignableFrom(String.class) || attrClass.isAssignableFrom(String[].class)) {
					List<String> searchValue = mapParam.get(key);

					if (searchValue.size() > 1) {
						Object searchValues = searchValue.toArray();
						lstCrriterion.add(new Criterion(resolveKey, searchValues));
					} else {
						lstCrriterion.add(new Criterion(resolveKey, ".*" + mapParam.getFirst(key).toLowerCase() + ".*", false));
					}
				} else if (ClassUtils.isAssignable(attrClass, Double.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Double.parseDouble(mapParam.getFirst(key))));
				} else if (ClassUtils.isAssignable(attrClass, Boolean.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Boolean.parseBoolean(mapParam.getFirst(key))));
				} else if (ClassUtils.isAssignable(attrClass, Float.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Float.parseFloat(mapParam.getFirst(key))));
				} else if (ClassUtils.isAssignable(attrClass, Integer.class, true)) {
					lstCrriterion.add(new Criterion(resolveKey, Integer.parseInt(mapParam.getFirst(key))));
				} else if (ClassUtils.isAssignable(attrClass, Date.class) || ClassUtils.isAssignable(attrClass, LocalDate.class)
						|| ClassUtils.isAssignable(attrClass, LocalDateTime.class)) {
					try {
						List<String> strDates = mapParam.get(key);
						for (String strDate : strDates) {
							RestrictionType resType = RestrictionType.EQUAL;
							boolean isSpecial = false;
							if (strDate.contains(Criterion.SIGN_DELIMITER)) {
								String[] splited = strDate.split(Criterion.SIGN_DELIMITER);
								if (splited.length > 1) {
									strDate = splited[1].trim();
									String oper = splited[0].trim();
									resType = Criterion.RestrictionType.getRestrictionTypeBySign(oper, resType);
									if (ClassUtils.isAssignable(attrClass, LocalDateTime.class)) {
										lstCrriterion.add(new Criterion(resolveKey, resType, DateUtil.parseToLocalDateTime(strDate)));
										isSpecial = true;
									}
								}
							}
							if (!isSpecial) {
								lstCrriterion.add(new Criterion(resolveKey, resType, DateUtils.parseDateStrictly(strDate, STD_DATEFORMAT)));
							}
						}
					} catch (ParseException e) {
						LOGGER.error("Date Parsing Error.", e);
					}
				} else if (ClassUtils.isAssignable(attrClass, Enum.class)) {
					List<String> searchValue = mapParam.get(key);
					if (searchValue.size() > 1) {
						Object searchValues = searchValue.toArray();
						lstCrriterion.add(new Criterion(resolveKey, searchValues));
					} else {
						lstCrriterion.add(
								new Criterion(resolveKey, Enum.valueOf(attrClass.asSubclass(Enum.class), mapParam.getFirst(key)), true));
						if (resolveKey.equals("state")) {
						lstCrriterion.add(
								new Criterion("salesDocNumber", "CTN-0000000034", true));
						}
					}
				} else if (ClassUtils.isAssignable(attrClass, Collection.class)) {
					// seem Collection work in mongo
					if (field.toGenericString().contains("<java.lang.Long>")) {
						try {
							lstCrriterion.add(new Criterion(resolveKey, Long.valueOf(mapParam.getFirst(key))));
						} catch (Exception e) {
						}
					} else {
						lstCrriterion.add(new Criterion(resolveKey, mapParam.getFirst(key)));
					}
				} else {
					LOGGER.error("[" + attrClass.getName() + "] is not support in Acceval Base Criteria Search yet!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (e.getMessage() != null && e.getMessage().indexOf("Unable to locate Attribute  with the the given name") == -1) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		acceCriteria.setCriterion(lstCrriterion);

		// base entity
		if (!isOrCriteria && (!mapParam.containsKey("recordStatus") || StringUtils.isBlank(mapParam.getFirst("recordStatus")))
				&& (BaseEntity.class.isAssignableFrom(targetClass) || BaseCompanyEntity.class.isAssignableFrom(targetClass))) {
			acceCriteria.appendCriterion(new Criterion("recordStatus", STATUS.ACTIVE, true));
		}
		if (!isOrCriteria && (BaseCompanyModel.class.isAssignableFrom(targetClass) || BaseCompanyEntity.class.isAssignableFrom(targetClass))
				&& !mapParam.containsKey("companyId")) {
			Long companyId = PrincipalUtil.getCompanyID();
			if (companyId != null) {
				acceCriteria.appendCriterion(new Criterion("companyId", companyId));
			}
		}
		
		for (String key: mapParam.keySet()) {
			int index = key.indexOf("_");
			if (index != -1) {
				String nestedKey = key.substring(0, index);
				String propertyKey = key.substring(index + 1);
				for (Criterion criterion: acceCriteria.getCriterion()) {
					if (criterion.getPropertyName().equals(propertyKey)) {
						criterion.setNestedConditionKey(nestedKey);
					}
				}
			}
		}

		return acceCriteria;
	}


	@Override
	public QueryResult<T> queryByCriteria(Criteria acceCriteria) {
		return queryByCriteria(acceCriteria, this.getTargetClass());
	}	

	@Override
	public QueryResult<T> queryByCriteria(Criteria acceCriteria, Class<?> targetClass) {

		/** start query */
		QueryResult<T> queryResult = null;
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		List<org.springframework.data.mongodb.core.query.Criteria> criterias =
				(List<org.springframework.data.mongodb.core.query.Criteria>) this.getMongoCriterias(acceCriteria)[0];
		for (org.springframework.data.mongodb.core.query.Criteria criteria : criterias) {
			query.addCriteria(criteria);
		}

		if (acceCriteria.getOrder() != null) {
			for (Order order : acceCriteria.getOrder()) {
				if (order.getIsAscending()) {
					query.with(new Sort(Sort.Direction.ASC, order.getProperty()));
				} else {
					query.with(new Sort(Sort.Direction.DESC, order.getProperty()));
				}
			}
		}

		long total = 0;
		if (!acceCriteria.isFetchAll()) {

			int page = acceCriteria.getRequestedPage();
			int pageSize = acceCriteria.getPageSize();
			total = mongoTemplate.count(query, this.getTargetClass());

			final Pageable pageableRequest = new PageRequest(page, pageSize);
			query.with(pageableRequest);
		}

		List<T> result = (List<T>) mongoTemplate.find(query, this.getTargetClass());

		if (!acceCriteria.isFetchAll()) {
			queryResult = new QueryResult<T>(Math.toIntExact(total), result);
		} else {
			queryResult = new QueryResult<T>(result.size(), result);
		}

		return queryResult;
	}

	//	Remove pagination
	@Override
	public QueryResult<T> queryByCriteriaWithoutPagination(Criteria acceCriteria, Class<?> targetClass) {

		/** start query */
		QueryResult<T> queryResult = null;
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		List<org.springframework.data.mongodb.core.query.Criteria> criterias =
				(List<org.springframework.data.mongodb.core.query.Criteria>) this.getMongoCriterias(acceCriteria)[0];
		for (org.springframework.data.mongodb.core.query.Criteria criteria : criterias) {
			query.addCriteria(criteria);
		}

		if (acceCriteria.getOrder() != null) {
			for (Order order : acceCriteria.getOrder()) {
				if (order.getIsAscending()) {
					query.with(new Sort(Sort.Direction.ASC, order.getProperty()));
				} else {
					query.with(new Sort(Sort.Direction.DESC, order.getProperty()));
				}
			}
		}

		long total = 0;
		if (!acceCriteria.isFetchAll()) {

			int page = acceCriteria.getRequestedPage();
			int pageSize = acceCriteria.getPageSize();
			total = mongoTemplate.count(query, this.getTargetClass());

		}

		List<T> result = (List<T>) mongoTemplate.find(query, this.getTargetClass());

		if (!acceCriteria.isFetchAll()) {
			queryResult = new QueryResult<T>(Math.toIntExact(total), result);
		} else {
			queryResult = new QueryResult<T>(result.size(), result);
		}

		return queryResult;
	}

	@Override
	public List<T> aggregateByCriteria(Criteria acceCriteria) {
		List<AggregationOperation> lstOperation = (List<AggregationOperation>) this.getMongoCriterias(acceCriteria)[1];
		BasicDBObject dbProjection = null;
		for (Projection proj : acceCriteria.getProjections()) {
			if (StringUtils.isBlank(proj.getProperty())) continue;
			if (dbProjection == null) {
				dbProjection = new BasicDBObject(proj.getProperty(), 1);
			} else {
				dbProjection.append(proj.getProperty(), 1);
			}
		}
		if (dbProjection != null) {
			lstOperation.add(new BasicAggregationOperation(new BasicDBObject("$project", dbProjection)));
		}
		AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
		AggregationResults<T> results = (AggregationResults<T>) this.mongoTemplate
				.aggregate(Aggregation.newAggregation(lstOperation).withOptions(options), this.getTargetClass(), this.getTargetClass());
		return results.getMappedResults();
	}

	/**
	 * [0] mongo criteria, use for find()
	 * [1] mongo match, use for aggregate()
	 */
	@Override
	public Object[] getMongoCriterias(Criteria acceCriteria) {
		return getMongoCriterias(acceCriteria, getTargetClass());
	}

	/**
	 * [0] mongo criteria, use for find()
	 * [1] mongo match, use for aggregate()
	 */
	@Override
	public Object[] getMongoCriterias(Criteria acceCriteria, Class<?> targetClass) {

		/** append company criteria for BaseModel */
		if (acceCriteria.isAutoAppendCompany() && BaseCompanyModel.class.isAssignableFrom(targetClass)
				&& acceCriteria.getCriterion() != null) {
			boolean companyKeyFound =
					acceCriteria.getCriterion().stream().filter(c -> "companyId".equals(c.getPropertyName())).findFirst().isPresent();
			if (!companyKeyFound) {
				acceCriteria.appendCriterion(new Criterion("companyId", PrincipalUtil.getCompanyID()));
			}
		}

		List<org.springframework.data.mongodb.core.query.Criteria> mongoCriterias =
				new ArrayList<org.springframework.data.mongodb.core.query.Criteria>(); // [0]
		List<AggregationOperation> lstOperation = new ArrayList<>(); // [1]

		/** criteria */
		Map<String, org.springframework.data.mongodb.core.query.Criteria> mapCriteria = new HashMap<>();
		for (Criterion criterion : acceCriteria.getCriterion()) {
			String property = getMapPropertyResolver().containsKey(criterion.getPropertyName())
					? getMapPropertyResolver().get(criterion.getPropertyName())
					: criterion.getPropertyName();

			// or Criteria
			if (criterion instanceof OrCriterion && ((OrCriterion) criterion).getCriterions() != null) {
				List<org.springframework.data.mongodb.core.query.Criteria> lstOrCri = new ArrayList<>();//[0]
				//				Object[] orObj = new Object[((OrCriterion) criterion).getCriterions().size()];
				List<Object> lstOrObj = new ArrayList<>();
				int i = 0;
				BasicDBObject orDbo = new BasicDBObject("$or", lstOrObj);//[1]
				for (Criterion orC : ((OrCriterion) criterion).getCriterions()) {
					Object[] convertions = criterionToMongoCriteria(orC);

					/** for [0] */
					org.springframework.data.mongodb.core.query.Criteria criteria =
							(org.springframework.data.mongodb.core.query.Criteria) convertions[0];
					lstOrCri.add(criteria);

					/** for [1] */
					if (convertions.length == 2 && convertions[1] != null) {
						//						orObj[i++] = convertions[1];
						lstOrObj.add(convertions[1]);
					} else {
						//						orObj[i++] = criteria.getCriteriaObject();
						lstOrObj.add(criteria.getCriteriaObject());
					}
				}
				org.springframework.data.mongodb.core.query.Criteria mongoOrCriteria =
						new org.springframework.data.mongodb.core.query.Criteria()
								.orOperator(lstOrCri.toArray(new org.springframework.data.mongodb.core.query.Criteria[lstOrCri.size()]));
				mongoCriterias.add(mongoOrCriteria);//[0]

				lstOperation.add(new BasicAggregationOperation(new Document("$match", orDbo)));//[1]
				continue;
			}

			if ("_class".equals(property)) {
				String className = (String) criterion.getSearchValue();
				mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).is(className));
				lstOperation.add(Aggregation.match(org.springframework.data.mongodb.core.query.Criteria.where("_class").is(className)));
				continue;
			}
			Object[] convertions = criterionToMongoCriteria(criterion, targetClass);
			if (convertions == null) continue;

			/** for [0] */
			org.springframework.data.mongodb.core.query.Criteria criteria =
					(org.springframework.data.mongodb.core.query.Criteria) convertions[0];
			org.springframework.data.mongodb.core.query.Criteria mongoCri = mapCriteria.get(property);
			if (mongoCri != null) {
				mongoCri.andOperator(criteria);
			} else {
				mongoCriterias.add(criteria);
				mapCriteria.put(property, criteria);
			}

			/** for [1] */
			if (convertions.length == 2 && convertions[1] != null) {
				lstOperation.add(new BasicAggregationOperation(new Document("$match", (DBObject) convertions[1])));
			} else {
				lstOperation.add(Aggregation.match(criteria));
			}
		}

		return new Object[] { mongoCriterias, lstOperation };
	}

	/**
	 * [0] mongo criteria
	 * [1] DBObject for date special handling
	 */
	private Object[] criterionToMongoCriteria(Criterion criterion) {
		return criterionToMongoCriteria(criterion, getTargetClass());
	}

	/**
	 * [0] mongo criteria
	 * [1] DBObject for date special handling
	 */
	private Object[] criterionToMongoCriteria(Criterion criterion, Class<?> targetClass) {
		String property = getMapPropertyResolver().containsKey(criterion.getPropertyName())
				? getMapPropertyResolver().get(criterion.getPropertyName())
				: criterion.getPropertyName();
		property = StringUtils.isNotBlank(criterion.getAlternatePropertyName()) ? criterion.getAlternatePropertyName() : property;

		Class<?> attrClass = null;
		try {
			Field field = this.getField(targetClass, property);

			if (field == null) {
				return null;
			}

			attrClass = field.getType();
		} catch (NoSuchFieldException ex) {
			ex.printStackTrace();
			return null;
		}
		return criterionToMongoCriteriaByField(criterion, attrClass);
	}

	@Override
	public Object[] criterionToMongoCriteriaByField(Criterion criterion, Class<?> attrClass) {
		String property = getMapPropertyResolver().containsKey(criterion.getPropertyName())
				? getMapPropertyResolver().get(criterion.getPropertyName())
				: criterion.getPropertyName();

		Object value = criterion.getSearchValue();
		Object[] values = criterion.getSearchValues();
		RestrictionType restrictionType = criterion.getRestrictionType();

		// Null
		if (Criterion.RestrictionType.IS_NULL.equals(restrictionType)) {
			return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).is(null) };
		} else if (Criterion.RestrictionType.IS_NOT_NULL.equals(restrictionType)) {
			return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).ne(null) };
		}
		// LIKE
		else if (!criterion.isExactSearch() && value instanceof String) {
			return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property)
					.regex(this.escapeMetaCharacters(value.toString()), "i") };
		}
		// EQUAL
		else if (Criterion.RestrictionType.EQUAL.equals(restrictionType)) {
			// handle Date
			LocalDateTime localDateTime = mongoDateHandling(attrClass, value, restrictionType);
			if (localDateTime != null) {
				return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).gte(localDateTime)
						.lt(localDateTime.plusDays(1).plusSeconds(-1)) };
			} else {
				return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).is(value) };
			}
		} else if (Criterion.RestrictionType.NOT_EQUAL.equals(restrictionType)) {
			return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).ne(value) };
		} else if (Criterion.RestrictionType.IN.equals(restrictionType)) {
			return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).in(values) };
		} else if (Criterion.RestrictionType.NOT_IN.equals(restrictionType)) {
			return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).nin(values) };
		}
		// GREAT/LESS
		else if (Criterion.RestrictionType.GREATER.equals(restrictionType)) {
			LocalDateTime localDateTime = mongoDateHandling(attrClass, value, restrictionType);
			value = localDateTime != null ? localDateTime : value;
			return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).gt(value),
					dateFromString(property, "$gt", value, attrClass) };
		} else if (Criterion.RestrictionType.GREATER_OR_EQUAL.equals(restrictionType)) {
			LocalDateTime localDateTime = mongoDateHandling(attrClass, value, restrictionType);
			value = localDateTime != null ? localDateTime : value;
			return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).gte(value),
					dateFromString(property, "$gte", value, attrClass) };
		} else if (Criterion.RestrictionType.LESS.equals(restrictionType)) {
			LocalDateTime localDateTime = mongoDateHandling(attrClass, value, restrictionType);
			value = localDateTime != null ? localDateTime : value;
			return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).lt(value),
					dateFromString(property, "$lt", value, attrClass) };
		} else if (Criterion.RestrictionType.LESS_OR_EQUAL.equals(restrictionType)) {
			LocalDateTime localDateTime = mongoDateHandling(attrClass, value, restrictionType);
			value = localDateTime != null ? localDateTime : value;
			return new Object[] { org.springframework.data.mongodb.core.query.Criteria.where(property).lte(value),
					dateFromString(property, "$lte", value, attrClass) };
		}
		// unknown
		else {
			LOGGER.error("Criterion for [" + criterion.getPropertyName() + "] failed!");
		}

		return null;
	}

	public String escapeMetaCharacters(String inputString) {
		final String[] metaCharacters = { "(", ")" };

		for (int i = 0; i < metaCharacters.length; i++) {
			if (inputString.contains(metaCharacters[i])) {
				inputString = inputString.replace(metaCharacters[i], "\\" + metaCharacters[i]);
			}
		}
		return inputString;
	}

	protected LocalDateTime mongoDateHandling(Class<?> attrClass, Object value, RestrictionType restrictionType) {
		if (ClassUtils.isAssignable(attrClass, Date.class) || ClassUtils.isAssignable(attrClass, LocalDateTime.class)
				|| ClassUtils.isAssignable(attrClass, LocalDate.class)) {
			int year = 0;
			int month = 0;
			int day = 0;
			LocalDateTime temp = null;
			if (value instanceof String) {
				String strValue = (String) value;
				if (strValue.length() > 10) {
					temp = DateUtil.parseToLocalDateTime(strValue);
				} else {
					temp = TimeZoneUtil.returnTimeZone(strValue);
				}
				//				try {
				//					Calendar calendar = Calendar.getInstance();
				//					calendar.setTime(DateUtils.parseDateStrictly((String) value, STD_DATEFORMAT));
				//					year = calendar.get(Calendar.YEAR);
				//					month = calendar.get(Calendar.MONTH) + 1;
				//					day = calendar.get(Calendar.DAY_OF_MONTH);
				//				} catch (ParseException e) {
				//					LOGGER.error(e.getMessage(), e);
				//				}
			} else if (value instanceof Date) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime((Date) value);
				year = calendar.get(Calendar.YEAR);
				month = calendar.get(Calendar.MONTH) + 1;
				day = calendar.get(Calendar.DAY_OF_MONTH);
				temp = LocalDateTime.of(year, month, day, calendar.get(Calendar.HOUR), 0);
			} else if (value instanceof LocalDate) {
				LocalDate vLocalDate = (LocalDate) value;
				temp = TimeZoneUtil.returnTimeZone(vLocalDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				//				year = vLocalDate.getYear();
				//				month = vLocalDate.getMonthValue();
				//				day = vLocalDate.getDayOfMonth();
			} else if (value instanceof LocalDateTime) {
				temp = (LocalDateTime) value;
				//				return vLocalDate;
			}

			//			if (RestrictionType.LESS.equals(restrictionType) || RestrictionType.LESS_OR_EQUAL.equals(restrictionType)) {
			//				return LocalDateTime.of(year, month, day, 23, 59, 59, 999999999);
			//				return temp.plusHours(23).plusMinutes(59).plusSeconds(59);
			//			} else {
			//				return LocalDateTime.of(year, month, day, 0, 0);
			return temp;
			//			}

		}
		return null;
	}

	protected DBObject dateFromString(String property, String operator, Object value, Class<?> attrClass) {
		//		String mongoDateFormat = (ClassUtils.isAssignable(attrClass, Date.class) || ClassUtils.isAssignable(attrClass, LocalDateTime.class))
		//				? Criterion.DEFAULT_MONGO_DATE_TIME_FORMAT
		//				: Criterion.DEFAULT_MONGO_DATE_FORMAT;
		//		String dateFormat = Criterion.DEFAULT_MONGO_DATE_TIME_FORMAT.equals(mongoDateFormat) ? VariableContext.DEFAULT_DATE_TIME_FORMAT
		//				: VariableContext.DEFAULT_DATE_FORMAT;

		if (value instanceof String) {
			value = TimeZoneUtil.returnTimeZone((String) value);
		}
		if (value == null) return null;

		//		return new BasicDBObject("$expr",
		//				new BasicDBObject(operator,
		//						new Object[] { "$" + property, new BasicDBObject("$dateFromString", new BasicDBObject("dateString", strDate)
		//								.append("format", mongoDateFormat).append("timezone", ZoneId.systemDefault().toString())) }));
		return new BasicDBObject(property, new BasicDBObject(operator, value));
	}

	protected Field getField(Class<?> javaClass, String property) throws NoSuchFieldException, SecurityException {
		String[] splitProperty = property.split("[.]");
		Field field = null;
		for (String prop : splitProperty) {
			if (field == null) {
				Map<String, Field> fields = this.getAllFields(javaClass, new HashMap<String, Field>());
				field = fields.get(prop);
			} else {
				Class<?> attrClass = field.getType();
				if (attrClass.getName().equals("java.util.List") || attrClass.getName().equals("java.util.Map")) {
					ParameterizedType collectionType = (ParameterizedType) field.getGenericType();
					attrClass = (Class<?>) collectionType.getActualTypeArguments()[0];
				}
				Map<String, Field> fields = this.getAllFields(attrClass, new HashMap<String, Field>());
				field = fields.get(prop);
			}
		}
		return field;
	}

	private Map<String, Field> getAllFields(Class clazz, Map<String, Field> fields) {
		Class superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			this.getAllFields(superClazz, fields);
		}
		for (Field field : clazz.getDeclaredFields()) {
			fields.put(field.getName(), field);
		}
		return fields;
	}

	/**
	 * provide Map to translate short property to full property path eg.
	 * customerID => customer.customerID
	 */
	protected Map<String, String> getMapPropertyResolver() {
		return new HashMap<>();
	}

	/**
	 * @Deprecated use queryByCriteria(Criteria, Class<?>)
	 */
	@Deprecated
	@Override
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, List<DateRange> dateRanges) {

		Criteria criteria = this.getCriteriaByMapParam(mapParam, this.getTargetClass());

		/** start query */
		QueryResult<T> queryResult = null;

		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();

		DateRange dateRange1 = dateRanges.get(0);
		criteria.appendCriterion(new Criterion(dateRange1.getPropertyPath(), RestrictionType.GREATER_OR_EQUAL, dateRange1.getStartDate()));
		criteria.appendCriterion(new Criterion(dateRange1.getPropertyPath(), RestrictionType.LESS_OR_EQUAL, dateRange1.getEndDate()));

		List<org.springframework.data.mongodb.core.query.Criteria> andCriterias =
				(List<org.springframework.data.mongodb.core.query.Criteria>) this.getMongoCriterias(criteria)[0];

		for (org.springframework.data.mongodb.core.query.Criteria andCriteria : andCriterias) {
			query.addCriteria(andCriteria);
		}

		for (DateRange dateRange : dateRanges) {
			if (true) break;
			LocalDateTime startDate = LocalDateTime.of(dateRange.getStartDate().getYear(), dateRange.getStartDate().getMonthValue(),
					dateRange.getStartDate().getDayOfMonth(), 8, 0, 0);

			if (dateRange.getEndDate() != null) {
				LocalDateTime endDate = LocalDateTime.of(dateRange.getEndDate().getYear(), dateRange.getEndDate().getMonthValue(),
						dateRange.getEndDate().getDayOfMonth(), 9, 0);
				//				endDate = endDate.plusDays(1);

				query.addCriteria(
						org.springframework.data.mongodb.core.query.Criteria.where(dateRange.getPropertyPath()).gte(startDate).lt(endDate));
			} else {
				query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where(dateRange.getPropertyPath()).gte(startDate));
			}
		}

		if (criteria.getOrder() != null) {

			for (Order order : criteria.getOrder()) {
				if (order.getIsAscending()) {
					query.with(new Sort(Sort.Direction.ASC, order.getProperty()));
				} else {
					query.with(new Sort(Sort.Direction.DESC, order.getProperty()));
				}
			}
		}

		long total = 0;
		if (!criteria.isFetchAll()) {

			int page = criteria.getRequestedPage();
			int pageSize = criteria.getPageSize();
			total = mongoTemplate.count(query, this.getTargetClass());

			final Pageable pageableRequest = new PageRequest(page, pageSize);
			query.with(pageableRequest);
		}

		List<T> result = (List<T>) mongoTemplate.find(query, this.getTargetClass());
		queryResult = new QueryResult<T>(Math.toIntExact(total), result);

		return queryResult;
	}

	/**
	 * @Deprecated use queryByCriteria(Criteria, Class<?>)
	 */
	@Deprecated
	@Override
	public QueryResult<T> queryByMapParamDateTime(MultiValueMap<String, String> mapParam, List<DateTimeRange> dateTimeRanges) {

		Criteria criteria = this.getCriteriaByMapParam(mapParam, this.getTargetClass());

		/** start query */
		QueryResult<T> queryResult = null;

		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();

		DateTimeRange firstDateTime = dateTimeRanges.get(0);
		criteria.appendCriterion(
				new Criterion(firstDateTime.getPropertyPath(), RestrictionType.GREATER_OR_EQUAL, firstDateTime.getStartDateTime()));
		criteria.appendCriterion(
				new Criterion(firstDateTime.getPropertyPath(), RestrictionType.LESS_OR_EQUAL, firstDateTime.getEndDateTime()));

		List<org.springframework.data.mongodb.core.query.Criteria> andCriterias =
				(List<org.springframework.data.mongodb.core.query.Criteria>) this.getMongoCriterias(criteria)[0];

		for (org.springframework.data.mongodb.core.query.Criteria andCriteria : andCriterias) {
			query.addCriteria(andCriteria);
		}

		for (DateTimeRange dateTimeRange : dateTimeRanges) {
			if (true) break;
			LocalDateTime startDateTime =
					LocalDateTime.of(dateTimeRange.getStartDateTime().getYear(), dateTimeRange.getStartDateTime().getMonthValue(),
							dateTimeRange.getStartDateTime().getDayOfMonth(), dateTimeRange.getStartDateTime().getHour(),
							dateTimeRange.getStartDateTime().getMinute(), dateTimeRange.getStartDateTime().getSecond());

			if (dateTimeRange.getEndDateTime() != null) {
				LocalDateTime endDateTime =
						LocalDateTime.of(dateTimeRange.getEndDateTime().getYear(), dateTimeRange.getEndDateTime().getMonthValue(),
								dateTimeRange.getEndDateTime().getDayOfMonth(), dateTimeRange.getEndDateTime().getHour(),
								dateTimeRange.getEndDateTime().getMinute(), dateTimeRange.getEndDateTime().getSecond());
				//				endDate = endDate.plusDays(1);

				query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where(dateTimeRange.getPropertyPath())
						.gte(startDateTime).lt(endDateTime));
			} else {
				query.addCriteria(
						org.springframework.data.mongodb.core.query.Criteria.where(dateTimeRange.getPropertyPath()).gte(startDateTime));
			}
		}

		if (criteria.getOrder() != null) {

			for (Order order : criteria.getOrder()) {
				if (order.getIsAscending()) {
					query.with(new Sort(Sort.Direction.ASC, order.getProperty()));
				} else {
					query.with(new Sort(Sort.Direction.DESC, order.getProperty()));
				}
			}
		}

		long total = 0;
		if (!criteria.isFetchAll()) {

			int page = criteria.getRequestedPage();
			int pageSize = criteria.getPageSize();
			total = mongoTemplate.count(query, this.getTargetClass());

			final Pageable pageableRequest = new PageRequest(page, pageSize);
			query.with(pageableRequest);
		}

		List<T> result = (List<T>) mongoTemplate.find(query, this.getTargetClass());
		queryResult = new QueryResult<T>(Math.toIntExact(total), result);

		return queryResult;
	}

	/**
	 * @Deprecated use queryByCriteria(Criteria, Class<?>)
	 */
	@Deprecated
	@Override
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> andParam, MultiValueMap<String, String> orParam) {

		Criteria andCriteria = this.getCriteriaByMapParam(andParam, this.getTargetClass());
		Criteria orCriteria = this.getCriteriaByMapParam(orParam, this.getTargetClass());

		return this.queryByCriteria(andCriteria, orCriteria, this.getTargetClass());
	}

	/**
	 * @Deprecated use queryByCriteria(Criteria, Class<?>)
	 */
	@Deprecated
	@Override
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> andParam, MultiValueMap<String, String> orParam,
			List<DateRange> dateRanges) {

		Criteria andCriteria = this.getCriteriaByMapParam(andParam, this.getTargetClass());
		Criteria orCriteria = this.getCriteriaByMapParam(orParam, this.getTargetClass());

		Criterion companyCriterion = null;
		for (Criterion criterion : orCriteria.getCriterion()) {
			if (criterion.getPropertyName().equalsIgnoreCase("companyId")) {
				companyCriterion = criterion;
				break;
			}
		}
		if (companyCriterion != null) {
			orCriteria.getCriterion().remove(companyCriterion);
		}

		/** start query */
		QueryResult<T> queryResult = null;

		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();

		List<org.springframework.data.mongodb.core.query.Criteria> andCriterias =
				(List<org.springframework.data.mongodb.core.query.Criteria>) this.getMongoCriterias(andCriteria)[0];

		for (org.springframework.data.mongodb.core.query.Criteria criteria : andCriterias) {
			query.addCriteria(criteria);
		}

		List<org.springframework.data.mongodb.core.query.Criteria> orCriterias =
				(List<org.springframework.data.mongodb.core.query.Criteria>) this.getMongoCriterias(orCriteria)[0];
		org.springframework.data.mongodb.core.query.Criteria[] orCriteriaArray =
				orCriterias.toArray(new org.springframework.data.mongodb.core.query.Criteria[orCriterias.size()]);
		org.springframework.data.mongodb.core.query.Criteria criteria = new org.springframework.data.mongodb.core.query.Criteria();
		criteria.orOperator(orCriteriaArray);

		query.addCriteria(criteria);

		for (DateRange dateRange : dateRanges) {

			LocalDateTime startDate = LocalDateTime.of(dateRange.getStartDate().getYear(), dateRange.getStartDate().getMonthValue(),
					dateRange.getStartDate().getDayOfMonth(), 0, 0, 0);

			if (dateRange.getEndDate() != null) {
				LocalDateTime endDate = LocalDateTime.of(dateRange.getEndDate().getYear(), dateRange.getEndDate().getMonthValue(),
						dateRange.getEndDate().getDayOfMonth(), 0, 0);
				endDate = endDate.plusDays(1);

				query.addCriteria(
						org.springframework.data.mongodb.core.query.Criteria.where(dateRange.getPropertyPath()).gte(startDate).lt(endDate));
			} else {
				query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where(dateRange.getPropertyPath()).gte(startDate));
			}
		}

		if (andCriteria.getOrder() != null) {

			for (Order order : andCriteria.getOrder()) {
				if (order.getIsAscending()) {
					query.with(new Sort(Sort.Direction.ASC, order.getProperty()));
				} else {
					query.with(new Sort(Sort.Direction.DESC, order.getProperty()));
				}
			}
		}

		long total = 0;
		if (!andCriteria.isFetchAll()) {

			int page = andCriteria.getRequestedPage();
			int pageSize = andCriteria.getPageSize();
			total = mongoTemplate.count(query, this.getTargetClass());

			final Pageable pageableRequest = new PageRequest(page, pageSize);
			query.with(pageableRequest);
		}

		List<T> result = (List<T>) mongoTemplate.find(query, this.getTargetClass());

		if (!andCriteria.isFetchAll()) {
			queryResult = new QueryResult<T>(Math.toIntExact(total), result);
		} else {
			queryResult = new QueryResult<T>(result.size(), result);
		}

		return queryResult;
	}

	/**
	 * @Deprecated use queryByCriteria(Criteria, Class<?>)
	 */
	@Deprecated
	@Override
	public QueryResult<T> queryByMapParamDateTime(MultiValueMap<String, String> andParam, MultiValueMap<String, String> orParam,
			List<DateTimeRange> dateTimeRanges) {

		Criteria andCriteria = this.getCriteriaByMapParam(andParam, this.getTargetClass());
		Criteria orCriteria = this.getCriteriaByMapParam(orParam, this.getTargetClass());

		Criterion companyCriterion = null;
		for (Criterion criterion : orCriteria.getCriterion()) {
			if (criterion.getPropertyName().equalsIgnoreCase("companyId")) {
				companyCriterion = criterion;
				break;
			}
		}
		if (companyCriterion != null) {
			orCriteria.getCriterion().remove(companyCriterion);
		}

		/** start query */
		QueryResult<T> queryResult = null;

		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();

		List<org.springframework.data.mongodb.core.query.Criteria> andCriterias =
				(List<org.springframework.data.mongodb.core.query.Criteria>) this.getMongoCriterias(andCriteria)[0];

		for (org.springframework.data.mongodb.core.query.Criteria criteria : andCriterias) {
			query.addCriteria(criteria);
		}

		List<org.springframework.data.mongodb.core.query.Criteria> orCriterias =
				(List<org.springframework.data.mongodb.core.query.Criteria>) this.getMongoCriterias(orCriteria)[0];
		org.springframework.data.mongodb.core.query.Criteria[] orCriteriaArray =
				orCriterias.toArray(new org.springframework.data.mongodb.core.query.Criteria[orCriterias.size()]);
		org.springframework.data.mongodb.core.query.Criteria criteria = new org.springframework.data.mongodb.core.query.Criteria();
		criteria.orOperator(orCriteriaArray);

		query.addCriteria(criteria);

		for (DateTimeRange dateTimeRange : dateTimeRanges) {
			if (true) break;
			LocalDateTime startDateTime =
					LocalDateTime.of(dateTimeRange.getStartDateTime().getYear(), dateTimeRange.getStartDateTime().getMonthValue(),
							dateTimeRange.getStartDateTime().getDayOfMonth(), dateTimeRange.getStartDateTime().getHour(),
							dateTimeRange.getStartDateTime().getMinute(), dateTimeRange.getStartDateTime().getSecond());

			if (dateTimeRange.getEndDateTime() != null) {
				LocalDateTime endDateTime =
						LocalDateTime.of(dateTimeRange.getEndDateTime().getYear(), dateTimeRange.getEndDateTime().getMonthValue(),
								dateTimeRange.getEndDateTime().getDayOfMonth(), dateTimeRange.getEndDateTime().getHour(),
								dateTimeRange.getEndDateTime().getMinute(), dateTimeRange.getEndDateTime().getSecond());
				//				endDate = endDate.plusDays(1);

				query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where(dateTimeRange.getPropertyPath())
						.gte(startDateTime).lt(endDateTime));
			} else {
				query.addCriteria(
						org.springframework.data.mongodb.core.query.Criteria.where(dateTimeRange.getPropertyPath()).gte(startDateTime));
			}
		}

		if (andCriteria.getOrder() != null) {

			for (Order order : andCriteria.getOrder()) {
				if (order.getIsAscending()) {
					query.with(new Sort(Sort.Direction.ASC, order.getProperty()));
				} else {
					query.with(new Sort(Sort.Direction.DESC, order.getProperty()));
				}
			}
		}

		long total = 0;
		if (!andCriteria.isFetchAll()) {

			int page = andCriteria.getRequestedPage();
			int pageSize = andCriteria.getPageSize();
			total = mongoTemplate.count(query, this.getTargetClass());

			final Pageable pageableRequest = new PageRequest(page, pageSize);
			query.with(pageableRequest);
		}

		List<T> result = (List<T>) mongoTemplate.find(query, this.getTargetClass());

		if (!andCriteria.isFetchAll()) {
			queryResult = new QueryResult<T>(Math.toIntExact(total), result);
		} else {
			queryResult = new QueryResult<T>(result.size(), result);
		}

		return queryResult;
	}
	
	@Override
	public QueryResult<T> queryByCriteria(Criteria andCriteria, Criteria orCriteria) {
		return queryByCriteria(andCriteria, orCriteria, this.getTargetClass());
	}

	/**
	 * @Deprecated use queryByCriteria(Criteria, Class<?>)
	 */
//	@Deprecated
	@Override
	public QueryResult<T> queryByCriteria(Criteria andCriteria, Criteria orCriteria, Class<?> targetClass) {

		/** start query */
		QueryResult<T> queryResult = null;

		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();

		List<org.springframework.data.mongodb.core.query.Criteria> andCriterias =
				(List<org.springframework.data.mongodb.core.query.Criteria>) this.getMongoCriterias(andCriteria)[0];

		for (org.springframework.data.mongodb.core.query.Criteria criteria : andCriterias) {
			query.addCriteria(criteria);
		}

		List<org.springframework.data.mongodb.core.query.Criteria> orCriterias =
				(List<org.springframework.data.mongodb.core.query.Criteria>) this.getMongoCriterias(orCriteria)[0];
		
		org.springframework.data.mongodb.core.query.Criteria criteria = new org.springframework.data.mongodb.core.query.Criteria();
		
		if (orCriterias != null && orCriterias.size() > 0) {
			
			List<org.springframework.data.mongodb.core.query.Criteria> reformCriterias 
				= new ArrayList<org.springframework.data.mongodb.core.query.Criteria>();
						
			HashMap<String, List<org.springframework.data.mongodb.core.query.Criteria>> nestedCriteriaMap 
				= new HashMap<String, List<org.springframework.data.mongodb.core.query.Criteria>>();
			
			for (Criterion criterion: orCriteria.getCriterion()) {
				if (criterion.getNestedConditionKey() != null) {
					String nestedKey = criterion.getNestedConditionKey();
					if (!nestedCriteriaMap.containsKey(nestedKey)) {
						List<org.springframework.data.mongodb.core.query.Criteria> nestedCriterias 
							= new ArrayList<org.springframework.data.mongodb.core.query.Criteria>();
						org.springframework.data.mongodb.core.query.Criteria nestedCriteria = null;
						for (org.springframework.data.mongodb.core.query.Criteria searchCriteria: orCriterias) {
							if (searchCriteria.getKey().equals(criterion.getPropertyName())) {
								nestedCriteria = searchCriteria;
							}
						}
						if (nestedCriteria != null) {
							nestedCriterias.add(nestedCriteria);
							nestedCriteriaMap.put(nestedKey, nestedCriterias);
						}
					} else {
						List<org.springframework.data.mongodb.core.query.Criteria> nestedCriterias 
							= nestedCriteriaMap.get(nestedKey);
						org.springframework.data.mongodb.core.query.Criteria nestedCriteria = null;
						for (org.springframework.data.mongodb.core.query.Criteria searchCriteria: orCriterias) {
							if (searchCriteria.getKey().equals(criterion.getPropertyName())) {
								nestedCriteria = searchCriteria;
							}
						}
						if (nestedCriteria != null) {
							nestedCriterias.add(nestedCriteria);
						}
					}
				} else {

					org.springframework.data.mongodb.core.query.Criteria mainCriteria = null;
					for (org.springframework.data.mongodb.core.query.Criteria searchCriteria: orCriterias) {
						if (searchCriteria.getKey().equals(criterion.getPropertyName())) {
							mainCriteria = searchCriteria;
						}
					}
					if (mainCriteria != null) {
						reformCriterias.add(mainCriteria);
					}					
				}
			}
			
			for (String key: nestedCriteriaMap.keySet()) {
				List<org.springframework.data.mongodb.core.query.Criteria> nestedCriterias = nestedCriteriaMap.get(key);
				org.springframework.data.mongodb.core.query.Criteria nestedCriteria = null;
				
				for (org.springframework.data.mongodb.core.query.Criteria subCriteria: nestedCriterias) {
					if (nestedCriteria == null) {
						nestedCriteria = subCriteria;
					} else {
						nestedCriteria.andOperator(subCriteria);
					}
				}
				if (nestedCriteria != null) {
					reformCriterias.add(nestedCriteria);
				}
			}
						
			org.springframework.data.mongodb.core.query.Criteria[] criteriaArray =
					reformCriterias.toArray(new org.springframework.data.mongodb.core.query.Criteria[reformCriterias.size()]);
			
			criteria.orOperator(criteriaArray);
		}

		query.addCriteria(criteria);

		if (andCriteria.getOrder() != null) {

			for (Order order : andCriteria.getOrder()) {
				if (order.getIsAscending()) {
					query.with(new Sort(Sort.Direction.ASC, order.getProperty()));
				} else {
					query.with(new Sort(Sort.Direction.DESC, order.getProperty()));
				}
			}
		}

		long total = 0;
		if (!andCriteria.isFetchAll()) {

			int page = andCriteria.getRequestedPage();
			int pageSize = andCriteria.getPageSize();
			total = mongoTemplate.count(query, this.getTargetClass());

			final Pageable pageableRequest = new PageRequest(page, pageSize);
			query.with(pageableRequest);
		}

		List<T> result = (List<T>) mongoTemplate.find(query, this.getTargetClass());

		if (!andCriteria.isFetchAll()) {
			queryResult = new QueryResult<T>(Math.toIntExact(total), result);
		} else {
			queryResult = new QueryResult<T>(result.size(), result);
		}

		return queryResult;
	}
}
