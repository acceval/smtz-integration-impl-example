package com.acceval.core.amqp;

public class MessageBody<T> {
	
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
