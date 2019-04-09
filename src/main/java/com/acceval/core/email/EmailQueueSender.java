package com.acceval.core.email;

import org.springframework.stereotype.Component;

import com.acceval.core.amqp.MessageBody;
import com.acceval.core.amqp.QueueSender;

@Component
public class EmailQueueSender extends QueueSender {

	private static final String SENDER_QUEUE_NAME = "commons-email-queue";
	private static final String SMARTTRADZ_TOPIC = "smarttradz.topic";

	public EmailQueueSender() {
		this.messageBody = new MessageBody<String>();
	}

	public EmailQueueSender(String body) {

		this.messageBody = new MessageBody<>(body);
	}

	@Override
	protected String getTopicExchageName() {
		return SMARTTRADZ_TOPIC;
	}

	@Override
	protected String getSenderQueueName() {
		return SENDER_QUEUE_NAME;
	}

}
