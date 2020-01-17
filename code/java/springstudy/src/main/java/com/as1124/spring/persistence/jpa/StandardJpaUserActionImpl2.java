package com.as1124.spring.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Transactional
@Component
public class StandardJpaUserActionImpl2 implements IUserAction {

	// ATTENTION  javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available 
	// for current thread - cannot reliably process 'merge' call
	// SharedEntityManagerCreator.java:298

	@PersistenceContext
	private EntityManager mEntityManager;

	@Override
	public UserInfo findOne(int uid) {
		return mEntityManager.find(UserInfo.class, uid);
	}

	@Override
	public int addUser(UserInfo user) {
		mEntityManager.persist(user);
		//			mEntityManager.flush();
		mEntityManager.close();
		return user.getId();
	}

	@Override
	public boolean updateUser(UserInfo user) {
		//			mEntityManager.getTransaction().begin();
		mEntityManager.merge(user);
		//			mEntityManager.flush();
		//			mEntityManager.getTransaction().commit();
		mEntityManager.close();
		return true;
	}

}
