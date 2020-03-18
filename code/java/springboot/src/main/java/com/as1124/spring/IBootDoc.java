package com.as1124.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Conditional;

/**
 * Spring Boot 四大优势:
 * <ol>
 * <li>依赖管理：Maven、Gradle的依赖传递；指定需要的 parent <code>**-startter-**</code>
 * <li>自动配置
 * <li>Client 命令管理工具
 * <li>Actuator End-point管理
 * </ol>
 * 
 * 自动配置
 * <br/>
 * <ol>
 * <li>spring-boot-autoconfigure.jar，实现原理是Spring的条件Bean => {@link Conditional}
 * <li>用户显式声明的配置优先，在没有的情况下加载自动配置 => {@link ConditionalOnMissingBean}, {@link ConditionalOnClass} 等
 * <li>简化属性配置 => application.properties、application.yml
 * </ol>
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IBootDoc {

}
