package com.as1124.spring.jdbc.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

/**
 * 利用JPA实现数据持久化及操作
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
@Transactional
public class JPAUserActionImpl implements IUserAction {

	/**
	 * JPA注入
	 */
//	@PersistenceUnit
	private EntityManagerFactory emf;

//	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public int addUser(UserInfo user) {
		emf.createEntityManager().persist(user);
		return user.getId();
	}

	@Override
	public UserInfo findOne(int uid) {
		UserInfo user = emf.createEntityManager().find(UserInfo.class, uid);
		return user;
	}

	@Override
	public boolean updateUser(UserInfo user) {
		emf.createEntityManager().merge(user);
		return false;
	}

}
