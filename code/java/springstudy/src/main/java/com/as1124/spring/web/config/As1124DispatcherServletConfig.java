package com.as1124.spring.web.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Spring MVC Java配置方式
 * {@linkplain AbstractAnnotationConfigDispatcherServletInitializer} 就是 {@linkplain WebApplicationInitializer}的实现
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class As1124DispatcherServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	/* 
	 * Spring Bean 配置类
	 */
	/**
	 * 返回带有{@link Configuration}注解的类，其中配置的Spring Bean 用来配置{@link ContextLoaderListener}
	 * 创建的应用上下文中的bean<br/>
	 * {@link ContextLoaderListener} 通常负责加载如： 驱动应用的中间层、数据层的组件
	 */
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootContextConfig.class };
	}

	@Override
	protected ApplicationContextInitializer<?>[] getRootApplicationContextInitializers() {
		ApplicationContextInitializer<?>[] initializer = new ApplicationContextInitializer<?>[1];
		initializer[0] = (ConfigurableApplicationContext context) -> {
			System.out.println("[As1124RootContextInitializer] Spring Root Context id = " + context.getId());
			System.out.println("[As1124RootContextInitializer] Spring Root Context hashCode = " + context.hashCode());
			context.addApplicationListener(new ApplicationContextListener());
			System.out.println("[As1124RootContextInitializer] Add ApplicationListener to Root Context");
		};
		return initializer;
	}

	/**
	 * 返回带有{@link Configuration}注解的类，其中配置的Spring Bean用来
	 * 定义{@link DispatcherServlet}应用上下文中的bean：即用作Servlet处理。<br/>
	 * {@link DispatcherServlet}负责加载如：控制器、视图解析器、处理映射器等 bean
	 */
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebContextConfig.class };
	}

	@Override
	protected ApplicationContextInitializer<?>[] getServletApplicationContextInitializers() {
		ApplicationContextInitializer<?>[] initializer = new ApplicationContextInitializer<?>[1];
		initializer[0] = (ConfigurableApplicationContext context) -> {
			System.out.println("[As1124WebContextInitializer] Spring Root Context id = " + context.getId());
			System.out.println("[As1124WebtContextInitializer] Spring Root Context hashCode = " + context.hashCode());
			context.addApplicationListener(new ApplicationContextListener());
			System.out.println("[As1124WebtContextInitializer] Add ApplicationListener to Web Context");
		};
		return initializer;
	}

	/* 
	 * 请求路径通配符，截获所有请求但不包括JSP<br/>
	 * ATTENTION 不能写成 <code>/*</code>，不然访问JSP时就返回源码
	 */
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		// registration 实际就是向ServletContext注册DispatcherServlet之后得到的封装对象
		// 其中包含了Tomcat的上下文Application以及请求Wrapper
		registration.setMultipartConfig(new MultipartConfigElement("/resources/uploaded"));
	}

}
