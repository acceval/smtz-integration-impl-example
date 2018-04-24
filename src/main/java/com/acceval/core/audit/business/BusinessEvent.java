package com.acceval.core.audit.business;

import org.springframework.context.ApplicationEvent;

import com.acceval.core.amqp.MessageBody;

public class BusinessEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 7772590195620791162L;
	
	protected MessageBody<?> messageBody = null;
	
	public BusinessEvent(Object source) {
		super(source);
	}
	
	public BusinessEvent(Object source, MessageBody<?> body) {
		super(source);
		this.messageBody = body;		
	}

	public MessageBody<?> getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(MessageBody<?> messageBody) {
		this.messageBody = messageBody;
	}
	
}
