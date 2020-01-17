package com.as1124.spring.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Repository;

/**
 * 使用标准JPA框架进行对象持久化处理;
 * JPA的实现厂商有：
 * <ul>
 * <li>Hibernate JPA
 * <li>Eclipse link JPA
 * </ul>
 * <ol>
 * <li><code> FactoryBean </code></li>
 * Spring中 {@link LocalEntityManagerFactoryBean}、{@linkplain LocalContainerEntityManagerFactoryBean} 
 * 都实现了{@linkplain FactoryBean}接口，<b>所有<code>FactoryBean</code>在Spring-Context中并不会将自生暴露为Bean
 * 而是提供类型为 {@linkplain FactoryBean#getObjectType()} 的Bean</b>；<br/>
 * 获取方式{@linkplain FactoryBean#getObject()}
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
 * <li>Spring-Data-JPA</li>
 * 
 * </ol>
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */

@Repository
public class PersistenceJPAWay {

	// 标准 JPA 只是一套规范，没有提供具体实现

	@Bean("jpaVendorAdapter")
	public JpaVendorAdapter createJpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setGenerateDdl(false);
		jpaVendorAdapter.setShowSql(true);
		return jpaVendorAdapter;
	}

	@Bean("appEMFBean")
	public LocalEntityManagerFactoryBean applicationEntityManagerFactory(JpaVendorAdapter jpaVendorAdapter) {
		LocalEntityManagerFactoryBean appEMFBean = new LocalEntityManagerFactoryBean();
		// persistence.xml 中配置的名称
		appEMFBean.setPersistenceUnitName("as1124_jpa_mysql");
		appEMFBean.setJpaVendorAdapter(jpaVendorAdapter);

		// ATTENTION 需要依赖 Hibernate-core：为了提供PersistenceProvider

		return appEMFBean;
	}

	@Bean("containerEMFBean")
	@Primary
	public LocalContainerEntityManagerFactoryBean containerEntityManagerFactory(
			@Qualifier("java-jdbc-injection") DataSource ds, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean containerEMFBean = new LocalContainerEntityManagerFactoryBean();
		containerEMFBean.setDataSource(ds);
		containerEMFBean.setJpaVendorAdapter(jpaVendorAdapter);

		// 设置查找实体映射关系模型的包
		containerEMFBean.setPackagesToScan("com.as1124.spring.web.model");

		return containerEMFBean;
	}

}
