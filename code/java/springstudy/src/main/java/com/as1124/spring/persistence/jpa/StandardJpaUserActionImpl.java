package com.as1124.spring.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Component;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

/**
 * 利用JPA实现数据持久化及操作
 * <ul>
 * <li>如果JPA的获取方式是通过{@linkplain EntityManagerFactory}创建，
 * <b>不论以何种方式注入都需要手动关闭close </b></li>
 * <li>EntityManagerFactory 需要自行处理事务开启、提交以及EntityManager的关闭</li>
 * <li>如果是{@linkplain PersistenceContext}由Spring注入的{@linkplain EntityManager}, 不需要手动关闭
 * <li>Removing a detached instance com.as1124.spring.web.model.UserInfo</li>
 * 先 <code> EntityManager.merge </code>查出最新数据，然后才能执行 {@link EntityManager#remove(Object)}
 * <li>UPDATE、DELETE、INSERT 都需要事务处理</li>
 * <ul>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@javax.transaction.Transactional
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

		UserInfo updatedOne = entityManager.merge(user);

		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();

		return user.equals(updatedOne);
	}

	@Override
	public boolean deleteUser(int uid) {
		EntityManager entityManager = mEMF.createEntityManager();
		entityManager.getTransaction().begin();

		UserInfo user = new UserInfo();
		user.setId(uid);
		entityManager.remove(entityManager.merge(user));

		entityManager.getTransaction().commit();
		entityManager.close();
		return true;
	}

}
