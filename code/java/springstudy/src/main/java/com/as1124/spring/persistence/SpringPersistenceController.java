package com.as1124.spring.persistence;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.as1124.spring.persistence.jdbc.JdbcTemplateUserActionImpl;
import com.as1124.spring.persistence.jpa.SpringJpaFramework;
import com.as1124.spring.persistence.jpa.StandardJpaUserActionImpl;
import com.as1124.spring.persistence.jpa.StandardJpaUserActionImpl2;
import com.as1124.spring.web.model.UserInfo;

/**
 * 数据持久化功能测试
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Controller
@RequestMapping("/persistence")
public class SpringPersistenceController {

	@Autowired
	private AbstractApplicationContext springCtx;

	@GetMapping("/datasource")
	public void testSpringDataSource() {
		springCtx.getBean(SpringDataSourceDemo.class).configSpringDatasource();
	}

	@GetMapping("/jdbctemplate")
	public void userActionByJDBC() {
		JdbcTemplateUserActionImpl uAction = springCtx.getBean(JdbcTemplateUserActionImpl.class);
		assertNotNull(uAction);
		UserInfo user = uAction.findOne(1);
		System.out.println(user == null ? "--" : user);
		if (user != null) {
			user.setUserName("新沙雕");
			System.out.println("Update = " + uAction.updateUser(user));
		}
		UserInfo newUser = new UserInfo("新用户", "测试插入");
		newUser.setBirthday(new Date());
		System.out.println("Insert = " + uAction.addUser(newUser));
	}

	@Autowired
	private StandardJpaUserActionImpl sJpaActionImpl;

	@Autowired
	private StandardJpaUserActionImpl2 sJpaActionImpl2;

	@Autowired
	private SpringJpaFramework springJPA;

	@GetMapping("/jpa-unit")
	public void userActionByJpa() {
		// 测试 PersistenceUnit 行为
		UserInfo user = sJpaActionImpl.findOne(4);
		Assert.assertNotNull(user);
		System.out.println(user.getUserName() + "," + user.getAddress());

		user.setUserName("JPA 更新");
		user.setAddress("嘿嘿嘿哈哈哈");
		System.out.println("JPA 更新结果 == " + sJpaActionImpl.updateUser(user));

		UserInfo newUser = new UserInfo("JPA 插入", "中国上海宜山路");
		newUser.setBirthday(new Date(System.currentTimeMillis()));
		System.out.println("新插入的id==" + sJpaActionImpl.addUser(newUser));

		System.out.println("JPA删除结果 == " + sJpaActionImpl.deleteUser(1));
	}

	@GetMapping("/jpa-context")
	@javax.transaction.Transactional
	public void userActionByJpa2() {
		// 测试 PersistenceContext 行为
		UserInfo userx = sJpaActionImpl2.findOne(2);
		if (userx != null) {
			System.out.println(userx.getUserName() + "," + userx.getAddress());
			userx.setUserName("----HHHHHHHH--------");
			userx.setAddress("+++++++Address++++++");
			sJpaActionImpl2.updateUser(userx);

			userx.setId(null);
			sJpaActionImpl2.addUser(userx);

			sJpaActionImpl2.deleteUser(12);
		}
	}

	@GetMapping("/springjpa")
	public void userActionBySpring() {
		// 增删改查成功，不需要 @Transactional 注释
		Assert.assertNotNull(springJPA);
		Optional<UserInfo> user = springJPA.findById(5);
		if (user.isPresent()) {
			System.out.println(user.get());
		}
		springJPA.findByUserNameLike("%-JPA%").forEach(System.out::println);
		springJPA.queryUserByName("%Huang%").forEach(System.out::println);
		springJPA.queryUserByAddress("%上海%").forEach(u -> System.out.println(u));

		UserInfo newUser = new UserInfo("Spring-JPA创建", "地球天朝");
		UserInfo saveResult = springJPA.save(newUser);
		System.out.println("新建用户ID==" + saveResult.getId());
		saveResult.setAddress("Spring-jpa修改地址");
		springJPA.saveAndFlush(saveResult);

		springJPA.deleteUser(1);
	}

	@GetMapping("/springjpa2")
	public void userActionBySpring2() {
		JpaRepositoryFactory repositoryFactory = new JpaRepositoryFactory(sJpaActionImpl2.getEntityManager());
		SpringJpaFramework springjap2 = repositoryFactory.getRepository(SpringJpaFramework.class);
		Optional<UserInfo> user = springjap2.findById(6);
		if (user.isPresent()) {
			System.out.println(user.get());
		}
		List<UserInfo> result = springjap2.queryUserByName("%JPA%");
		result.forEach(u -> System.out.println(u.getAddress()));
		result = springjap2.findByUserNameLike("%JPA%");
		result.forEach(u -> System.out.println(u.getAddress()));

		UserInfo newUser = new UserInfo("Spring-JPA创建", "地球天朝");
		UserInfo saveResult = springjap2.save(newUser);
		System.out.println("新建用户ID==" + saveResult.getId());

		saveResult.setAddress("Spring-jpa修改地址");
		springjap2.saveAndFlush(saveResult);
	}
}
