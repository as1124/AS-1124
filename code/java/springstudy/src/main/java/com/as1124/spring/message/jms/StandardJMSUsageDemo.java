package com.as1124.spring.message.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.support.JmsUtils;

/**
 * 通过标准JMS API 连接ActiveMQ 进行异步消息消息的收发
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class StandardJMSUsageDemo {

	public static void main(String[] args) {
		String instanceURL = "tcp://localhost:61616";
		jmsProducer(instanceURL);
		jmsConsumer(instanceURL);
	}

	/**
	 * 标准JMS框架联接消息服务器
	 */
	public static void jmsProducer(String instanceURL) {
		ConnectionFactory cf = new org.apache.activemq.ActiveMQConnectionFactory(instanceURL);
		Connection conn = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			conn = cf.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = new ActiveMQQueue("activemq.queqe");
			// 创建到消息队列的消息生产者
			producer = session.createProducer(destination);
			Message msg = session.createTextMessage("Hello JMS!! 我是欢哥");
			producer.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (producer != null) {
				JmsUtils.closeMessageProducer(producer);
			}
			if (session != null) {
				JmsUtils.closeSession(session);
			}
			if (conn != null) {
				JmsUtils.closeConnection(conn);
			}
		}
	}

	public static void jmsConsumer(String instanceURL) {
		ConnectionFactory cf = new org.apache.activemq.ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection conn = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
			conn = cf.createConnection();
			//HUANG 读取消息必须要调用 start()
			conn.start();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = new ActiveMQQueue("activemq.queqe");
			// 创建到消息队列的消息生产者
			consumer = session.createConsumer(destination);
			Message msg = consumer.receive();
			System.out.println("【ActiveMQ】接收到的消息类型 = " + msg.getClass().getName());
			if (TextMessage.class.isAssignableFrom(msg.getClass())) {
				System.out.println("【ActiveMQ】消息内容 = " + ((TextMessage) msg).getText());
			}
			conn.start();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (consumer != null) {
				JmsUtils.closeMessageConsumer(consumer);
			}
			if (session != null) {
				JmsUtils.closeSession(session);
			}
			if (conn != null) {
				JmsUtils.closeConnection(conn);
			}
		}
	}
}
