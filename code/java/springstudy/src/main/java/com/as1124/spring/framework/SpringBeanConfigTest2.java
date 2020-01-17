package com.as1124.spring.framework;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.as1124.spring.framework.bean.PixarVideo;
import com.as1124.spring.framework.bean.VideoPlayer;

/**
 * 测试Spring自动装配方式二：<br/>
 * {@link Bean} 注解： 要搭配 {@linkplain Configuration} 或 {@linkplain Repository} 注解一起使用
 * <br/><br/>
 * <strong>这是显式配置Bean:</strong> 通过方法创建{@linkplain Bean}实例并返回，不用 <code>@Component</code 去进行注解
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBeanConfigTest2.class)

//@Configuration
@Repository
public class SpringBeanConfigTest2 {

	@Autowired
	private IMediaPlayer mPlayer;

	@Test
	public void testSpring() {
		assertNotNull(mPlayer);
		mPlayer.startPlay();
	}

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
	@Bean(name = "videoPlayerBean")
	public IMediaPlayer getPlayer(IMedia media) {
		VideoPlayer player = new VideoPlayer();
		player.setVideo(media);
		return player;
	}
}
