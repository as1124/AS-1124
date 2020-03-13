package com.as1124.spring.web;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.HandlesTypes;

import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * <ol>
 * Spring MVC 容器启动挂载及初始化
 * <li>主入口 => {@link SpringServletContainerInitializer#onStartup(Set, ServletContext)}, 这是基于Servlet 3.0之后标准实现，
 * J2EE容器查找并发现 {@link ServletContainerInitializer}的实现，然后委派这些Initialize来初始化Web应用上下文，
 * 也就是{@link ServletContext}
 * <br/>
 * <li>Spring会通过{@link HandlesTypes}挂载所有 {@link WebApplicationInitializer}接口的实现，来进行ServletContext的配置;
 * 这里注册了{@link ContextLoaderListener}将ServlectContext的生命周期和Spring-MVC的声明周期进行绑定
 * <br/>
 * <li>默认通过{@link AbstractDispatcherServletInitializer}实现类来配置 {@link DispatcherServlet}；这是Spring的默认Servlet
 * </br>
 * <li> 过程中会初始化两个{@link WebApplicationContext}: 一个处理应用业务的Root-Context，一个处理Servlet-Web的 Web-Context
 * <br/>
 * <li>Root-Context中声明的Bean会先于Web-Context中声明的Bean加载，所以配置Web上下文的Bean需要在Root-Context声明
 * <br/>
 * <li>Tomcat 执行{@link ServletContextListener}.contextInitialized(),此时处理BeanFactory 进行Bean加载
 * </ol>
 * 
 * <ul>
 * Spring Bean 加载
 * <li>
 * 
 * </ul>
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface ISpringMvcDoc {

}
