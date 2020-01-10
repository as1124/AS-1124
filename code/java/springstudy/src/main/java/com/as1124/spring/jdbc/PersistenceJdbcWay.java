package com.as1124.spring.jdbc;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Spring Java 型配置JDBC数据源{@link DataSource}，然后装配成 {@link JdbcTemplate} 进行数据库的 CRUD 操作
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
public class PersistenceJdbcWay {

	@Bean("java-jdbc-injection")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/spring");
		dataSource.setUsername("root");
		dataSource.setPassword("Root1234");
		return dataSource;
	}

	@Bean("java-dbcp-injection")
	public BasicDataSource getDBCPDatasource() {
		try {
			Properties pros = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/config/dbinfo.properties"));
			return BasicDataSourceFactory.createDataSource(pros);
		} catch (Exception e) {
			System.err.println("Create DBCP instance failed! Maybe properties is null.");
		}
		return null;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(@Autowired @Qualifier("java-jdbc-injection") DataSource ds) {
		Assert.assertNotNull(ds);
		return new JdbcTemplate(ds);
	}

}
