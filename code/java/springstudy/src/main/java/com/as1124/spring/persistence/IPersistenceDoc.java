package com.as1124.spring.persistence;

import java.sql.Connection;
import java.sql.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring 后端数据操作
 * <ul>
 * 数据源类型
 * <li>{@link DriverManagerDataSource}：非池化管理，每次调用{@link DataSource#getConnection()}都会创建一个新的{@link Connection}对象
 * <li>{@link SimpleDriverDataSource}：非池化管理，需要用户自己处理{@link Driver}来获取Connection
 * <li>{@link SingleConnectionDataSource}：只维护一个Connection的数据源
 * <li>JNDI 数据源（托管于J2EE容器管理）
 * <li>连接池型数据源：C3p0、Apache DBCP、阿里 Druid
 * </ul>
 * <ul>
 * 持久化方式
 * <li>{@link JdbcTemplate}：没有事物处理过程，简单方便；缺点=>没有事物概念
 * <li>Standard JPA Framework
 * <li>Spring Data JPA Framework
 * <li>ORM Framework: Hibernate、MyBatis
 * </ul>
 * 
 * <ul>
 * JPA的实现厂商有：
 * <li>Hibernate JPA
 * <li>Eclipse link JPA
 * </ul>
 * <ol>
 * <li><code> FactoryBean </code></li>
 * Spring中 {@link LocalEntityManagerFactoryBean}、{@linkplain LocalContainerEntityManagerFactoryBean} 
 * 都实现了{@linkplain FactoryBean}接口<br/>
 * ~ <b>所有<code>FactoryBean</code>在Spring-Context中并不会将自生暴露为Bean
 * 而是提供类型为 {@linkplain FactoryBean#getObjectType()} 的Bean</b>；<br/>
 * ~ 获取方式{@linkplain FactoryBean#getObject()}
 * </li>
 * 
 * <li><code> BeanFactory </code></li>
 * {@linkplain BeanFactory} 用来管理Bean的生命周期以及<code> Scope </code>；返回的 Bean 是标准实例(prototype模式)还是单例模式;<br/>
 * ~ {@linkplain ListableBeanFactory} 管理的Bean是平级的<br/>
 * ~ {@linkplain HierarchicalBeanFactory} 查找Bean采用双亲委派查找机制，同 ClassLoader；子 BeanFactory 会覆盖父
 * BeanFactory中的同名Bean <br/>
 * 
 * <li>通过 {@link EntityManagerFactory} 获取 EntityManager 处理Entity，需要手动close; {@linkplain PersistenceContext} 
 * 注入的 EntityManager 可以不用调用 {@link EntityManager#close() close}</li>
 * 
 * <li>{@linkplain EntityManager} 的查询SQL都是基于<code> @Entity </code>域和对应的<code> Field </code>
 * 名称展开的而不是基于数据库表名、列名；这在使用Spring-JPA({@link JpaRepository}) 
 * 的{@linkplain Query 自定义查询}需要注意
 * </li>
 * 
 * <li>{@link PersistenceCotext} 能保证线程安全，{@link PersistenceUnit} 线程不安全</li>
 * 
 * <li>JPA 事物处理</li>
 * ~ 使用{@link PersistenceUnit} 注解的 Application 类型的 EntityManagerFactory时，事物处理是jdk的事物处理 => {@link javax.transaction.Transactional}
 * <br/>
 * ~ 使用{@link PersistenceContext} 注解的 Container 类型的 EntityManagerFactory时，事物处理则依赖于容器，例如
 * 使用的是Spring的上下文，则需要 => {@link org.springframework.transaction.annotation.Transactional}
 * 
 * <li>Spring 事物处理</li>
 * ~ {@link EnableTransactionManagement} 开启Spring上下文事物处理，此注解和 @Configuration、@Repository 一起使用才生效
 * <br/>
 * ~ 向Spring-Context 提供事物处理对象，{@link PlatformTransactionManager} 的实现Bean
 * ~ 注解事物处理类、方法：org.springframework.transaction.annotation.Transactional
 * 
 * <li>Spring-Data-JPA</li>
 * 需要向 <code>configuration</code>  注册 {@link EnableJpaRepositories}, 以便Spring启动时扫描 {@linkplain JpaRepository}
 * 的实现接口；此配置不会扫描子包<br/>
 * ~ 上下文需要注册名为<code> entityManagerFactory </code>的 EntityManagerFactory-Bean <br/>
 * ~ 上下文需要注册名为<code> transactionManager </code>的 TransactionManager-Bean <br/>
 * ~ 查询方式：<code> findByUsername(DML)、@Query、混合查询(Impl 结尾) </code> <br/>
 * ~ select s from UserInfo s where s.id >= ?1
 * ~ select s from UserInfo s where s.id >= :uid
 * </ol>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IPersistenceDoc {

}
