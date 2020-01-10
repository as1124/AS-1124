package com.as1124.spring.jdbc;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.as1124.spring.web.model.UserInfo;

/**
 * 常用数据源类型及Spring支持的配置方式：
 * <ul>
 * <li>JDBC 型数据源：XML / JAVA</li>
 * <li>JNDI 型数据源：XML / JAVA （两者都基于Web容器配置的数据源信息进行查找）</li>
 * <li>数据库连接池：C3P0, DBCP, 阿里 Druid</li>
 * </ul>
 * 
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringDataSourceConfig.class)
@ImportResource("classpath:/config/spring-datasource.xml")
@ComponentScan(basePackages = "com.as1124.spring.jdbc")
public class SpringDataSourceConfig {

	/**
	 * 注入XML配置的JDBC数据源
	 */
	@Autowired
	@Qualifier("xml-jdbc-injection")
	private DataSource xmlJdbcDatasource;

	/**
	 * 注入JAVA配置的JDBC数据源
	 */
	@Autowired
	@Qualifier("java-jdbc-injection")
	private DataSource javaJdbcDatasource;

	//	private DataSource xmlJndiDatasource;
	//
	//	private DataSource javaJndiDatasource;

	@Autowired
	@Qualifier("xml-dbcp-injection")
	private BasicDataSource xmlDBCP;

	@Autowired
	@Qualifier("java-dbcp-injection")
	private BasicDataSource javaDBCP;

	@Autowired
	private JdbcTemplateUserActionImpl uAction;

	@Test
	public void configSpringJdbcDatasource() {
		assertNotNull(xmlJdbcDatasource);
		assertNotNull(javaJdbcDatasource);
		assertNotEquals(xmlJdbcDatasource, javaJdbcDatasource);
	}

	@Test
	public void configSpringJNDIDatasource() {
		assertNotNull(xmlDBCP);
		assertNotNull(javaDBCP);
		assertNotEquals(xmlDBCP, javaDBCP);
	}

	@Test
	public void testUserActions() {
		assertNotNull(uAction);
		UserInfo user = uAction.findOne(1);
		System.out.println(user == null ? "--" : user.getUserName());

		if (user != null) {
			user.setUserName("新沙雕");
			user.setAddress("--11--");
			uAction.updateUser(user);
		}
	}
}
