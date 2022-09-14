package com.acceval.core.notification;

import org.springframework.stereotype.Component;

import com.acceval.core.amqp.MessageBody;
import com.acceval.core.amqp.QueueSender;

@Component
public class NotificationQueueSender extends QueueSender {

	private static final String SENDER_QUEUE_NAME = "commons-notification-queue";
	private static final String SMARTTRADZ_TOPIC = "smarttradz.topic";

	public NotificationQueueSender() {
		this.messageBody = new MessageBody<String>();
	}

	public NotificationQueueSender(String body) {

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
