package com.as1124.spring.jms;

import java.util.Date;

import javax.jms.Message;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.junit.Assert;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import com.as1124.spring.web.model.UserInfo;

/**
 * 学习使用 Spring JmsTemplate 与 JMS Broker服务进行交互
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class SpringJmsTemplateDemo {

	public static void main(String[] args) throws InterruptedException {
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(
				SpringJmsTemplateDemo.class.getPackage().getName())) {
			JmsTemplate jmsBean = context.getBean(JmsTemplate.class);
			Assert.assertNotNull(jmsBean);
			MessageConverter converter = jmsBean.getMessageConverter();
			System.out.println("当前消息转换器 =" + converter.getClass().getName());
			UserInfo user = new UserInfo(1, "JMS 消息传输");
			user.setAddress("天上人间");
			user.setBirthday(new Date(System.currentTimeMillis()));
			if (SimpleMessageConverter.class.isAssignableFrom(converter.getClass())) {
				((ActiveMQConnectionFactory) jmsBean.getConnectionFactory()).setTrustAllPackages(true);
				jmsBean.convertAndSend(user);
			} else if (MappingJackson2MessageConverter.class.isAssignableFrom(converter.getClass())) {
				jmsBean.convertAndSend(user, (Message msg) -> {
					msg.setStringProperty("class-type", "user");
					return msg;
				});
			} else {
				return;
			}

			Thread.sleep(2000);

			Object msg = jmsBean.receiveAndConvert();
			System.out.println("收到消息类型为: " + msg.getClass().getName());
			System.out.println("收到消息内容为: " + msg.toString());
		}
	}
}
