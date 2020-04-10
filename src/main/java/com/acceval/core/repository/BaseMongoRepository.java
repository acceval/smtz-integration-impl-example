package com.acceval.core.repository;

import java.util.List;

import org.springframework.util.MultiValueMap;

public interface BaseMongoRepository<T> {
		
	QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam);
	
	QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, List<DateRange> dateRanges);
	
	QueryResult<T> queryByMapParam(MultiValueMap<String, String> andParam, MultiValueMap<String, String> orParam);
	
	QueryResult<T> queryByMapParam(MultiValueMap<String, String> andParam, MultiValueMap<String, String> orParam,
		List<DateRange> dateRanges);

	QueryResult<T> queryByCriteria(Criteria acceCriteria);

	QueryResult<T> queryByCriteria(Criteria acceCriteria, Class<?> targetClass);

	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam);

	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass);

	Object[] getMongoCriterias(Criteria acceCriteria);
}
