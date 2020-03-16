package com.as1124.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * Learn and figure out the Proxy-Design-Pattern
 * <ol>
 * <li>静态代理:
 * <li>JDK 动态代理: {@link Proxy}、{@link InvocationHandler}
 * <li>CGlib 代理: {@link MethodInterceptor}、{@link Enhancer}
 * <li>SpringBean 实现接口时，用JDK的动态代理
 * <li>SpringBean 没有实现接口时，用CGLib实现动态代理
 * <li>强制启动CGLib => &lt;aop:aspectj-autoproxy proxy-target-class="true" /&lt;
 * <li>JDK 8及之后动态代理效率高于CGLib
 * </ol>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IProxyDoc {

}
