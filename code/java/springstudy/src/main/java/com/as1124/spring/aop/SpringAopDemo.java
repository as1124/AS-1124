package com.as1124.spring.aop;

import org.junit.Assert;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 演出：Spring AOP 能力。Spring 面向切面能力是Aspect J面向切面能力的一个子集。
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@EnableAspectJAutoProxy
@Configuration
public class SpringAopDemo {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringAopDemo.class)) {
			AspectAudience1 a = ctx.getBean("audienceA", AspectAudience1.class);
			Assert.assertNotNull(a);
			AspectAudience2 b = ctx.getBean("audienceB", AspectAudience2.class);
			Assert.assertNotNull(b);
			IPerformance one = ctx.getBean(IPerformance.class);
			System.out.println("IPerformance real class = " + one.getClass().getName());
			one.broadcastShowName();
			one.broadcastShowName("飞屋环游记");
			one.perform();
			ITicketSaler ticketSaler = ctx.getBean(ITicketSaler.class);

			// 动态引入，两个bean其实是同一个代理bean
			Assert.assertEquals(one, ticketSaler);
			((ITicketSaler) one).getAvailableTickets("aabcc");
		}
	}

	@Bean
	public IPerformance cratePerformance() {
		// ATTENTION 不能利用lambda传递匿名对象；与TicketSalerAspect要求冲突
		return new PerformanceImpl();
	}

	@Bean("audienceA")
	public AspectAudience1 audienceA() {
		return new AspectAudience1();
	}

	@Bean("audienceB")
	public AspectAudience2 audienceB() {
		return new AspectAudience2();
	}

	@Bean
	public AspectAudienceInAround audienceC() {
		return new AspectAudienceInAround();
	}

	@Bean
	public AspectTicketSaler ticketSalerAspect() {
		return new AspectTicketSaler();
	}

}
