package com.as1124.spring.framework.runtime;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.as1124.spring.framework.IMedia;

/**
 * 测试运行时注入装配信息，读取配置文件、变量占位符
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExpressiveConfig.class)
public class BeanAssembleTest4 {

	@Autowired(required = true)
	private IMedia videoInfo;

	@Test
	public void testSpring() {
		assertNotNull(videoInfo);
		videoInfo.getMediaContent();
	}
}
