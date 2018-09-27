package com.acceval.core.repository;

import java.util.List;

import org.springframework.util.MultiValueMap;

public interface BaseRepository<T> {
	
	List<T> findAll();
	
	boolean isExists(Object obj);
			
	QueryResult<T> queryByCriteria(Criteria acceCriteria);

	QueryResult<T> queryByCriteria(Criteria acceCriteria, Class<?> targetClass);

	QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam);

	QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass);

	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam);

	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass);

}
