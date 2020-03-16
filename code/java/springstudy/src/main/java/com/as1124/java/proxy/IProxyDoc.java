package com.as1124.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * Learn and figure out the Proxy-Design-Pattern
 * <ol>
 * <li>静态代理: A和B实现接口C，B对外暴露并持有A对象引用，B上对C的方法调用委托A实际执行
 * <li>JDK 动态代理: {@link Proxy}、{@link InvocationHandler}
 * <li>CGlib 代理: {@link MethodInterceptor}、{@link Enhancer}
 * <li>SpringBean 实现接口时，用JDK的动态代理; 没有实现接口时，用CGLib实现动态代理
 * <li>强制启用CGLib => &lt;aop:aspectj-autoproxy proxy-target-class="true" /&lt;
 * <li>JDK 8及之后动态代理效率高于CGLib；
 * <li>JDK基于反射机制，接口编程；CGlib是基于继承机制
 * </ol>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IProxyDoc {

}
