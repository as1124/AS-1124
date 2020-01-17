package com.as1124.spring.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

/**
 * 利用JPA实现数据持久化及操作
 * <ul>
 * <li>如果JPA的获取方式是通过{@linkplain EntityManagerFactory}创建，
 * <b>不论以何种方式注入都需要手动关闭close </b></li>
 * <li>EntityManagerFactory 需要自行处理事务开启、提交以及EntityManager的关闭</li>
 * <li>如果是{@linkplain PersistenceContext}由Spring注入的{@linkplain EntityManager}, 不需要手动关闭
 * <ul>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Transactional
@Component
public class StandardJpaUserActionImpl implements IUserAction {

	@PersistenceUnit
	private EntityManagerFactory mEMF;

	@Override
	public int addUser(UserInfo user) {
		// EntityManagerFactory 需要自行处理事务开启、提交以及EntityManager的关闭
		EntityManager entityManager = mEMF.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(user);

		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return user.getId();
	}

	@Override
	public UserInfo findOne(int uid) {
		EntityManager entityManager = mEMF.createEntityManager();

		UserInfo result = entityManager.find(UserInfo.class, uid);

		entityManager.close();
		return result;
	}

	@Override
	public boolean updateUser(UserInfo user) {
		EntityManager entityManager = mEMF.createEntityManager();
		entityManager.getTransaction().begin();

		UserInfo updatedOne = null;
		updatedOne = entityManager.merge(user);

		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();

		return user.equals(updatedOne);
	}

}
