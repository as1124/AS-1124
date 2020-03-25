package com.as1124.spring.framework.scope;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
public class BeanScopeTest {

	@Autowired
	private ScopeSingletonBean singletonA;

	@Autowired
	private ScopePrototypeBean prototypeA;

	@Autowired
	private ScopeSingletonWithInnerPrototypeBean complexScopeA;

	public static void main(String[] args) {
		try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				BeanScopeTest.class.getPackage().getName())) {
			BeanScopeTest test = ctx.getBean(BeanScopeTest.class);
			test.testSingleton(ctx.getBean(ScopeSingletonBean.class));
			test.testPrototype(ctx.getBean(ScopePrototypeBean.class));
			test.testComplex(ctx.getBean(ScopeSingletonWithInnerPrototypeBean.class));
		}
	}

	public void testSingleton(ScopeSingletonBean b) {
		Assert.assertEquals(singletonA, b);
		if (singletonA == b) {
			System.out.println("Singleton 结果正确");
		}
	}

	public void testPrototype(ScopePrototypeBean b) {
		Assert.assertNotNull(prototypeA);
		if (!prototypeA.equals(b)) {
			System.out.println("Prototype 结果正确");
		}
	}

	public void testComplex(ScopeSingletonWithInnerPrototypeBean b) {
		Assert.assertNotNull(complexScopeA);
		Assert.assertEquals(complexScopeA, b);
		Assert.assertEquals(complexScopeA.getPrototypeBean(), b.getPrototypeBean());
	}

}
