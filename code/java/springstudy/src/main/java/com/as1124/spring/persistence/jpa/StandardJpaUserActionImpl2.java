package com.as1124.spring.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@org.springframework.transaction.annotation.Transactional
@Component("StandardJpaUserActionImpl2")
public class StandardJpaUserActionImpl2 implements IUserAction {

	// ATTENTION  未解决异常 javax.persistence.TransactionRequiredException: 
	// No EntityManager with actual transaction available 
	// for current thread - cannot reliably process 'merge' call
	// SharedEntityManagerCreator.java:298
	// FIXED：确定上下文Context使用合理且正确的 Transactional 注解

	@PersistenceContext
	private EntityManager mEntityManager;

	@Override
	public UserInfo findOne(int uid) {
		return mEntityManager.find(UserInfo.class, uid);
	}

	@Override
	public int addUser(UserInfo user) {
		mEntityManager.persist(user);
		return user.getId();
	}

	@Override
	public boolean updateUser(UserInfo user) {
		mEntityManager.merge(user);
		return true;
	}

	@Override
	public boolean deleteUser(int uid) {
		UserInfo userToDelete = mEntityManager.merge(new UserInfo(uid, ""));
		mEntityManager.remove(userToDelete);
		return true;
	}

	public EntityManager getEntityManager() {
		return this.mEntityManager;
	}
}
