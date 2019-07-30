package com.as1124.spring.framework.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.as1124.spring.framework.bean.LovedHeros;

/**
 * 用来测试Profile环境
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
public class LovedHerosConfig {

	@Bean
	@Profile("REAL")
	public LovedHeros chinaHeros() {
		LovedHeros h = new LovedHeros();
		h.setName("秦惠文王");
		h.setStory("纵横捭阖");
		return h;
	}

	@Bean
	@Profile("SD")
	public LovedHeros marvelHeros() {
		LovedHeros h = new LovedHeros();
		h.setName("Thor");
		h.setStory("A fatty Man!!");
		return h;
	}
}
