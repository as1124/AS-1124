package com.as1124.spring.jmx;

import javax.management.DynamicMBean;
import javax.management.JMX;
import javax.management.MXBean;
import javax.management.modelmbean.ModelMBean;

import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.support.JmxUtils;

/**
 * JMX(Java managed extensions)
 * <ul>
 * <li>JMX构成：MBeanServer、MBean、Agent、Connection
 * <li>MBean的4种类型：Standard MBean、DynamicMBean、开放MBean、{@link ModelMBean 模型MBean}
 * <li>标记MBean的三种方式：接口名以MBean结尾，实现{@link DynamicMBean}接口，用{@link MXBean}注解；
 * {@link JmxUtils#isMBean(Class)}、{@link JMX#isMXBeanInterface(Class)}
 * </ul>
 * Spring 导出MBean的方式
 * <ul>
 * <li>通过{@link MBeanExporter}
 * <li>注解 {@link ManagedResource}、{@link ManagedAttribute} 等Spring-JMX相关注解
 * </ul>
 * MBean注册时命名冲突解决
 * <ul>
 * 
 * </ul>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IJmxDoc {

}
