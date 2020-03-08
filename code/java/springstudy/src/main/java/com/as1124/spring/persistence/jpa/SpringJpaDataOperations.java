package com.as1124.spring.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as1124.spring.web.model.UserInfo;

/**
 * Spring-Data-JPA 框架
 * <br/>这里使用的是 Spring 上下文中的<b><code> Transactional </code></b>
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@org.springframework.transaction.annotation.Transactional
public interface SpringJpaDataOperations extends JpaRepository<UserInfo, Integer>, ISpringJpaExtQuery {

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
	//ATTENTION 不能写 Select *
	@Query("select s from UserInfo s where s.userName like ?1")
	public List<UserInfo> queryUserByName(String userName);

	@Query("select s from UserInfo s where s.address like :addr")
	public List<UserInfo> queryUserByAddress(@Param("addr") String address);

	@Modifying
	@Query("delete from UserInfo s where s.id = :uid")
	public void deleteUser(@Param("uid") int uid);

}
