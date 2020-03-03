package com.as1124.spring.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 观众：Spring AOP 示例中切面模型，关注点(切点)是演出。
 * <br>
 * <li>演出前<br/>
 * <li>演出结束后<br/>
 * 两个连接点
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Aspect
public class AspectAudience1 {

	/**
	 * 切点调用前调用
	 */
	@Before(value = "execution(* com.as1124.spring.aop.IPerformance.broadcastShowName(..))")
	public void volumeOffCellPhone() {
		System.out.println("[Before performance1] Everyon volumn cellphone off please!");
	}

	/**
	 * 切点调用前调用
	 */
	@Before(value = "execution(* IPerformance.broadcastShowName(..))")
	public void sitDown() {
		System.out.println("[Before performance1] Everyone sit down please!");
	}

	/**
	 * 切点调用返回后执行
	 */
	@AfterReturning(value = "execution(* IPerformance.perform(..))")
	public void applause() {
		System.out.println("[After performance1] Nice performance, Clap! Clap!!");
	}

	/**
	 * 切点调用时抛出异常时执行
	 */
	@AfterThrowing(value = "execution(* IPerformance.perform(..))")
	public void disappoint() {
		// 要求退款
		System.out.println("[After performance1] Awful performance, Demanding refund!!");
	}

}
