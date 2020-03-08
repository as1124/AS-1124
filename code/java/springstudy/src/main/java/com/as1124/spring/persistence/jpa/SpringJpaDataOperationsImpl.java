package com.as1124.spring.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.as1124.spring.web.model.UserInfo;

/**
 * Spring-Data-JPA 混合查询测试：注意命名 = <code>${JpaRepository} + Impl</code>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Transactional
public class SpringJpaDataOperationsImpl implements ISpringJpaExtQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> allUserData() {
		Query query = entityManager.createQuery("select s from UserInfo s where s.id >= ?1");
		query.setParameter(1, Integer.valueOf(2));
		return query.getResultList();
	}

}
