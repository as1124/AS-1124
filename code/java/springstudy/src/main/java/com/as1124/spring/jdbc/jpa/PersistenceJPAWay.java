package com.as1124.spring.jdbc.jpa;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class PersistenceJPAWay {

	@Bean
	public LocalEntityManagerFactoryBean applicationManagerFactoryBean() {
		LocalEntityManagerFactoryBean applicationEMFBean = new LocalEntityManagerFactoryBean();
		applicationEMFBean.setPersistenceUnitName("");

		return applicationEMFBean;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean containerManagerFactoryBean(DataSource ds,
			JpaVendorAdapter jpaVendor) {
		LocalContainerEntityManagerFactoryBean containerEMFBean = new LocalContainerEntityManagerFactoryBean();
		containerEMFBean.setDataSource(ds);
		containerEMFBean.setJpaVendorAdapter(jpaVendor);

		// 扫描 package 下包含JPA注解的实体
		containerEMFBean.setPackagesToScan("");

		return containerEMFBean;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendor = new HibernateJpaVendorAdapter();
		jpaVendor.setDatabase(Database.MYSQL);
		jpaVendor.setShowSql(true);
		jpaVendor.setGenerateDdl(false);

		return jpaVendor;
	}

	@Bean
	public JndiObjectFactoryBean getEntityManagerFactory() {
		JndiObjectFactoryBean jndiObjFactory = new JndiObjectFactoryBean();
		jndiObjFactory.setJndiName("jdbc/as1124_ds");
		return jndiObjFactory;
	}
}
