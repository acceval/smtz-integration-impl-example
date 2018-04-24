package com.acceval.core.audit.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.acceval.core.amqp.MessageBody;

@Component
public class BusinessEventPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	public void publishEvent(Object source, MessageBody<?> body) {
		
		BusinessEvent businessEvent = new BusinessEvent(source, body);
		applicationEventPublisher.publishEvent(businessEvent);
	}
}
