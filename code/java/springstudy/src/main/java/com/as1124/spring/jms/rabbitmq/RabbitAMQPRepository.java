package com.as1124.spring.jms.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

/**
 * HUANG 此处填写 class 信息
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
public class RabbitAMQPRepository {

	@Bean
	public ConnectionFactory createConnectionFactory() {
		CachingConnectionFactory connFactory = new CachingConnectionFactory();
		connFactory.setHost("localhost");
		connFactory.setPort(5672);
		connFactory.setUsername("guest");
		connFactory.setPassword("guest");
		// 使用 Connection 模式
		connFactory.setCacheMode(CacheMode.CONNECTION);
		// 设置每个Connection 下拥有的Channel 数量
		connFactory.setChannelCacheSize(10);
		// 设置获取Channel超时时间
		connFactory.setChannelCheckoutTimeout(30 * 1000);
		connFactory.setConnectionCacheSize(5);
		connFactory.setConnectionLimit(30 * 1000);
		return connFactory;
	}

	@Bean
	public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setEncoding("UTF-8");
		template.setDefaultReceiveQueue("spring.queue");
		// 设置需要交互的 exchange 名称
		//		template.setExchange("spring.exchange");
		// 设置需要交互队列的匹配key
		//		template.setRoutingKey("");
		// 关闭事物
		template.setChannelTransacted(false);
		template.setMandatory(false);
		return template;
	}

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				RabbitAMQPRepository.class.getPackage().getName())) {
			RabbitTemplate template = context.getBean(RabbitTemplate.class);
			template.convertAndSend("Hello Spring-Rabbit!!");
			Object obj = template.receiveAndConvert();
			if (obj != null) {
				System.out.println(obj);
			}
		}
	}
}
