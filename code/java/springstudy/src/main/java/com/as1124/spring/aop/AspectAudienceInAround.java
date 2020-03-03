package com.as1124.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 创建环绕切面通知：通过{@link ProceedingJoinPoint#proceed()} 自己选择原切点程序的执行时机
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Aspect
public class AspectAudienceInAround {

	@Pointcut("execution(* IPerformance.perform(..))")
	public void showPerformance() {
		// can be empty
	}

	@Around("showPerformance()")
	public void watchPerformance(ProceedingJoinPoint joinPoint) {
		try {
			System.out.println("[Around-Advice Before] Everyone volume off cellphone please!!");
			System.out.println("[Around-Advice Before] Please sit down");

			// 执行切点对应的方法，即：返回通知者继续执行
			joinPoint.proceed();

			System.out.println("[Around-Advice After] Nice, Clap, Clap!!");
		} catch (Throwable e) {
			System.out.println("[Around-Advice After] Auful Show, Demanding refund");
		}
	}
}
