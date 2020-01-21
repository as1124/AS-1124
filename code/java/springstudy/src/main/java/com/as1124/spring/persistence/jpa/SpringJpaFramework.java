package com.as1124.spring.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as1124.spring.web.model.UserInfo;

/**
 * Spring-Data-JPA 框架
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface SpringJpaFramework extends JpaRepository<UserInfo, Integer> {

	/**
	 * 利用Spring-JPA中默认语法进行查询
	 * 
	 * @param userName
	 * @return
	 */
	public List<UserInfo> findByUserNameLike(String userName);

	/**
	 * 使用 {@link Query} 进行自定义查询; 动态参数匹配
	 * 
	 * @param userName
	 * @return
	 */
	@Query("select s from UserInfo s where s.userName like ?1")
	public List<UserInfo> queryUserByName(String userName);

	@Query("select s from UserInfo s where s.address like :addr")
	public List<UserInfo> queryUserByAddress(@Param("addr") String address);

}
