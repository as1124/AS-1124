package com.as1124.spring.framework.runtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.as1124.spring.framework.IMedia;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
@PropertySource("classpath:/com/as1124/spring/framework/runtime/app.properties")
public class ExpressiveConfig {

	@Autowired
	private Environment env;

	@Bean
	public IMedia createRuntimeBean() {
		VideoInfo video = new VideoInfo();
		video.setTitle(env.getRequiredProperty("video.name"));
		video.setDesc(env.getProperty("video.desc", "空描述"));
		return video;
	}
}
