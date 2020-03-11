package com.as1124.spring.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

/**
 * <strong>
 * 一：Spring Bean三种装配方式：
 * </strong>
 * <ol>
 * <li><strong>自动装配方式：隐式装配</strong><br/>
 * {@link Autowired}：注解用于自动装配bean, 扫描完 <code>@</code>{@linkplain Component} 后自动进行类型匹配, 
 * 设置<code>required = false </code>取消强制自动装配，失败时不抛出Exception。所有自动装配依赖于 <code>@Component</code> 注解
 * 或者XML显示申明Bean，  没有注解的类或预先声明的Bean就无法被自动装配
 * </li>
 * <li><strong>Java装配方式：显示装配</strong><br/>
 * {@link Bean} 注解, 提供方法创建Bean
 * </li>
 * <li><strong>XML配置装配方式：</strong><br/>
 * </li>
 * </ol>
 * 
 * <strong>
 * 二：上下文Bean环境配置：
 * </strong>
 * <ol>
 * <li><code>{@link Configuration}</code> 在不配置任何参数的情况下，默认是以当前包作为<code> base-package</code> 
 * 扫描当前包及其子包来发现所有的Bean
 * <li><code>{@linkplain ComponentScan}</code> <br/>
 * <code>basePackages</code>: 配置扫描基础包, 字符串参数； <strong>影响重构，类型不安全</strong><br/>
 * <code>basePackageClasses</code>: 配置成对应类所在包作为基础包；<strong>适合重构，类型安全</strong>
 * <li>配置xml文件
 * <li>{@link Import}, {@link ImportResource}: 混合配置，引入<code>Configuration</code>相关类，xml资源
 * </ol>
 * 
 * 
 * <strong>
 * 三：自动装配过程中歧义冲突解决：
 * </strong><br/>
 * 当存在符合装配条件的多个可选bean时
 * <ol>
 * <li>{@link Primary} 设置首选Bean</li>
 * <li>{@link Conditional} 条件话初始Bean，使运行唯一；例如check当前Profile
 * <li>{@link Qualifier} 装配限定; 既可以用在定义Bean时限定修饰，也可用于装备时处理歧义
 * </ol>
 * 
 * 
 * <strong>
 * 四：Bean的作用域：{@link Scope} 注解<br/>
 * When <u><code>type-level</code></u> used with {@link Component}<br/>
 * When <u><code>method-level</code></u> used with {@link Bean}
 * </strong><br/>
 * 问题根源：默认情况下Spring中的Bean都是单例存在的，然而很多时候需要对象的状态，
 * 此时单例重用就会造成对象污染
 * <ol>
 * <li>{@link ConfigurableBeanFactory#SCOPE_SINGLETON} ：整个应用中单例作用域，Spring默认情况</li>
 * <li>{@link ConfigurableBeanFactory#SCOPE_PROTOTYPE} ：原型作用域, 每次都创建新的实例
 * <li>{@link WebApplicationContext#SCOPE_SESSION}：在Web应用中为每个会话创建一个Bean实例，生命周期与Session同步
 * <li>{@link WebApplicationContext#SCOPE_REQUEST}：在Web应用中为每个请求创建一个Bean实例
 * <li>{@link WebApplicationContext#SCOPE_APPLICATION}：WebApplicationContext时存在，生命周期与ServletContext同步
 * </ol>
 * 
 * 
 * <strong>
 * 五：Bean的多环境兼容配置：{@link Profile} 注解<br/>
 * 譬如测试环境创建基于测试环境的Bean，生产环境创建基于生产环境的Bean，而此时Bean其实类型等描述
 * 信息都一直除了内容，此时解决办法就可以用<code>Profile</code>
 * </strong><br/>
 * <ol>
 * 激活特定 <code>Profile</code>信息
 * <li>{@link ActiveProfiles} 注解</li>
 * <li>运行时上下文环境 {@link Environment#acceptsProfiles(org.springframework.core.env.Profiles)}
 * </ol>
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface ISpringDoc1 {

}
