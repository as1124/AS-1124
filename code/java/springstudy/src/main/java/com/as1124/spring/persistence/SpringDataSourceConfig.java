package com.as1124.spring.persistence;

import javax.naming.Context;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Component;

import com.mchange.v2.c3p0.PooledDataSource;

/**
 * 常用数据源类型及Spring支持的配置方式：
 * <ul>
 * <li>JDBC 型数据源：XML / JAVA 配置</li>
 * <li>JNDI 型数据源：一般通过 XML， 基于Web容器上下文环境，使用的是内置的数据库连接池，有环境依赖；<br/>
 * 通过{@linkplain Context}进行查找，或者通过{@linkplain JndiObjectFactoryBean} 进行注入创建
 * </li>
 * <li>数据库连接池：C3P0, DBCP, 阿里 Druid</li>
 * </ul>
 * 
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@ImportResource("classpath:/config/spring-datasource.xml")
@Component
public class SpringDataSourceConfig {

	/**
	 * 注入XML配置的JDBC数据源
	 */
	@Autowired
	@Qualifier(IPersistenceConstants.JDBC_XML)
	private DataSource xmlJdbcDataSource;

	/**
	 * 注入JAVA配置的JDBC数据源
	 */
	@Autowired
	@Qualifier(IPersistenceConstants.JDBC_JAVA)
	private DataSource javaJdbcDataSource;

	@Autowired(required = false)
	@Qualifier(IPersistenceConstants.JNDI_XML)
	private JndiObjectFactoryBean jndiHolder;

	@Autowired
	@Qualifier(IPersistenceConstants.JNDI_JAVA)
	private DataSource jndiDataSource;

	@Autowired
	@Qualifier(IPersistenceConstants.DBCP_XML)
	private BasicDataSource xmlDBCP;

	@Autowired
	@Qualifier(IPersistenceConstants.DBCP_JAVA)
	private BasicDataSource javaDBCP;

	@Autowired
	@Qualifier(IPersistenceConstants.C3P0_XML)
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
		// 理论上应该是同一个对象，因为都是对当前J2EE容器的数据源对象的引用
		Assert.assertEquals(obj, jndiDataSource);

		Assert.assertNotNull(xmlC3p0);
	}
}
