package com.acceval.core.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

import com.acceval.core.CommonApplication;


public abstract class QueueListenerApplication extends CommonApplication {

	protected abstract String getQueueName();
	
	protected abstract String getExchangeName();
	
//	protected abstract MessageReceiver getMessageReceiver();
    
    		
    @Bean
    Queue queue() {
    		if (this.getQueueName() != null || !this.getQueueName().isEmpty()) {
    			return new Queue(this.getQueueName(), false);
    		} else {
    			return null;
    		}
    }

    @Bean
    TopicExchange exchange() {
    		if (this.getExchangeName() != null || !this.getExchangeName().isEmpty()) {
    			return new TopicExchange(this.getExchangeName());
    		} else {
    			return null;
    		}
    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//            MessageListenerAdapter listenerAdapter) {
//    	
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(this.getQueueName());
//        container.setMessageConverter(new JsonMessageConverter());
//        container.setMessageListener(listenerAdapter);        
//        return container;
//    }

    
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
    		if (this.getQueueName() != null || !this.getQueueName().isEmpty()) {
    			return BindingBuilder.bind(queue).to(exchange).with(this.getQueueName());
    		} else {
    			return null;
    		}
    }
    
}
