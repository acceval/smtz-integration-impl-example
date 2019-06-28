package com.acceval.core.repository;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import com.mongodb.BasicDBObject;

public class BasicAggregationOperation implements AggregationOperation {

	private Document document;

	public BasicAggregationOperation(Document document) {
		super();
		this.document = document;
	}

	public BasicAggregationOperation(BasicDBObject basicDBObject) {
		super();
		this.document = new Document(basicDBObject.toMap());
	}

	@Override
	public Document toDocument(AggregationOperationContext context) {
		return context.getMappedObject(document);
	}

}
