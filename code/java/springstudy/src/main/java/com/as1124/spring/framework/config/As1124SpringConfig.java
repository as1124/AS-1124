package com.as1124.spring.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * <code>{@link Configuration}</code> 在不配置任何参数的情况下，
 * 默认是以当前包作为<code> base-package</code> 扫描当前包及其子包来发现所有的
 * <code>{@linkplain Component}</code>注解的bean类
 * <ul>
 * 	<li>basePackages : 配置扫描基础包, 字符串参数； <strong>影响重构，类型不安全</strong>
 * 	<li>basePackageClasses : 配置成对应类所在包作为基础包；<strong>适合重构，类型安全</strong>
 * </ul>
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
@ComponentScan(basePackages = { "com.as1124.spring.framework" })
public class As1124SpringConfig {

}
