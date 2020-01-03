package com.as1124.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.as1124.spring.web.controller" })
public class WebConfig implements WebMvcConfigurer {

	@Bean("jspViewResolver")
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(InternalResourceView.class);
		resolver.setPrefix("/views/");
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setContentType("text/html;charset=utf-8");

		return resolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// 对静态资源的请求转发到Servlet容器中的默认Servlet进行处理，而不是使用DispatcherServlet进行处理
		// 例如请求 JSP 界面转到tomcat容器进行处理
		configurer.enable();
	}

}
