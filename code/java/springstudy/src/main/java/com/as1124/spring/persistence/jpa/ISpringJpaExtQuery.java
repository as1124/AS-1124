package com.as1124.spring.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as1124.spring.web.model.UserInfo;

/**
 * Spring-Data-JPA 混合查询测试：定义一个额外扩展的查询接口，假设查询设计负责的关联查询等处理工作，
 * 通过单独的 {@link JpaRepository} 以及 {@ Query) 无法实现
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface ISpringJpaExtQuery {

	List<UserInfo> allUserData();
}
