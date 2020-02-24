package com.as1124.spring.rest;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

/**
 * 配置REST有两种：
 * <ul>
 * <li>内容协商机制：{@link ContentNegotiationConfigurer}、{@link ContentNegotiationManager} 来配置 {@link ContentNegotiatingViewResolver}；
 * 
 * <li>使用HTTP信息转换器: {@link RestController}、{@link RequestBody}、{@link ResponseBody}、{@link HttpMessageConverter}
 * </ul>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
public class SpringRestWebConfig {

//	@Bean("cnManager")
//	public ContentNegotiationManagerFactoryBean cnManagerFactory() {
//		ContentNegotiationManagerFactoryBean factoryBean = new ContentNegotiationManagerFactoryBean();
//		factoryBean.setDefaultContentType(MediaType.APPLICATION_JSON);
//
//		return factoryBean;
//	}

	/**
	 * {@link WebConfig#configureContentNegotiation(ContentNegotiationConfigurer)} 创建生成的 <code>ContentNegotiationManager</code>, 
	 * 在本 Repository 之后创建
	 * 
	 * @param cnManager
	 * @return
	 */
//	@Bean
//	@Qualifier("cnManager")
//	public ViewResolver contentNegotiationResolver(ContentNegotiationManager cnManager) {
//		ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
//		viewResolver.setContentNegotiationManager(cnManager);
//
//		return viewResolver;
//	}
	
}
