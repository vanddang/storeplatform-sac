/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.util;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;

import com.rabbitmq.client.Channel;

/**
 * <p>
 * MQ를 사용하지 않도록 RabbitTemplate 예외처리 객체.
 * 사용처 : staging 서버
 * </p>
 * Updated on : 2014. 10. 31 Updated by : 임근대, SK 플래닛.
 */
public class NoOpRabbitTemplate extends RabbitTemplate {
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#setMessagePropertiesConverter(org.springframework.amqp.rabbit.support.MessagePropertiesConverter)
	 */
	@Override
	public void setMessagePropertiesConverter(
			MessagePropertiesConverter messagePropertiesConverter) {
		//super.setMessagePropertiesConverter(messagePropertiesConverter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.amqp.rabbit.connection.RabbitAccessor#
	 * setConnectionFactory
	 * (org.springframework.amqp.rabbit.connection.ConnectionFactory)
	 */
	@Override
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		// super.setConnectionFactory(connectionFactory);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.connection.RabbitAccessor#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		//super.afterPropertiesSet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.amqp.rabbit.core.RabbitTemplate#convertAndSend(java
	 * .lang.Object, org.springframework.amqp.core.MessagePostProcessor)
	 */
	@Override
	public void convertAndSend(Object message,
			MessagePostProcessor messagePostProcessor) throws AmqpException {
		// super.convertAndSend(message, messagePostProcessor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.amqp.rabbit.core.RabbitTemplate#convertAndSend(java
	 * .lang.String, java.lang.String, java.lang.Object,
	 * org.springframework.amqp.core.MessagePostProcessor,
	 * org.springframework.amqp.rabbit.support.CorrelationData)
	 */

	@Override
	public void convertAndSend(String exchange, String routingKey,
			Object message, MessagePostProcessor messagePostProcessor,
			CorrelationData correlationData) throws AmqpException {
		//super.convertAndSend(exchange, routingKey, message, messagePostProcessor, correlationData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.amqp.rabbit.core.RabbitTemplate#convertAndSend(java
	 * .lang.String, java.lang.String, java.lang.Object,
	 * org.springframework.amqp.rabbit.support.CorrelationData)
	 */

	@Override
	public void convertAndSend(String exchange, String routingKey,
			Object object, CorrelationData corrationData) throws AmqpException {
		//super.convertAndSend(exchange, routingKey, object, corrationData);
	}

	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#convertAndSend(java.lang.Object)
	 */
	@Override
	public void convertAndSend(Object object) throws AmqpException {
		//super.convertAndSend(object);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#convertAndSend(java.lang.String, java.lang.String, java.lang.Object, org.springframework.amqp.core.MessagePostProcessor)
	 */
	@Override
	public void convertAndSend(String exchange, String routingKey,
			Object message, MessagePostProcessor messagePostProcessor)
			throws AmqpException {
		//super.convertAndSend(exchange, routingKey, message, messagePostProcessor);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#convertAndSend(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void convertAndSend(String exchange, String routingKey, Object object)
			throws AmqpException {
		//super.convertAndSend(exchange, routingKey, object);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#convertAndSend(java.lang.String, java.lang.Object, org.springframework.amqp.core.MessagePostProcessor)
	 */
	@Override
	public void convertAndSend(String routingKey, Object message,
			MessagePostProcessor messagePostProcessor) throws AmqpException {
		//super.convertAndSend(routingKey, message, messagePostProcessor);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#convertAndSend(java.lang.String, java.lang.Object, org.springframework.amqp.core.MessagePostProcessor, org.springframework.amqp.rabbit.support.CorrelationData)
	 */
	@Override
	public void convertAndSend(String routingKey, Object message,
			MessagePostProcessor messagePostProcessor,
			CorrelationData correlationData) throws AmqpException {
		//super.convertAndSend(routingKey, message, messagePostProcessor, correlationData);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#convertAndSend(java.lang.String, java.lang.Object)
	 */
	@Override
	public void convertAndSend(String routingKey, Object object)
			throws AmqpException {
		//super.convertAndSend(routingKey, object);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#convertAndSend(java.lang.String, java.lang.Object, org.springframework.amqp.rabbit.support.CorrelationData)
	 */
	@Override
	public void convertAndSend(String routingKey, Object object,
			CorrelationData correlationData) throws AmqpException {
		//super.convertAndSend(routingKey, object, correlationData);
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#send(java.lang.String, java.lang.String, org.springframework.amqp.core.Message, org.springframework.amqp.rabbit.support.CorrelationData)
	 */
	@Override
	public void send(String exchange, String routingKey, Message message,
			CorrelationData correlationData) throws AmqpException {
		//super.send(exchange, routingKey, message, correlationData);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#doSend(com.rabbitmq.client.Channel, java.lang.String, java.lang.String, org.springframework.amqp.core.Message, org.springframework.amqp.rabbit.support.CorrelationData)
	 */
	@Override
	protected void doSend(Channel channel, String exchange, String routingKey,
			Message message, CorrelationData correlationData) throws Exception {
		//super.doSend(channel, exchange, routingKey, message, correlationData);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#doSendAndReceive(java.lang.String, java.lang.String, org.springframework.amqp.core.Message)
	 */
	@Override
	protected Message doSendAndReceive(String exchange, String routingKey,
			Message message) {
		//return super.doSendAndReceive(exchange, routingKey, message);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#doSendAndReceiveWithFixed(java.lang.String, java.lang.String, org.springframework.amqp.core.Message)
	 */
	@Override
	protected Message doSendAndReceiveWithFixed(String exchange,
			String routingKey, Message message) {
		return null;
		//return super.doSendAndReceiveWithFixed(exchange, routingKey, message);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate#doSendAndReceiveWithTemporary(java.lang.String, java.lang.String, org.springframework.amqp.core.Message)
	 */
	@Override
	protected Message doSendAndReceiveWithTemporary(String exchange,
			String routingKey, Message message) {
		//return super.doSendAndReceiveWithTemporary(exchange, routingKey, message);
		return null;
	}
}
