package com.acceval.core.repository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.DateFormat;
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;

import com.acceval.core.model.AuthUser;
import com.acceval.core.model.BaseModel;
import com.acceval.core.repository.Criterion.RestrictionType;

public abstract class BaseMongoRepositoryImpl<T> implements BaseMongoRepository<T> {

	public static String _PAGE = "_page";
	public static String _PAGESIZE = "_pageSize";
	public static String _SORT = "_sort";
	public static String _FETCHALL = "_fetchAll";

	public static String _ASC = "asc";
	public static String _DESC = "desc";
	
	public static String[] STD_DATEFORMAT = new String[] { "yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy", "yyyy/MM/dd" };
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseMongoRepositoryImpl.class);

	@Autowired
	protected MongoTemplate mongoTemplate;

	protected abstract Class<?> getTargetClass();

	@Override
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam) {
		
		return queryByMapParam(mapParam, this.getTargetClass());
	}
	
	@Override
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, List<DateRange> dateRanges) {
		
		Criteria criteria = this.getCriteriaByMapParam(mapParam, this.getTargetClass());

		/** start query */
		QueryResult<T> queryResult = null;
		
		org.springframework.data.mongodb.core.query.Query query = 
				new org.springframework.data.mongodb.core.query.Query();
				
		List<org.springframework.data.mongodb.core.query.Criteria> andCriterias = this.getMongoCriterias(criteria);
		
		for (org.springframework.data.mongodb.core.query.Criteria andCriteria : andCriterias) {
			query.addCriteria(andCriteria);
		}
		
		for (DateRange dateRange : dateRanges) {
			
			LocalDateTime startDate = LocalDateTime.of(dateRange.getStartDate().getYear(), 
					dateRange.getStartDate().getMonthValue(), 
					dateRange.getStartDate().getDayOfMonth(),
					0,
					0,
					0);
			
			if (dateRange.getEndDate() != null) {
				LocalDateTime endDate = LocalDateTime.of(dateRange.getEndDate().getYear(), 
						dateRange.getEndDate().getMonthValue(), 
						dateRange.getEndDate().getDayOfMonth(),
						0,
						0);
				endDate = endDate.plusDays(1);
								
				query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where(
					dateRange.getPropertyPath()).gte(startDate).lt(endDate));
			} else {
				query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where(
						dateRange.getPropertyPath()).gte(startDate));
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
	
	@Override
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> andParam, MultiValueMap<String, String> orParam) {
		
		Criteria andCriteria = this.getCriteriaByMapParam(andParam, this.getTargetClass());
		Criteria orCriteria = this.getCriteriaByMapParam(orParam, this.getTargetClass());
		
		return this.queryByCriteria(andCriteria, orCriteria, this.getTargetClass());
	}

	/**
	 * convert map to criteria and inquiry, normally use for Search Form, with
	 * pagination
	 */	
	public QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass) {
		
		Criteria criteria = this.getCriteriaByMapParam(mapParam, targetClass);
		return this.queryByCriteria(criteria, targetClass);
	}

	/**
	 * convert map to criteria
	 */	
	public Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass) {
		int page = mapParam.get(_PAGE) != null ? Integer.parseInt(mapParam.getFirst(_PAGE)) : 0;
		int pageSize = mapParam.get(_PAGESIZE) != null ? Integer.parseInt(mapParam.getFirst(_PAGESIZE)) : 0;
		boolean isFetchAll = mapParam.get(_FETCHALL) != null ? Boolean.parseBoolean(mapParam.getFirst(_FETCHALL)) : false;
		List<String> lstSort = mapParam.get(_SORT);

		try {
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
				String resolveKey = this.getMapPropertyResolver().containsKey(key) ? getMapPropertyResolver().get(key) : key;
				Class<?> attrClass = this.getField(this.getTargetClass(), resolveKey).getType();

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
						lstCrriterion.add(new Criterion(resolveKey, ".*" + mapParam.getFirst(key).toLowerCase() + ".*", false));
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
				e.printStackTrace();
				if (e.getMessage().indexOf("Unable to locate Attribute  with the the given name") == -1) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		acceCriteria.setCriterion(lstCrriterion);

		return acceCriteria;
	}
	
	public QueryResult<T> queryByCriteria(Criteria andCriteria, Criteria orCriteria, Class<?> targetClass) {
		
		/** start query */
		QueryResult<T> queryResult = null;
		
		org.springframework.data.mongodb.core.query.Query query = 
				new org.springframework.data.mongodb.core.query.Query();
				
		List<org.springframework.data.mongodb.core.query.Criteria> andCriterias = this.getMongoCriterias(andCriteria);
		
		for (org.springframework.data.mongodb.core.query.Criteria criteria : andCriterias) {
			query.addCriteria(criteria);
		}
		
		List<org.springframework.data.mongodb.core.query.Criteria> orCriterias = this.getMongoCriterias(orCriteria);
		org.springframework.data.mongodb.core.query.Criteria[] orCriteriaArray = orCriterias.toArray(
				new org.springframework.data.mongodb.core.query.Criteria[orCriterias.size()]);
		org.springframework.data.mongodb.core.query.Criteria criteria = 
				new org.springframework.data.mongodb.core.query.Criteria();
		criteria.orOperator(orCriteriaArray);
		
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
	
	public QueryResult<T> queryByCriteria(Criteria acceCriteria, Class<?> targetClass) {

		/** start query */
		QueryResult<T> queryResult = null;
		
		org.springframework.data.mongodb.core.query.Query query = 
				new org.springframework.data.mongodb.core.query.Query();
		
		List<org.springframework.data.mongodb.core.query.Criteria> criterias = this.getMongoCriterias(acceCriteria);
		
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
	
	protected List<org.springframework.data.mongodb.core.query.Criteria> getMongoCriterias(Criteria acceCriteria) {

		List<org.springframework.data.mongodb.core.query.Criteria> mongoCriterias = 
				new ArrayList<org.springframework.data.mongodb.core.query.Criteria>();
		
		/** criteria */
		for (Criterion criterion : acceCriteria.getCriterion()) {
			String property = getMapPropertyResolver().containsKey(criterion.getPropertyName())
					? getMapPropertyResolver().get(criterion.getPropertyName())
					: criterion.getPropertyName();
			Object value = criterion.getSearchValue();
			Object[] values = criterion.getSearchValues();
			RestrictionType restrictionType = criterion.getRestrictionType();
			
			Class<?> attrClass = null;
			try {
				Field field = this.getField(this.getTargetClass(), property);
			
				attrClass = field.getType();
			} catch(NoSuchFieldException ex) {
				ex.printStackTrace();
				continue;
			}
			

			// Null
			if (Criterion.RestrictionType.IS_NULL.equals(restrictionType)) {
				mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).exists(false));
				
			} else if (Criterion.RestrictionType.IS_NOT_NULL.equals(restrictionType)) {
				mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).exists(true));
			}
			// LIKE
			else if (!criterion.isExactSearch() && value instanceof String) {
				mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property)
						.regex(value.toString(), "i"));
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
									
					LocalDate startDate = LocalDate.of(year, month, day);						
					LocalDate endDate = startDate.plusDays(1);
					
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property)
							.gte(startDate).lte(endDate));
					
				} else {					
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).is(value));
				}
			} else if (Criterion.RestrictionType.IN.equals(restrictionType)) {

				mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).in(values));				

			} else if (Criterion.RestrictionType.NOT_IN.equals(restrictionType)) {

				mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).nin(values));				
			}
			// GREAT/LESS
			else if (Criterion.RestrictionType.GREATER.equals(restrictionType)) {
				if (value instanceof LocalDate) {					
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).gt((LocalDate) value));					
				} else if (value instanceof Date) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).gt((Date) value));					
				} else if (value instanceof LocalDateTime) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).gt((LocalDateTime) value));					
				} else if (value instanceof Number) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).gt((Number) value));					
				}
			} else if (Criterion.RestrictionType.GREATER_OR_EQUAL.equals(restrictionType)) {
				if (value instanceof LocalDate) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).gte((LocalDate) value));					
				} else if (value instanceof Date) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).gte((Date) value));
				} else if (value instanceof LocalDateTime) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).gte((LocalDateTime) value));
				} else if (value instanceof Number) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).gte((Number) value));
				}
			} else if (Criterion.RestrictionType.LESS.equals(restrictionType)) {
				if (value instanceof LocalDate) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).lt((LocalDate) value));
				} else if (value instanceof Date) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).lt((Date) value));
				} else if (value instanceof LocalDateTime) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).lt((LocalDateTime) value));
				} else if (value instanceof Number) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).lt((Number) value));
				}
			} else if (Criterion.RestrictionType.LESS_OR_EQUAL.equals(restrictionType)) {
				if (value instanceof LocalDate) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).lte((LocalDate) value));
				} else if (value instanceof Date) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).lte((Date) value));
				} else if (value instanceof LocalDateTime) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).lte((LocalDateTime) value));
				} else if (value instanceof Number) {
					mongoCriterias.add(org.springframework.data.mongodb.core.query.Criteria.where(property).lte((Number) value));
				}
			}
			// unknown
			else {
				LOGGER.error("Criterion for [" + criterion.getPropertyName() + "] failed!");
			}
		}
		
		return mongoCriterias;
	}


	protected Field getField(Class<?> javaClass, String property) 
			throws NoSuchFieldException, SecurityException {
		
		String[] splitProperty = property.split("[.]");
		Field field = null;
		for (String prop : splitProperty) {
			if (field == null) {
				Map<String, Field> fields = this.getAllFields(javaClass, new HashMap<String, Field>());
				field = fields.get(prop);				
			} else {
				Class<?> attrClass = field.getType();
				if (attrClass.getName().equals("java.util.List") ||  attrClass.getName().equals("java.util.Map")) {
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
	    
	    for (Field field: clazz.getDeclaredFields()) {
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
}
