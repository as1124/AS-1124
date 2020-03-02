package com.as1124.spring.framework.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@EnableAspectJAutoProxy
@Repository
public class PerformanceImpl {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				PerformanceImpl.class.getPackage().getName())) {
			IPerformance one = ctx.getBean(IPerformance.class);
			one.perform();
			Audience a = ctx.getBean("audienceA", Audience.class);
			Audience2 b = ctx.getBean("audienceB", Audience2.class);
			a.volumeOffCellPhone();
			b.showPerformance();
		}
	}

	@Bean
	public IPerformance cratePerformance() {
		return () -> {
			System.out.println("【美食从天而降】");
		};
	}

	@Bean("audienceA")
	public Audience audienceA() {
		return new Audience();
	}

	@Bean("audienceB")
	public Audience2 audienceB() {
		return new Audience2();
	}
}
