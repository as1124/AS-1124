package com.as1124.spring.persistence.jpa;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import com.as1124.spring.persistence.IPersistenceConstants;

/**
 * JPA 使用示例
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@EnableTransactionManagement
@Repository
//@EnableJpaRepositories(basePackages = "com.as1124.spring.persistence.jpa")
public class JPARepository {

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
		appEMFBean.setJpaDialect(new HibernateJpaDialect());

		// ATTENTION 需要依赖 Hibernate-core：为了提供PersistenceProvider
		appEMFBean.setPersistenceProvider(new HibernatePersistenceProvider());

		return appEMFBean;
	}

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
	public PlatformTransactionManager createTransactionManager(EntityManagerFactory emf,
			@Qualifier(IPersistenceConstants.DBCP_JAVA) DataSource dataSource) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(emf);
		transactionManager.setDataSource(dataSource);
		transactionManager.setTransactionSynchronization(
			AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION);
		return transactionManager;
	}

}
