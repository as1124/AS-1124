package com.as1124.spring.message.rpcclient;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.MessageTransformer;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.junit.Assert;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.stereotype.Repository;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

@Repository
public class MQServiceClient {

	@Bean("jmsUserService")
	public static JmsInvokerProxyFactoryBean createJMSFactoryBean() {
		JmsInvokerProxyFactoryBean factoryBean = new JmsInvokerProxyFactoryBean();
		ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory();
		connFactory.setTransformer(new MessageTransformer() {

			@Override
			public Message producerTransform(Session session, MessageProducer producer, Message message)
					throws JMSException {
				message.setStringProperty("class-type", "user");
				return message;
			}

			@Override
			public Message consumerTransform(Session session, MessageConsumer consumer, Message message)
					throws JMSException {
				return null;
			}
		});
		connFactory.setBrokerURL("tcp://localhost:61616");
		connFactory.setTrustAllPackages(true);
		factoryBean.setConnectionFactory(connFactory);
		factoryBean.setQueueName("activemq.queqe");
		factoryBean.setServiceInterface(IUserAction.class);
		return factoryBean;
	}

	public static void main(String[] args) throws InterruptedException {
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(
				MQServiceClient.class.getPackage().getName())) {
			MQServiceClient clientBean = context.getBean(MQServiceClient.class);
			Assert.assertNotNull(clientBean);
			Object obj = context.getBean("jmsUserService");
			if (obj != null && IUserAction.class.isAssignableFrom(obj.getClass())) {
				IUserAction userService = (IUserAction) obj;
				UserInfo user = new UserInfo(124, "Spring 装配 JMS 服务");
				user.setAddress("消息来自客户端");
				userService.addUser(user);
				System.out.println("通过JMS服务发送消息完成: " + user);
				Thread.sleep(2000);
				System.out.println("通过JMS服务接收消息：" + userService.findOne(0));

			}
		}
	}

}
