package com.as1124.spring.framework.config;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.as1124.spring.framework.SpringBeanConfigTest;
import com.as1124.spring.framework.SpringBeanConfigTest2;

/**
 * Spring混合配置
 * <ul>
 * <li>@ComponentScan、@Component</li>
 * <li>@Bean、@Repository</li>
 * <li>XML 文件</li>
 * <li>@Import、@ImportResource</li>
 * </ul>
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
@Import({ SpringBeanConfigTest.class, SpringBeanConfigTest2.class })
@ImportResource("/config/spring-config.xml")
public class SpringConfigWays {

	// 将3个configuration组装成一个，一起加载
}
