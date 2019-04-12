package com.acceval.core.repository;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BasicAggregationOperation implements AggregationOperation {

	private BasicDBObject basicDBObject;

	public BasicAggregationOperation(BasicDBObject basicDBObject) {
		super();
		this.basicDBObject = basicDBObject;
	}

	@Override
	public DBObject toDBObject(AggregationOperationContext context) {
		return context.getMappedObject(basicDBObject);
	}

}
