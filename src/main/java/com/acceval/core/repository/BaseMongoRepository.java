package com.acceval.core.repository;

import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

public interface BaseMongoRepository<T> {
		
	QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam);
	
	QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, List<DateRange> dateRanges);
	
	QueryResult<T> queryByMapParamDateTime(MultiValueMap<String, String> mapParam, List<DateTimeRange> dateTimeRanges);
	
	QueryResult<T> queryByMapParam(MultiValueMap<String, String> andParam, MultiValueMap<String, String> orParam);
			
	QueryResult<T> queryByMapParam(MultiValueMap<String, String> andParam, MultiValueMap<String, String> orParam,
		List<DateRange> dateRanges);
	
	QueryResult<T> queryByMapParamDateTime(MultiValueMap<String, String> andParam, MultiValueMap<String, String> orParam, 
		List<DateTimeRange> dateTimeRanges);

	QueryResult<T> queryByCriteria(Criteria acceCriteria);
	
	QueryResult<T> queryByCriteria(Criteria acceCriteria, Class<?> targetClass);
	
	QueryResult<T> queryByCriteria(Criteria andCriteria, Criteria orCriteria);

	QueryResult<T> queryByCriteria(Criteria andCriteria, Criteria orCriteria, Class<?> targetClass);	

	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam);

	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass);
	
	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam, Map<String, String> propertyResolver, 
			Class<?> targetClass, boolean isOrCriteria);
	
	Object[] getMongoCriterias(Criteria acceCriteria);

	Object[] getMongoCriterias(Criteria acceCriteria, Class<?> targetClass);

	List<T> aggregateByCriteria(Criteria acceCriteria);

	QueryResult<T> queryByCriteriaWithoutPagination(Criteria acceCriteria, Class<?> targetClass);

	Object[] criterionToMongoCriteriaByField(Criterion criterion, Class<?> attrClass);
}
