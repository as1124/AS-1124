package com.as1124.spring.message.jms;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

/**
 * <p>
 * JAVA、Spring消息框架：
 * <br/>
 * —> 同步消息：RMI，JAX-WS，HTTP 消息处理都是同步的
 * <br/>
 * —> 异步消息协议：JMS(Java Message Service)，Advance Message Queue Protocol；处理框架：ActiveMQ，RabbitMQ，RocketMQ
 * <br/>
 * </p>
 * 一、标准 JMS
 * <ul>
 * <li>{@link ConnectionFactory 联接工厂} -> {@link Connection 创建联接} -> {@link Session 获取会话}；有点类似于 JDBC 操作数据库
 * <li>创建消息目的地：{@link Destination} ：有两种类型 => {@link Queue 队列}，{@link Topic 主题}
 * <li>{@link MessageProducer} 生产者，向Broker {@link Destination} 发送 {@link Message}
 * <li>{@link MessageConsumer} 消费者，从Broker Destination 处获取 Message
 * </ul>
 * 二、Spring JmsTemplate
 * <ul>
 * <li>Spring {@link JmsTemplate} 省去了样板代码的处理，直接关注于 send 和 receive 消息就好了
 * <li><b>{@link JmsTemplate#receive()} 方法都是同步阻塞的方法</b>；即：JmsTemplate 是异步发送消息（不阻塞），接收消息时阻塞
 * <li>向Spring-Context注册 {@link MessageListenerContainer}，取代 receive() 收取消息，实现异步通知、不阻塞的目的
 * <li>{@link MessageConverter}：JmsTemplate 默认使用的是 {@link SimpleMessageConverter}，支持{@link Serializable} 对象传输; 
 * 但是需要指定白名单 {@link ActiveMQConnectionFactory#setTrustedPackages(java.util.List)}，
 * 或者手动将对象包装为 {@link BytesMessage} 类型的消息（http://activemq.apache.org/objectmessage.html）
 * <li>{@link MappingJackson2MessageConverter} 支持JSON形式对象序列化，但是需要指定<code>typeIdPropertyName 或 typeIdMappings</code>；
 * 也就意味着不能自动进行Class 类型推断，需要在传输消息的时候通过 {@link MessagePostProcessor} 写出 Property
 * </ul>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IMessageDoc {

}
