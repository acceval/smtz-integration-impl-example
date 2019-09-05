package com.acceval.core.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class QueueSender {

	public static final String SMARTTRADZ_TOPIC = "smarttradz.topic";
	public static final String QUEUE_NAME_AUDITLOG = "commons-auditlog-queue";

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
		try {
			if (rabbitTemplate.getMessageConverter().getClass().getSimpleName().equals("Jackson2JsonMessageConverter")) {
				rabbitTemplate.convertAndSend(this.getTopicExchageName(), this.getSenderQueueName(), this.messageBody.getBody());
			} else {
				logger.warn("Queue message not send due to wrong rabbit mq message converter. Must be json format.");
			}
		} catch (AmqpConnectException ex) {
			ex.printStackTrace();
		}
	}

	public void sendMessage(Object objMsg) {
		try {
			if (rabbitTemplate.getMessageConverter().getClass().getSimpleName().equals("Jackson2JsonMessageConverter")) {
				rabbitTemplate.convertAndSend(this.getTopicExchageName(), this.getSenderQueueName(), objMsg);
			} else {
				logger.warn("Queue message not send due to wrong rabbit mq message converter. Must be json format.");
			}
		} catch (AmqpConnectException ex) {
			ex.printStackTrace();
		}
	}
}
