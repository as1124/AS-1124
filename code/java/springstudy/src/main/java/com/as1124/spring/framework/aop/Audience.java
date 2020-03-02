package com.as1124.spring.framework.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 观众：Spring AOP 示例中切面模型，关注点是演出。
 * <br>
 * <li>演出前<br/>
 * <li>演出时<br/>
 * <li>演出结束后<br/>
 * 三个连接点
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Aspect
public class Audience {

	/**
	 * 切点调用前调用
	 */
	@Before(value = "execution(* *.framework.aop.IPerformance.perform(..))")
	public void volumeOffCellPhone() {
		System.out.println("[Before performance] Everyon volumn cellphone off please!");
	}

	/**
	 * 切点调用前调用
	 */
	@Before("execution(* *.framework.aop.IPerformance.perform(..))")
	public void sitDown() {
		System.out.println("[Before performance] Everyone sit down please!");
	}

	/**
	 * 切点调用返回后执行
	 */
	@AfterReturning("execution(* *.framework.aop.IPerformance.perform(..))")
	public void applause() {
		System.out.println("[After performance] Nice performance, Clap! Clap!!");
	}

	/**
	 * 切点调用时抛出异常时执行
	 */
	@AfterThrowing("execution(* *.framework.aop.IPerformance.perform(..))")
	public void disappoint() {
		// 要求退款
		System.out.println("[After performance] Awful performance, Demanding refund!!");
	}

}
