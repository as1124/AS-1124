package com.as1124.spring.framework;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试Spring自动装配方式一：<br/>
 * {@link ComponentScan} 注解： 扫描指定包及子包下的 {@linkplain Component} 注解
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBeanConfigTest.class)

@Configuration
@ComponentScan(basePackages = { "com.as1124.spring.framework.bean" })
public class SpringBeanConfigTest {

	@Autowired
	private IMediaPlayer mediaPlayer;

	@Test
	public void testSpring() {
		assertNotNull(mediaPlayer);
		mediaPlayer.startPlay();
	}
}
