package com.as1124.spring.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 应用启动时注册自己的Servlet、Filter，而不是只简单依靠{@link DispatcherServlet}
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class As1124ServletInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		Dynamic servlet = servletContext.addServlet("resource-servlet", ResourcesServlet.class);
		servlet.addMapping("/resource/action/*");
	}

}
