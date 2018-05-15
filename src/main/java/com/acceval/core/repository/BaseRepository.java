package com.acceval.core.repository;

import org.springframework.util.MultiValueMap;

public interface BaseRepository {
	
	boolean isExists(Object obj);
			
	QueryResult queryByCriteria(Criteria acceCriteria);

	QueryResult queryByCriteria(Criteria acceCriteria, Class<?> targetClass);

	QueryResult queryByMapParam(MultiValueMap<String, String> mapParam);

	QueryResult queryByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass);

	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam);

	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass);

}
