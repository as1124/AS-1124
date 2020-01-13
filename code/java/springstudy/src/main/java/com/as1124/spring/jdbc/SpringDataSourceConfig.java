package com.as1124.spring.jdbc;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mchange.v2.c3p0.PooledDataSource;

/**
 * 常用数据源类型及Spring支持的配置方式：
 * <ul>
 * <li>JDBC 型数据源：XML / JAVA</li>
 * <li>JNDI 型数据源：XML / JAVA （两者都基于Web容器上下文环境配置的数据源进行查找）：环境依赖</li>
 * <li>数据库连接池：C3P0, DBCP, 阿里 Druid</li>
 * </ul>
 * 
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@ContextConfiguration(classes = SpringDataSourceConfig.class)
@ImportResource("classpath:/config/spring-datasource.xml")
@ComponentScan(basePackages = "com.as1124.spring.jdbc")

@Controller
@RequestMapping("/jdbc")
public class SpringDataSourceConfig {

	/**
	 * 注入XML配置的JDBC数据源
	 */
	@Autowired
	@Qualifier("xml-jdbc-injection")
	private DataSource xmlJdbcDataSource;

	/**
	 * 注入JAVA配置的JDBC数据源
	 */
	@Autowired
	@Qualifier("java-jdbc-injection")
	private DataSource javaJdbcDataSource;

	@Autowired(required = false)
	@Qualifier("xml-jndi-factory")
	private JndiObjectFactoryBean jndiHolder;

	@Autowired(required = false)
	@Qualifier("jndi-injection")
	private DataSource jndiDataSource;

	@Autowired
	@Qualifier("xml-dbcp-injection")
	private BasicDataSource xmlDBCP;

	@Autowired
	@Qualifier("java-dbcp-injection")
	private BasicDataSource javaDBCP;

	@Autowired
	@Qualifier("xml-c3p0-injection")
	private PooledDataSource xmlC3p0;

	public void configSpringDatasource() {
		Assert.assertNotNull(xmlJdbcDataSource);
		Assert.assertNotNull(javaJdbcDataSource);
		Assert.assertNotEquals(xmlJdbcDataSource, javaJdbcDataSource);

		Assert.assertNotNull(xmlDBCP);
		Assert.assertNotNull(javaDBCP);
		Assert.assertNotEquals(xmlDBCP, javaDBCP);

		Assert.assertNotNull(jndiDataSource);
		Assert.assertNotNull(jndiHolder);
		Object obj = jndiHolder.getObject();
		Assert.assertNotEquals(obj, jndiDataSource);

		Assert.assertNotNull(xmlC3p0);
	}

	@GetMapping("/ways")
	public void testSpringDataSource() {
		configSpringDatasource();
	}

	@GetMapping("/jdbctemplate")
	public void testJdbcTemplatePersistence(@Autowired PersistenceJdbcWay tools) {
		Assert.assertNotNull(tools);
		tools.testUserActions();
	}
}
