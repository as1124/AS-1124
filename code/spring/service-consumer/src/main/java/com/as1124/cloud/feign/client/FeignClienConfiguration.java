package com.as1124.cloud.feign.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class FeignClienConfiguration {

	/**
	 * 给Feign-Client配置日志级别
	 * <p>
	 * <li>NONE: 不记录任何信息
	 * <li>BASIC: 仅记录请求方法、URL和响应状态和执行时间
	 * <li>HEADERS: BASIC信息、请求和响应头信息
	 * <li>FULL: 记录全部详细信息
	 * </p>
	 * @return
	 */
	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
