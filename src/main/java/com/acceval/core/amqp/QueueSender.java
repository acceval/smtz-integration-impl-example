package com.acceval.core.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class QueueSender {

	public static final String SMARTTRADZ_TOPIC = "smarttradz.topic";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	protected MessageBody<?> messageBody = null;
	
	@Autowired
	private AmqpAdmin admin;	
	
	@Autowired
	private TopicExchange topicExchange;

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

	@Deprecated
	public void sendMessage() {

		try {
			if (rabbitTemplate.getMessageConverter().getClass().getSimpleName()
					.equals("Jackson2JsonMessageConverter")) {
				
				Queue queue = new Queue(this.getSenderQueueName(), true, false, false);				
				Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(this.getSenderQueueName());
				admin.declareBinding(binding);
				
				rabbitTemplate.convertAndSend(this.getTopicExchageName(), this.getSenderQueueName(), 
						this.messageBody.getBody());
			} else {
				logger.warn("Queue message not send due to wrong rabbit mq message converter. Must be json format.");
			}
		} catch (AmqpConnectException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendMessage(MessageBody<?> messageBody) {

		try {
			if (rabbitTemplate.getMessageConverter().getClass().getSimpleName()
					.equals("Jackson2JsonMessageConverter")) {
				
				Queue queue = new Queue(this.getSenderQueueName(), true, false, false);				
				Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(this.getSenderQueueName());
				admin.declareBinding(binding);
				rabbitTemplate.convertAndSend(this.getTopicExchageName(), this.getSenderQueueName(),
						messageBody.getBody());
				
			} else {
				logger.warn("Queue message not send due to wrong rabbit mq message converter. Must be json format.");
			}
		} catch (AmqpConnectException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendMessage(Object objMsg) {
		try {
			if (rabbitTemplate.getMessageConverter().getClass().getSimpleName()
					.equals("Jackson2JsonMessageConverter")) {
				
				Queue queue = new Queue(this.getSenderQueueName(), true, false, false);				
				Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(this.getSenderQueueName());
				admin.declareBinding(binding);				
				rabbitTemplate.convertAndSend(this.getTopicExchageName(), this.getSenderQueueName(), objMsg);
				
			} else {
				logger.warn("Queue message not send due to wrong rabbit mq message converter. Must be json format.");
			}
		} catch (AmqpConnectException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
