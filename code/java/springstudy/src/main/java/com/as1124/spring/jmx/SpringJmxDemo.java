package com.as1124.spring.jmx;

import javax.management.MBeanServer;

import org.junit.Assert;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jmx.export.MBeanExporter;

/**
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class SpringJmxDemo {

	public static void main(String[] args) {
		showXMLConfig();

		showJavaConfig();
	}

	public static void showXMLConfig() {
		try (AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("/config/spring-mbean.xml")) {
			MBeanExporter exporterBean = ctx.getBean(MBeanExporter.class);
			Assert.assertNotNull(exporterBean);
			MBeanServer mBeanServer = ctx.getBean(MBeanServer.class);
			System.out.println(mBeanServer.toString() + "/ domain = " + mBeanServer.getDefaultDomain());
			System.out.println("MBean-count = " + mBeanServer.getMBeanCount());
		}
	}

	public static void showJavaConfig() {
		try (AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(SpringJmxRepository.class)) {
			MBeanExporter exporterBean = ctx.getBean(MBeanExporter.class);
			Assert.assertNotNull(exporterBean);
			MBeanServer mBeanServer = ctx.getBean(MBeanServer.class);
			System.out.println(mBeanServer.toString() + "/ domain = " + mBeanServer.getDefaultDomain());
			System.out.println("MBean-count = " + mBeanServer.getMBeanCount());
		}
	}
}
