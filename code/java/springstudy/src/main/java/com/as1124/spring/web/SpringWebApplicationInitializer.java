package com.as1124.spring.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.stereotype.Controller;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 应用启动时注册自己的Servlet、Filter，而不是只简单依靠{@link DispatcherServlet} 与  {@link Controller} 的组合
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class SpringWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		Dynamic servlet = servletContext.addServlet("resource-servlet", StandardRequestServlet.class);
		servlet.addMapping("/resource/action/*");
	}

}
