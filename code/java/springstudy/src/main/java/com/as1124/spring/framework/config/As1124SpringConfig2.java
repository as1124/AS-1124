package com.as1124.spring.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.as1124.spring.framework.IMedia;
import com.as1124.spring.framework.IMediaPlayer;
import com.as1124.spring.framework.PixarVideo;
import com.as1124.spring.framework.VideoPlayer;

/**
 * 配置方式二：<br/><br/>
 * 显式配置bean: 通过方法创建bean实例并返回，不用 <code>@Component</code 去进行注解，取而代之用{@link Bean}
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
public class As1124SpringConfig2 {

	/**
	 * 装配没有依赖的Bean
	 * @return
	 */
	@Bean(name = "videoBean")
	public IMedia getVideo() {
		return new PixarVideo();
	}

	/**
	 * 装配依赖于类型为{@link IMedia} Bean的<code>IMediaPlayer</code> Bean
	 * @param media
	 * @return
	 */
	@Bean(name = "playerBean")
	public IMediaPlayer getPlayer(IMedia media) {
		VideoPlayer player = new VideoPlayer();
		player.setVideo(media);
		return player;
	}
}