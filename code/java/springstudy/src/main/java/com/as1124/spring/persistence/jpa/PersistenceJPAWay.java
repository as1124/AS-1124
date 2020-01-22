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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import com.as1124.spring.persistence.IPersistenceConstants;

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
 * 都实现了{@linkplain FactoryBean}接口<br/>，
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
 * <li>Spring-Data-JPA</li>
 * 需要向 <code>configuration</code>  注册 {@link EnableJpaRepositories}, 以便Spring启动时扫描 {@linkplain JpaRepository}
 * 的实现接口；此配置不会扫描子包<br/>
 * ~ 上下文需要注册名为<code> entityManagerFactory </code>的 EntityManagerFactory-Bean <br/>
 * ~ 上下文需要注册名为<code> transactionManager </code>的 TransactionManager-Bean
 * </ol>
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
//@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.as1124.spring.persistence.jpa")
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

	//	@Bean("appEMFBean")
	//	public LocalEntityManagerFactoryBean applicationEntityManagerFactory(JpaVendorAdapter jpaVendorAdapter) {
	//		LocalEntityManagerFactoryBean appEMFBean = new LocalEntityManagerFactoryBean();
	//		// persistence.xml 中配置的名称
	//		appEMFBean.setPersistenceUnitName("as1124_jpa_mysql");
	//		appEMFBean.setJpaVendorAdapter(jpaVendorAdapter);
	//
	//		// ATTENTION 需要依赖 Hibernate-core：为了提供PersistenceProvider
	//
	//		return appEMFBean;
	//	}

	/****************************************************************/
	// Spring JPA 要求ApplicationContext上下文必须包含名为 entityManagerFactory 的 EntityManagerFactory
	// Spring JAP 要求ApplicationContext上下文必须包含名为 transactionManager 的 TransactionManager

	@Primary
	@Bean("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean containerEntityManagerFactory(JpaVendorAdapter jpaVendorAdapter,
			@Qualifier(IPersistenceConstants.DBCP_JAVA) DataSource ds) {
		LocalContainerEntityManagerFactoryBean containerEMFBean = new LocalContainerEntityManagerFactoryBean();
		containerEMFBean.setDataSource(ds);
		containerEMFBean.setJpaVendorAdapter(jpaVendorAdapter);

		// 设置查找实体映射关系模型的包
		containerEMFBean.setPackagesToScan("com.as1124.spring.web.model");

		return containerEMFBean;
	}

	@Bean("transactionManager")
	public TransactionManager createTransactionManager(EntityManagerFactory emf,
			@Qualifier(IPersistenceConstants.DBCP_JAVA) DataSource dataSource) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(emf);
		transactionManager.setDataSource(dataSource);
		transactionManager.setTransactionSynchronization(
			AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION);

		return transactionManager;
	}

}
