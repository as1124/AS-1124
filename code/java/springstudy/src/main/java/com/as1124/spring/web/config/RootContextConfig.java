package com.as1124.spring.web.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 对{@link ComponentScan}注解引用的Spring Bean 进行了过滤：不包含注解值 = {@link EnableWebMvc}的类
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
@ComponentScan(basePackages = { "com.as1124.spring.web", "com.as1124.spring.persistence",
		"com.as1124.spring.rest" }, excludeFilters = {
				@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
public class RootContextConfig {

	/**
	 * 关注于 BeanFactory的生命周期
	 * @return
	 */
	@Bean("As1124BeanFactoryProcessor")
	public BeanFactoryPostProcessor createBeanFactoryProcessor() {
		return beanFactory -> System.out.println("[BeanFactoryProcessor] " + beanFactory.toString());
	}

	/**
	 * 关注于 Bean 的生命周期
	 * @return
	 */
	@Bean("As1124BeanProcessor")
	public BeanPostProcessor createBeanProcessor() {
		return new RootContextBeanPostProcessor();
	}

	/**
	 * ApplicationContext 初始化完成后，状态是active可用后才会调用，设置给开发者获取
	 * @return
	 */
	@Bean("As1124ApplicationContextAware")
	public ApplicationContextAware createContextAware() {
		return (ApplicationContext context) -> {
			System.out.println("[ApplicationContextAware]" + context.toString());
			System.out.println(String.format("[ApplicationContextAware] appName is %s, id is %s, hashCode is %d",
				context.getApplicationName(), context.getId(), context.hashCode()));
		};
	}

	@Bean("As1124BeanFactoryAware")
	public BeanFactoryAware crateBeanFactoryAware() {
		return (BeanFactory beanFactory) -> {
			System.out.println("[BeanFactoryAware] " + beanFactory.toString());
			System.out.println(String.format("[BeanFactoryAware] class is %s, hashCode is %d",
				beanFactory.getClass().getName(), beanFactory.hashCode()));
		};
	}

	@Bean("As1124BeanNameAware")
	public BeanNameAware createBeanNameAware() {
		return beanName -> System.out.println("[BeanNameAware] Create Bean " + beanName);
	}

}