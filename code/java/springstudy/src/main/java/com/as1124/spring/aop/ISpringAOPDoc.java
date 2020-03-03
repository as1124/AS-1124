package com.as1124.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring 面向切面编程，它是Aspect J面向切面能力的一个子集。<b>目的在于不侵入原方法执行的过程而进行观察、通知。</b>
 * <ul>
 * AOP的关键要素
 * <li>切点(pointcut): 原程序执行过程中关注的一个点作为介入点
 * <li>通知(Advice)：调用已定义的事前、事后（返回时、异常时）、环绕时的处理方法称为通知
 * <li>环绕通知(Around)：通过{@link ProceedingJoinPoint#proceed()} 自己选择原切点程序的执行时机, 如果不调用， 原程序逻辑则中断
 * <li>联接点(join point)：即对象的时机：类加载、构造方法调用、field值变化、方法执行前、方法返回、方法调用异常等
 * <li>切面：以上三点组成一个完整的切面
 * </ul>
 * <b>Spring AOP 只支持方法级别的连接点 </b>
 * <ul>
 * AspectJ 切点表达式语法: execution(* IPerformance.broadcastShowName(..))
 * <li>execution：连接点名称（执行时触发通知）；args(限定参数)，with，target(限定目标对象为指定类型)，annotation(限定目标带有指定注解)
 * <li>使用args时指定的参数名要与Advice的参数名一直，相当于获取方法执行的参数
 * <li>* 号：方法返回类型匹配，* 代表不关注
 * <li>IPerformance.broadcastShowName：连接点对象，Spring只支持方法; 类名或包含package名称的完全限定名
 * <li>.. ：表示不关注方法的参数（方法重载时有用）
 * </ul>
 * <ul>
 * 动态引入新API
 * <li>场景：拿不到源码或无法修改所有接口实现类的情况下又希望原接口支持新API
 * <li>{@link DeclareParents} + AspectJ，在原接口的Aspect代理中引入新接口API，这样相当于代理同时完成了两个接口的调用委托
 * <li>特别注意这种情况不是和匿名类
 * </ul>
 * <ul>
 * 相关注解
 * <li>{@link Aspect}
 * <li>{@link EnableAspectJAutoProxy}
 * </ul>
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface ISpringAOPDoc {

}
