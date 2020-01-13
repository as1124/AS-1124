package com.as1124.spring.jdbc;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.as1124.spring.web.model.UserInfo;

/**
 * Spring Java 型配置JDBC数据源{@link DataSource}，然后装配成 {@link JdbcTemplate} 进行数据库的 CRUD 操作
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Component
public class PersistenceJdbcWay {

	@Autowired
	private JdbcTemplateUserActionImpl uAction;

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
