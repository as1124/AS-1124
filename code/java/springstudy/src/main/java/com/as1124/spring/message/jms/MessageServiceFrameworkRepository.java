package com.as1124.spring.message.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.as1124.spring.rest.pojo.BookXMLPojo;
import com.as1124.spring.web.model.UserInfo;

/**
 * JMS以及市面上常用的消息处理框架使用示例
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
public class MessageServiceFrameworkRepository {

	@Bean("jsonJMSConverter")
	public MessageConverter createMessageConverter() {
		MappingJackson2MessageConverter msgConverter = new MappingJackson2MessageConverter();
		Map<String, Class<?>> typeMapping = new HashMap<>();
		typeMapping.put("user", UserInfo.class);
		typeMapping.put("book", BookXMLPojo.class);
		msgConverter.setTypeIdMappings(typeMapping);
		msgConverter.setTypeIdPropertyName("class-type");
		msgConverter.setEncoding("UTF-8");

		return msgConverter;
	}

	@Bean("jmsTemplate4ActiveMQ")
	@Qualifier("jsonJMSConverter")
	public JmsTemplate template4ActiveMQ(@Nullable MessageConverter msgConverter) {
		ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory();
		connFactory.setBrokerURL("tcp://localhost:61616");

		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connFactory);
		template.setDefaultDestinationName("activemq.queqe");
		if (msgConverter != null) {
			// 默认使用的是 SimpleMessageConverter
			template.setMessageConverter(msgConverter);
		}

		return template;
	}

	/**
	 * 默认 JmsTemplate在receive 消息时是同步阻塞的，为了达成异步目的则该用消息驱动进行消息的处理；
	 * 即：当消息产生时会自动调用注册当 {@link MessageListener#onMessage(Message)} 进行处理
	 * @return
	 */
	@Bean("messageListener4ActiveMQ")
	public MessageListenerContainer createMessageListenerContainer() {
		SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
		ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory();
		connFactory.setBrokerURL("tcp://localhost:61616");
		listenerContainer.setConnectionFactory(connFactory);
		listenerContainer.setDestinationName("activemq.queqe");
		MessageListener listener = (Message msg) -> {
			try {
				System.out.println("【MessageListener】消息类型：" + msg.getClass().getName() + ", 映射类="
						+ msg.getStringProperty("class-type"));
			} catch (JMSException e) {
				e.printStackTrace();
			}
		};
		listenerContainer.setMessageListener(listener);
		listenerContainer.setAutoStartup(true);
		return listenerContainer;
	}

	@ExceptionHandler(value = JmsException.class)
	public void handlerJmsException(JmsException ex) {
		System.out.println("【消息中间件异常】" + ex.getMessage());
		ex.printStackTrace();
	}

}
