package com.as1124.spring.framework.config;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Spring混合配置
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
@Import({ As1124SpringConfig.class, As1124SpringConfig2.class })
@ImportResource("/configuration.xml")
public class SpringConfigWays {

	// 将3个configuration组装成一个，一起加载
}
