package com.as1124.spring.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring WVC 中Web-Context相关Bean配置信息
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.as1124.spring.web" }, includeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
public class WebContextConfig implements WebMvcConfigurer {

	@Bean("jspViewResolver")
	public ViewResolver createViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(InternalResourceView.class);
		resolver.setPrefix("/views/");
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setContentType("text/html;charset=utf-8");

		return resolver;
	}

	@Bean
	public WebMvcConfigurer crateSpringMvcConfigureAdapter() {
		return new WebMvcConfig();
	}
}
