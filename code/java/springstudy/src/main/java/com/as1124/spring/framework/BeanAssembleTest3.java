package com.as1124.spring.framework;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/configuration.xml")
//@ContextConfiguration(classes = SpringConfigWays.class)
public class BeanAssembleTest3 {

	@Autowired(required = false)
	@Qualifier("videoPlayerBean")
	private IMediaPlayer player;

	@Test
	public void testSpring() {
		assertNotNull(player);
		player.startPlay();
	}
}
