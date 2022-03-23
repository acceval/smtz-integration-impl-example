package com.acceval.core.amqp.audit;

import org.springframework.stereotype.Component;

import com.acceval.core.amqp.MessageBody;
import com.acceval.core.amqp.QueueSender;

//@Component
public class PathAuditQueueSender extends QueueSender {
	    
//    private static final String SENDER_QUEUE_NAME = "commons-audittrail-queue";
    private static final String SMARTTRADZ_TOPIC = "smarttradz.topic"; 
    
    public PathAuditQueueSender() {
    	this.messageBody = new MessageBody<String>();
    }
    
    public PathAuditQueueSender(String body) {
    	
    	this.messageBody = new MessageBody<String>(body);
	}
    
	@Override
	protected String getTopicExchageName() {
		return SMARTTRADZ_TOPIC;
	}

	@Override
	protected String getSenderQueueName() {		
		return "commons-audittrail-queue";
	}
    
}
