package com.acceval.core.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.util.MultiValueMap;

import com.acceval.core.model.BaseEntity.STATUS;

public interface BaseRepository<T> {
	
	Long generateId();

	List<T> findAll();

	boolean isExists(Object obj);

	QueryResult<T> queryByCriteria(Criteria acceCriteria);

	QueryResult<T> queryByCriteria(Criteria acceCriteria, Class<?> targetClass);

	QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam);
	
	QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, List<DateRange> dateRanges);

	QueryResult<T> queryByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass);

	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam);

	Criteria getCriteriaByMapParam(MultiValueMap<String, String> mapParam, Class<?> targetClass);

	T findOneByRecordStatus(STATUS status, Long id);

	List<T> findAllByRecordStatus(STATUS status);

	void softDelete(T entity);

	void softDelete(List<T> entities);

	QueryResult<Tuple> projectionByCriteria(Criteria acceCriteria);

	QueryResult<Tuple> projectionByCriteria(Criteria acceCriteria, Class<?> targetClass);

}
