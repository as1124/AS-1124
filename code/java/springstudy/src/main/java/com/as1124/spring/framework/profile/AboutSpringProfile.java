package com.as1124.spring.framework.profile;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.as1124.spring.framework.bean.LovedHeros;

/**
 * {@link Profile}注解：其作用是为了部署单元能够适应多个运行环境；
 * 只有当Bean 的<code> Profile </code>与运行环境描述相匹配时, Bean才会被加载. 
 * 没有被 Profile 描述的Bean会始终被装配。
 * <ol>Profile启用方式
 * <li><code>spring.profiles.active/spring.profiles.default</code>
 * <li>测试时使用{@link ActiveProfiles} 注解
 * </ol>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LovedHerosConfig.class)
@ActiveProfiles(profiles = "SD")
public class AboutSpringProfile {

	@Autowired
	private LovedHeros hero;

	@Test
	public void testProfile() {
		Assert.assertNotNull(hero);
		System.out.println(hero.getName() + "----" + hero.getStory());
	}
}
