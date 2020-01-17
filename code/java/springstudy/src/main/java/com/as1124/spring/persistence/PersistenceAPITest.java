package com.as1124.spring.persistence;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.as1124.spring.persistence.jpa.StandardJpaUserActionImpl;
import com.as1124.spring.persistence.jpa.StandardJpaUserActionImpl2;
import com.as1124.spring.web.model.UserInfo;

/**
 * 数据持久化功能测试
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@EnableWebMvc
@Controller
@RequestMapping("/persistence")
public class PersistenceAPITest {

	@Autowired
	private JdbcTemplateUserActionImpl uAction;

	@Autowired
	private SpringDataSourceConfig dsConfig;

	@GetMapping("/datasource")
	public void testSpringDataSource() {
		dsConfig.configSpringDatasource();
	}

	@GetMapping("/jdbctemplate")
	public void userActionByJDBC() {
		assertNotNull(uAction);
		UserInfo user = uAction.findOne(1);
		System.out.println(user == null ? "--" : user.getUserName());
		if (user != null) {
			user.setUserName("新沙雕");
			user.setAddress("--11--");
			System.out.println("更新结果 = " + uAction.updateUser(user));
		}
	}

	@Autowired
	private StandardJpaUserActionImpl sJpaActionImpl;

	@Autowired
	private StandardJpaUserActionImpl2 sJpaActionImpl2;

	@GetMapping("/jpa")
	public void userActionByJpa() {
		Assert.assertNotNull(sJpaActionImpl);
		UserInfo user = sJpaActionImpl.findOne(4);
		if (user != null) {
			System.out.println(user.getUserName() + "," + user.getAddress());
		}

		user.setUserName("JPA 更新");
		user.setAddress("嘿嘿嘿哈哈哈");
		//		System.out.println("JPA 更新结果 == " + sJpaActionImpl.updateUser(user));

		UserInfo newUser = new UserInfo("JPA 插入", "中国上海宜山路");
		newUser.setBirthday(new Date(System.currentTimeMillis()));
		//		System.out.println("新插入的id==" + sJpaActionImpl.addUser(newUser));

		UserInfo userx = sJpaActionImpl2.findOne(5);
		if (userx != null) {
			System.out.println(userx.getUserName() + "," + userx.getAddress());
		}
		userx.setUserName("----HHHHHHHH--------");
		userx.setAddress("+++++++Address++++++");
		sJpaActionImpl2.updateUser(userx);
		sJpaActionImpl2.addUser(newUser);
	}
}
