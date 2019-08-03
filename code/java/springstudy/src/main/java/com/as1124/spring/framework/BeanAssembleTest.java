package com.as1124.spring.framework;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.as1124.spring.framework.config.As1124SpringConfig;

/**
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
