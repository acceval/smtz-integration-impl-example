package com.acceval.core.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;


public abstract class MessageQueueApplication implements RabbitListenerConfigurer {

	protected abstract String getQueueName();
	
	protected abstract String getTopicExchangeName();
	
//	protected abstract MessageReceiver getMessageReceiver();
    
    		
    @Bean
    Queue queue() {
        return new Queue(this.getQueueName(), false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(this.getTopicExchangeName());
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
    		
        return BindingBuilder.bind(queue).to(exchange).with(this.getQueueName());
    }
    
	//Comment all methods below and remove interface's implementation to use the default serialization/deserialization.
	
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}
}