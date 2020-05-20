package com.as1124.spring.redis;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * HUANG 此处填写 class 信息
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */ 
@Configuration
public class SpringRedisConfiguration {

	@Bean
	public RedisConnectionFactory createJedisConnection() {
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		redisConfig.setHostName("127.0.0.1");
		redisConfig.setPort(6379);

		return new JedisConnectionFactory(redisConfig);
	}

	@Bean
	public RedisConnectionFactory createLettuceConnection() {
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		redisConfig.setHostName("127.0.0.1");
		redisConfig.setPort(6379);

		return new LettuceConnectionFactory(redisConfig);
	}
	
	public void createRedisTemplate() {
		
	}

	public static void main(String[] args) {
		try(ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SpringRedisConfiguration.class)){
			SpringRedisConfiguration redisTest = ctx.getBean(SpringRedisConfiguration.class);
			RedisConnectionFactory redisFactory = ctx.getBean(RedisConnectionFactory.class);
			RedisConnection connection = redisFactory.getConnection();
//			connection.get
		}
	}

}
