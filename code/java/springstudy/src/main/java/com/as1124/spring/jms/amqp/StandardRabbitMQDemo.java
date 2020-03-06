package com.as1124.spring.jms.amqp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;

/**
 * 演示使用标准RabbitMQ API 的连个例子
 * <ul>
 * <li>使用默认exchange、指定queue进行消息的收发
 * <li>Publish/subscribe 模型：日志管理为例，一个Producer，2个Consumer（一个输出到文件，一个打印到控制台）
 * </ul>
 * 1. 先有接收方 --> 建立发送方并发送消息
 * <br/>
 * 2. 默认情况下创建的与exchange的binding在对应thread死亡后就会注销，此时发送消息是接收不到的
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class StandardRabbitMQDemo {

	protected static final String QUEUE_NAME = "rabbitmq.queue";

	protected static final String EXCHANGE_NAME = "rabbitmq.exchange";

	protected static final int DEFAULT_PORT = 5672;

	public static void main(String[] args) {
		basicSendAndReceive();

		publishAndSubscribeDemo();
	}

	public static void basicSendAndReceive() {
		ConnectionFactory conFactory = new ConnectionFactory();
		conFactory.setHost("localhost");
		conFactory.setPort(DEFAULT_PORT);
		// 1. Start server for listening, receive message from a named queue
		try (Connection connection = conFactory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			channel.basicConsume(QUEUE_NAME, (String consumerTag, Delivery delivery) -> {
				String message = new String(delivery.getBody(), Charset.forName("UTF-8"));
				System.out.println("RabbitMQ 收到消息 = " + message);
			}, (consumerTag) -> {
				// Cancel 不做处理
			});
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}

		// 2. Send message from client to default exchange!
		try (Connection connection = conFactory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			String message = "Hello World!";
			// 没有指定 exchange 名称：使用 default exchange 将消息路由到 queue
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println("RabbitMQ Sent a message to default exchange has done!! ");

	}

	public static void publishAndSubscribeDemo() {
		// 1. Two Consumer read from different owned queue.
		Thread a = new Thread(() -> {
			readFromQueue();
		}, "Consumer-A");
		Thread b = new Thread(() -> {
			readFromQueue();
		}, "Consumer-B");
		a.start();
		b.start();

		// 2. Send a message to a named exchange
		ConnectionFactory conFactory = new ConnectionFactory();
		conFactory.setHost("localhost");
		conFactory.setPort(DEFAULT_PORT);
		try (Connection connection = conFactory.newConnection(); Channel channel = connection.createChannel()) {
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
			channel.basicPublish(EXCHANGE_NAME, "", null, "Show how to use exchange(Publish/Subscribe)!".getBytes());
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println("RabbitMQ Sent a message to " + EXCHANGE_NAME);

		System.out.println("等待接收----");
	}

	private static void readFromQueue() {
		ConnectionFactory conFactory = new ConnectionFactory();
		conFactory.setHost("localhost");
		conFactory.setPort(DEFAULT_PORT);
		try (Connection connection = conFactory.newConnection(); Channel channel = connection.createChannel()) {
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, "");
			channel.basicConsume(queueName, true, (String consumerTag, Delivery delivery) -> {
				String message = new String(delivery.getBody(), Charset.forName("UTF-8"));
				System.out.println(Thread.currentThread().getName() + " RabbitMQ 收到消息 = " + message);
			}, (consumerTag) -> {
				// Cancel 不做处理
			});
			while (true) {
				// 这个接收Thread不能死亡
				Thread.sleep(2000);
			}
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
