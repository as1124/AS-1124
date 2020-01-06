package com.as1124.spring.jdbc;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.as1124.spring.web.model.UserInfo;

/**
 * Spring 数据库操作
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = As1124DatabaseAction.class)

@Configuration
@ComponentScan(basePackages = "com.as1124.spring.jdbc")
public class As1124DatabaseAction {

	@Autowired(required = false)
	private UserActionImpl uAction;

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/wxdadong");
		dataSource.setUsername("root");
		dataSource.setPassword("Root1234");
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(@Autowired DataSource ds) {
		Assert.assertNotNull(ds);
		return new JdbcTemplate(ds);
	}

	@Test
	public void testDBActions() {
		assertNotNull(uAction);
		UserInfo user = uAction.findOne(1);
		System.out.println(user == null ? "--" : user.getUserName());
	}

}
