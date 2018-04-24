package com.acceval.core.amqp;

import java.io.Serializable;

public class MessageBody<T> implements Serializable {
	
	private T body;
	
	public MessageBody() {
		this(null);
	}

	public MessageBody(T body) {
		this.body = body;
	}

	public T getBody() {
		return body;
	}
}
