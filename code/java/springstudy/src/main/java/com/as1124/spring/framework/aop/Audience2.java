package com.as1124.spring.framework.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 功能和{@link Audience} 一样，为了演示切面不同的编写方式
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Aspect
public class Audience2 {

	/**
	 * 定义切点
	 */
	@Pointcut("execution(* *.framework.aop.IPerformance.perform(..))")
	public void showPerformance() {
		// 空方法即可，并不需要实现；仅作为注解依赖存在
	}

	/**
	 * 切点调用前调用
	 */
	@Before("showPerformance()")
	public void volumeOffCellPhone() {
		System.out.println("[Before performance] Everyon volumn cellphone off please!");
	}

	/**
	 * 切点调用前调用
	 */
	@Before("showPerformance()")
	public void sitDown() {
		System.out.println("[Before performance] Everyone sit down please!");
	}

	/**
	 * 切点调用返回后执行
	 */
	@AfterReturning("showPerformance()")
	public void applause() {
		System.out.println("[After performance] Nice performance, Clap! Clap!!");
	}

	/**
	 * 切点调用时抛出异常时执行
	 */
	@AfterThrowing("showPerformance()")
	public void disappoint() {
		// 要求退款
		System.out.println("[After performance] Awful performance, Demanding refund!!");
	}

}
