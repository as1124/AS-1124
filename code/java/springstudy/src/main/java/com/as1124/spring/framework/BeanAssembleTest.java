package com.as1124.spring.framework;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.as1124.spring.framework.config.As1124SpringConfig;

/**
 * <strong>
 * Spring Bean装配方式有三种：
 * </strong>
 * <ol>
 * <li><strong>自动装配方式：</strong><br/>
 * {@link Autowired}：注解用于自动装配bean, 扫描完 {@linkplain Component} 后自动进行类型匹配, 
 * 设置<code>required = false</code>用于自动装配失败是不抛出Exception。所以自动装配依赖于 
 * <code>@Component</code> 注解，没有注解的类就无法被自动装配
 * </li>
 * <li><strong>Java装配方式：</strong><br/>
 * {@link Bean} 注解, 提供创建方法提供Bean
 * </li>
 * <li><strong>XML配置装配方式：</strong><br/>
 * </li>
 * </ol>
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = As1124SpringConfig.class)
public class BeanAssembleTest {

	@Autowired(required = false)
	private IMediaPlayer mediaPlayer;

	@Test
	public void testSpring() {
		assertNotNull(mediaPlayer);
		mediaPlayer.startPlay();
	}
}
