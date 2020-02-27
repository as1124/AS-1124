package com.as1124.spring.message.rpcserver;

import org.junit.Assert;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.stereotype.Repository;

import com.as1124.spring.message.jms.MessageServiceFrameworkRepository;
import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

/**
 * 基于 JMS 远程过程调用，
 * ATTENTION 必须指定{@link JmsInvokerServiceExporter} 作为消息处理机，否则调用过程无法完成
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
public class MQServiceServer {

	public static void main(String[] args) {
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(
				MQServiceServer.class.getPackage().getName())) {
			JmsInvokerServiceExporter exporterBean = context.getBean(JmsInvokerServiceExporter.class);
			Assert.assertNotNull(exporterBean);
			Object obj = exporterBean.getService();
			Assert.assertNotNull(obj);
			System.out.println("服务端装配 JMS 完成!!!");
		}
	}

	@Bean("jmsInvokerExporter")
	public static RemoteExporter createJMSInvokerExporter() {
		JmsInvokerServiceExporter exporterBean = new JmsInvokerServiceExporter();
		//		exporterBean.setMessageConverter(MessageServiceFrameworkRepository.createMessageConverter());
		exporterBean.setService(new JMSUserActionImpl());
		exporterBean.setServiceInterface(IUserAction.class);
		return exporterBean;
	}

	@Bean
	public static MessageListenerContainer createListenerContainer(JmsInvokerServiceExporter exporter) {
		// JmsInvokerServiceExporter 可以作为 MessageListener 的代理，处理消息在 onMessage() 方法中
		MessageListenerAdapter listener = new MessageListenerAdapter();
		listener.setDelegate(exporter);
		return MessageServiceFrameworkRepository.createMessageListenerContainer(listener);
	}

	public static class JMSUserActionImpl implements IUserAction {

		@Override
		public UserInfo findOne(int uid) {
			UserInfo user = new UserInfo(uid, "Spring 装配 JMS 服务");
			user.setAddress("消息来自服务端");
			return user;
		}

		@Override
		public int addUser(UserInfo user) {
			System.out.println("收到来自客户端消息：" + user.toString());
			return user.getId();
		}

		@Override
		public boolean updateUser(UserInfo user) {
			// do nothing
			return false;
		}

	}
}
