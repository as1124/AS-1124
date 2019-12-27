package com.as1124.spring.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Spring MVC Java配置方式
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class As1124SpringApplication extends AbstractAnnotationConfigDispatcherServletInitializer {

	/* 
	 * Spring Bean 配置类
	 */
	/**
	 * 返回带有{@link Configuration}注解的类，其中配置的Spring Bean 用来配置{@link ContextLoaderListener}
	 * 创建的应用上下文中的bean<br/>
	 * {@link ContextLoaderListener} 通常负责加载如： 驱动应用的中间层、数据层的组件
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	/**
	 * 返回带有{@link Configuration}注解的类，其中配置的Spring Bean用来
	 * 定义{@link DispatcherServlet}应用上下文中的bean：即用作Servlet处理。<br/>
	 * {@link DispatcherServlet}负责加载如：控制器、视图解析器、处理映射器等 bean
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	/* 
	 * 请求路径通配符，截获所有请求但不包括JSP
	 * 
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/*" };
	}

}
