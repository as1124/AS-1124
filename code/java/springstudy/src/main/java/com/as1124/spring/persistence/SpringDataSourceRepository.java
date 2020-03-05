package com.as1124.spring.persistence;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Repository;

/**
 * 几种常见配置数据源的方式
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@PropertySource(value = "classpath:/config/dbinfo.properties")
@Repository
public class SpringDataSourceRepository {

	@Autowired
	private Environment springEnv;

	/**
	 * 使用普通的、不具有池管理功能的JDBC数据源；每次调用{@link DriverManagerDataSource#getConnection()}
	 * 都会创建新的连接 {@link java.sql.Connection}
	 * 
	 * @return
	 */
	@Bean(IPersistenceConstants.JDBC_JAVA)
	public DataSource createJdbcDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(springEnv.getProperty("driverClassName"));
		dataSource.setUrl(springEnv.getProperty("url"));
		dataSource.setUsername(springEnv.getProperty("username"));
		dataSource.setPassword(springEnv.getProperty("password"));
		return dataSource;
	}

	/**
	 * 查找配置在Web容器环境中的JNDI数据源
	 * 
	 * @return
	 */
	@Bean(IPersistenceConstants.JNDI_JAVA1)
	public DataSource lookupWebJndiDataSource() {
		// 通过 Java 内置 API 查找JNDI数据源
		try {
			Context context = new InitialContext();
			//ATTENTION 实际配置在 Tomcat 的context.xml 中JNDI的名字是：jdbc/jndi-ds
			Object obj = context.lookup("java:comp/env/jdbc/jndids");
			if (obj != null && DataSource.class.isAssignableFrom(obj.getClass())) {
				return (DataSource) obj;
			}
		} catch (NamingException e) {
			System.err.println("Error while finding Java-Naming-Resource for JNDI datasource!!");
		}
		return null;
	}

	/**
	 * 通过 Spring API 查找配置在Web容器中的JNDI数据源，此处要{@link JndiObjectFactoryBean#setResourceRef(boolean)}
	 * 
	 * @return
	 */
	@Bean(IPersistenceConstants.JNDI_JAVA2)
	public JndiObjectFactoryBean createJavaJndiDataSource() {
		JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
		factoryBean.setJndiName("jdbc/jndids");
		factoryBean.setLookupOnStartup(false);
		factoryBean.setProxyInterface(DataSource.class);
		factoryBean.setResourceRef(true);

		return factoryBean;
	}

	/**
	 * Apache Commons DBCP 数据库连接池
	 * 
	 * @return
	 */
	@Bean(IPersistenceConstants.DBCP_JAVA)
	public BasicDataSource createDbcpDataSource() {
		try {
			Properties pros = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/config/dbinfo.properties"));
			return BasicDataSourceFactory.createDataSource(pros);
		} catch (Exception e) {
			System.err.println("Create DBCP instance failed! Maybe properties is null.");
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * {@link JdbcTemplate} 操作数据
	 * 
	 * @param ds
	 * @return
	 */
	@Bean
	public JdbcOperations jdbcTemplate(@Qualifier(IPersistenceConstants.DBCP_JAVA) DataSource ds) {
		Assert.assertNotNull(ds);
		return new JdbcTemplate(ds);
	}
}
