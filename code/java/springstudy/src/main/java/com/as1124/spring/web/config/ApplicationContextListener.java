package com.as1124.spring.web.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * 注册ContextListener，监听ApplicationContext生命周期变化
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class ApplicationContextListener implements ApplicationListener<ApplicationContextEvent> {

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		ApplicationContext context = event.getApplicationContext();
		System.out.println("[ApplicationContextListener] ApplicationContextEvent happend on ：" + context.toString());
		System.out.println(String.format("Context id = %s, hashCode = %d", context.getId(), context.hashCode()));
		System.out.println("[ApplicationContextListener] ApplicationEvent = " + event.toString());
	}

}
