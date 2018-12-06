package com.acceval.core.repository;

import java.util.List;

import org.springframework.util.MultiValueMap;

public interface BaseMongoRepository<T> {
		
	QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam);
}
