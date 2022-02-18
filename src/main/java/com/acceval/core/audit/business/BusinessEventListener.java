package com.acceval.core.audit.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.acceval.core.amqp.audit.BusinessEventQueueSender;

//@Component
public class BusinessEventListener implements ApplicationListener<BusinessEvent> {
	
//	@Autowired
	private BusinessEventQueueSender eventQueueSender;
	
	@Override
    public void onApplicationEvent(BusinessEvent event) {
		
        System.out.println("Received spring custom event - " + event.getMessageBody().getBody());
        
        eventQueueSender.setMessageBody(event.getMessageBody());
		
        eventQueueSender.sendMessage();
    }

}
