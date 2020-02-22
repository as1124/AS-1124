package com.as1124.spring.remote.http;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.as1124.spring.web.model.IUserAction;

/**
 * 导出普通服务为HTTP服务--->装配到Spring Context上下文中---->Autowired自动装配调用
 * <br/>
 * HUANG 限制：HttpInvoker要求client、server都是Spring应用
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
public class SpringHttpInvokerServer {

	@Bean("httpServiceExporter")
	public HttpInvokerServiceExporter createHttpExporter() {
		HttpInvokerServiceExporter serviceExporter = new HttpInvokerServiceExporter();
		serviceExporter.setServiceInterface(IUserAction.class);
		serviceExporter.setService(new UserHttpServiceImpl());
		return serviceExporter;
	}

	/**
	 * 配置Servlet-Mapping来来处理客户端调用Spring-Http服务
	 * 
	 * @return
	 */
	@Bean
	public HandlerMapping httpInvokerMapping() {
		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		Properties properties = new Properties();
		// key->是uri，value->是要处理对应url的spring bean的name
		properties.setProperty("/userHttpService", "httpServiceExporter");
		mapping.setMappings(properties);
		return mapping;
	}
}
