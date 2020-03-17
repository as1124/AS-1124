package com.as1124.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * Learn and figure out the Proxy-Design-Pattern
 * <ol>
 * <li>静态代理: A和B实现接口C，B对外暴露并持有A对象引用，发生在B上对C的方法调用委托A实际执行
 * <li>JDK 动态代理: {@link Proxy}、{@link InvocationHandler}；动态生成代理类，基于接口方式
 * <li>CGlib 代理: {@link MethodInterceptor}、{@link Enhancer}；动态生成代理类，基于类继承方式
 * <li>Spring-Bean 实现接口时默认用JDK的动态代理; 没有实现接口时，用CGLib实现动态代理
 * <li>强制启用CGLib => &lt;aop:aspectj-autoproxy proxy-target-class="true" /&lt;
 * <li>JDK 8及之后动态代理效率高于CGLib；CGlib在创建 <code>Enhancer</code> 对象时较慢，以为是动态生成字节码的
 * <li>JDK基于反射机制，接口编程；代理类的类定义 => class $Proxy0 extends Proxy implements InterfaceA,... ;
 * 因此在Spring中wired时无法将JDK代理类型的bean装配给接口实现的类型
 * <li>CGlib 基于类继承机制，底层是基于asm框架实现，通过实现实现指定类的一个子类；=> $$EnhancerByCGLIB$$ extends SuperClass；
 * 所以不能是final修饰的类或方法
 * </ol>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IProxyDoc {

}
