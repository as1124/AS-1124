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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

/**
 * 几种类型数据源创建示例
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
public class DataSourceBeans {

	@Bean
	public JdbcOperations jdbcTemplate(@Autowired @Qualifier("java-jdbc-injection") DataSource ds) {
		Assert.assertNotNull(ds);
		return new JdbcTemplate(ds);
	}

	/**
	 * 使用普通的、不具有池管理功能的JDBC数据源；每次调用{@link DriverManagerDataSource#getConnection()}
	 * 都会创建新的连接 {@link java.sql.Connection}
	 * 
	 * @return
	 */
	@Bean("java-jdbc-injection")
	public DataSource createJdbcDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/spring");
		dataSource.setUsername("root");
		dataSource.setPassword("Root1234");
		return dataSource;
	}

	/**
	 * 查找配置在XML环境中的JNDI数据源
	 * 
	 * @return
	 */
	@Bean("java-jndi-injection")
	public DataSource createXmlJndiDataSource() {
		// 通过 Java 内置 API 查找JNDI数据源
		try {
			Context context = new InitialContext();
			//ATTENTION 实际配置在Tomcat context.xml 中JNDI的名字伟：jdbc/jndi-ds
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
	 * DBCP 数据库连接池
	 * 
	 * @return
	 */
	@Bean("java-dbcp-injection")
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
}
