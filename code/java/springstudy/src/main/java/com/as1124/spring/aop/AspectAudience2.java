package com.as1124.spring.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 功能和{@link AspectAudience1} 一样，为了演示切面不同的编写方式
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Aspect
public class AspectAudience2 {

	/**
	 * 定义切点（确切说应该叫引入切点，为了编写方便）
	 */
	@Pointcut("execution(* IPerformance.broadcastShowName(String)) && args(showName)")
	public void beforePerformance(String showName) {
		// 空方法即可，并不需要实现；仅作为注解依赖存在
	}

	/**
	 * 切点调用前调用
	 */
	@Before("beforePerformance(showName)")
	public void volumeOffCellPhone(String showName) {
		System.out.println("[Before performance2] Everyon volumn cellphone off please!");
	}

	/**
	 * 切点调用前调用
	 */
	@Before("beforePerformance(showName)")
	public void sitDown(String showName) {
		System.out.println("[Before performance2] Everyone sit down please!");
	}

	/**
	 * 切点调用返回后执行
	 */
	@AfterReturning("execution(* IPerformance.perform())")
	public void applause() {
		System.out.println("[After performance2] Nice performance, Clap! Clap!!");
	}

	/**
	 * 切点调用时抛出异常时执行
	 */
	@AfterThrowing("execution(* IPerformance.perform())")
	public void disappoint() {
		// 要求退款
		System.out.println("[After performance2] Awful performance, Demanding refund!!");
	}

}
