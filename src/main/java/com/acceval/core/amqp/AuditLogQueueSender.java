package com.acceval.core.amqp;

import org.springframework.stereotype.Component;

@Component
public class AuditLogQueueSender extends QueueSender {

	/**
	 * @Deprecated use sendMessage(Object)
	 */
	@Override
	@Deprecated
	public void sendMessage() {
		// do nothing
	}

	@Override
	protected String getTopicExchageName() {
		return SMARTTRADZ_TOPIC;
	}

	@Override
	protected String getSenderQueueName() {
		return QUEUE_NAME_AUDITLOG;
	}

}
