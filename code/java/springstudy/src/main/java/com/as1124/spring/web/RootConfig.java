package com.as1124.spring.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 对{@link ComponentScan}注解引用的Spring Bean 进行了过滤：不包含注解值 = {@link EnableWebMvc}的类
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
@ComponentScan(basePackages = { "com.as1124.spring.web.business", "com.as1124.spring.persistence",
		"com.as1124.spring.remote" }, excludeFilters = {
				@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
public class RootConfig {

}
