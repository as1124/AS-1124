package com.as1124.spring.web.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Configure Spring MVC environment, not the configure the web-context; this is
 * a part of web-context; the flow of how the setup works:
 * <br/>
 * <ol>
 * Web-MVC 配置启动过程
 * <li>{@link EnableWebMvc} 注解在定义时引入了 {@link DelegatingWebMvcConfiguration} 作为一个Component注入到了Context中
 * <li>Web-Context 实例化 {@link DelegatingWebMvcConfiguration}#setConfigurers(List), AutoWired 查找所有 {@link WebMvcConfigurer} 类型的Bean
 * <li>通过 {@link WebMvcConfigurationSupport#requestMappingHandlerAdapter()} 创建 {@link RequestMappingHandlerAdapter} Bean的时候处理了
 * WebMvcConfigurer 中的配置
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class WebMvcConfig implements WebMvcConfigurer {

	/*
	 * 启用J2EE 容器中的默认Servlet处理机制；例如Tomcat默认的web.xml中有配置一个 <code>default</code> 名称
	 * 的Servlet, {@linkplain org.apache.catalina.servlets.DefaultServlet}
	 * <br/>
	 * <h2>
	 * <b>对静态资源的请求转发到默认Servlet进行处理，而不是使用DispatcherServlet进行处理;
	 * 例如请求 JSP 界面转到tomcat容器进行处理<b/>，不然就会出现找不到页面的情况
	 * </h2>
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// 对静态资源的请求转发到Servlet容器中的默认Servlet进行处理，而不是使用DispatcherServlet进行处理
		// 例如请求 JSP 界面转到tomcat容器进行处理
		configurer.enable();
	}

	/**
	 * 注册Web request/response 消息数据类型转换器，这个方法的作用是：
	 * <p>
	 * <b>用来覆盖默认的 {@link HttpMessageConverter}: 如果向List添加了自己的Converter, 
	 * 则不会使用 {@linkplain WebMvcConfigurationSupport#addDefaultHttpMessageConverters} 中默认Converter了<b>
	 * </p>
	 * 
	 * @param converters 通常传递进来的 converters 是empty-list,
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 通常传递进来的 converters 是empty-list, 这个方法的作用
		boolean hasXmlConverter = false;
		for (HttpMessageConverter<?> msgConverter : converters) {
			if (Jaxb2RootElementHttpMessageConverter.class.isAssignableFrom(msgConverter.getClass())) {
				hasXmlConverter = true;
				break;
			}
		}
		if (!hasXmlConverter) {
			Jaxb2RootElementHttpMessageConverter xmlConverter = new Jaxb2RootElementHttpMessageConverter();
			xmlConverter.setDefaultCharset(Charset.forName("UTF-8"));
			//			converters.add(xmlConverter);
		}
	}
}
