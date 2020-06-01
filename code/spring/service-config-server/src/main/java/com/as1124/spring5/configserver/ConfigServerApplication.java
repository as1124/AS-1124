package com.as1124.spring5.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Spring 配置中心Module，注意以下几点
 * <ul>
 * <li><code>bootstrap.xml</code> 的作用: bootstrap.xml先于application.yml加载，支持从配置中心获取应用配置信息
 * <li>配置文件中profile设置成<code> native </code>的含义和作用
 * </ul>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
