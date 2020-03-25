package com.as1124.spring.framework.factorybean;

import org.junit.Assert;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Test for how to use FactoryBean
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class FactoryBeanDemo {

	public static void main(String[] args) {
		try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(FactoryBeanDemo.class)) {
			Object bean = ctx.getBean("createFactoryBean");
			Assert.assertNotNull(bean);
			Assert.assertEquals(ComputerBean.class, bean.getClass());
			System.out.println("It's right!");
		}
	}

	@Bean("createFactoryBean")
	public ComputerFactoryBean createFactoryBean() {
		return new ComputerFactoryBean();
	}
}
