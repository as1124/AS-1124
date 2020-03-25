package com.as1124.spring.framework.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.junit.Assert;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Constructor -> Set property -> PostConstruct -> afterPropertiesSet -> PreDestory -> Disposable
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Component
public class BeanLifecycleDemo implements InitializingBean, DisposableBean {

	private ApplicationContext ctx;

	private Environment envir;

	public static void main(String[] args) {
		try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(BeanLifecycleDemo.class)) {
			BeanLifecycleDemo bean = ctx.getBean(BeanLifecycleDemo.class);
			Assert.assertNotNull(bean);
		}
	}

	@Autowired
	public BeanLifecycleDemo(ApplicationContext appContext) {
		ctx = appContext;
		System.out.println("[BanLifecycleDemo] constructor");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("[BeanLifecycleDemo] afterProperties");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("[BeanLifecycleDemo] dispose");
	}

	@Autowired
	public void setEnvir(Environment envir) {
		this.envir = envir;
		System.out.println("[BeanLifecycleDemo] setEnvir");
	}

	@PostConstruct
	public void jsrOnConstruct() {
		System.out.println("[BeanLifecycleDemo] @PostConstruct");
	}

	@PreDestroy
	public void jsrPreDestory() {
		System.out.println("[BeanLifecycleDemo] @PreDestroy");
	}

	@Override
	public String toString() {
		if (ctx != null && envir != null) {
			return super.toString();
		} else {
			return "BeanLifecycleDemo is not OK";
		}
	}
}
