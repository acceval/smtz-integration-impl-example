package com.acceval.core.amqp;

import org.springframework.amqp.core.Queue;

public interface QueueListener {
	
	public Queue getQueue();
	
	public String getName();
	
	public String getBeanName();


}
