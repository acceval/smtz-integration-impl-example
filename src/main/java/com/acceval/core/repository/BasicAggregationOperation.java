package com.acceval.core.repository;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import com.mongodb.DBObject;

public class BasicAggregationOperation implements AggregationOperation {

	private DBObject basicDBObject;

	public BasicAggregationOperation(DBObject basicDBObject) {
		super();
		this.basicDBObject = basicDBObject;
	}

	@Override
	public DBObject toDBObject(AggregationOperationContext context) {
		return context.getMappedObject(basicDBObject);
	}

}
