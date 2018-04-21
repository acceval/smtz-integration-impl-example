package com.acceval.core.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class QueueSender {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected MessageBody<?> messageBody = null;
            
	@Autowired
    private RabbitTemplate rabbitTemplate;

    protected abstract String getTopicExchageName();
    
    protected abstract String getSenderQueueName();
    

    public MessageBody<?> getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(MessageBody<?> messageBody) {
		this.messageBody = messageBody;
	}


//  @Scheduled(cron = "0 0 21 * * *")
//  @Scheduled(fixedDelay = 3000L)        
    public void sendMessage() {
  		    		
		logger.info("Sending message...");
		rabbitTemplate.convertAndSend(this.getTopicExchageName(), this.getSenderQueueName(), this.messageBody.getBody());  	
    }

}
