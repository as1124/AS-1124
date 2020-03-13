package com.as1124.spring.web.config;

import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class RootContextBeanPostProcessor implements DestructionAwareBeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		System.out.println("[RootContextBeanPostProcessor] start create bean: " + beanName);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		System.out.println("[RootContextBeanPostProcessor] finished bean: " + beanName);
		return bean;
	}

	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName) {
		System.out.println("[RootContextBeanPostProcessor] Destruction bean: " + beanName);
	}
}
