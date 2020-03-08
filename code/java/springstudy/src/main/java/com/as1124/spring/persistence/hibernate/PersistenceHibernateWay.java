package com.as1124.spring.persistence.hibernate;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateOperations;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

/**
 * Hibernate JPA实现框架
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class PersistenceHibernateWay {

	public static void main(String[] args) {
	}

	@Bean("standardHibernateSession")
	public SessionFactory createHibernateSeesion() {
		Configuration hibernateConfig = new Configuration();
		hibernateConfig.addAnnotatedClass(null);
		hibernateConfig.addCacheableFile("");
		return hibernateConfig.buildSessionFactory();
	}

	@Bean("springHibernateSession")
	public SessionFactory createSpringHibernateSession(DataSource dataSource) {
		LocalSessionFactoryBuilder sfb = new LocalSessionFactoryBuilder(dataSource);
		sfb.addAnnotatedClass(null);
		sfb.addCacheableFile("");
		sfb.addPackage("");
		sfb.configure();
		return sfb.buildSessionFactory();
	}

	@Bean
	@Qualifier("springHibernateSession")
	public HibernateOperations cerateHibernateTemplate(SessionFactory sessionFactory) {
		return new HibernateTemplate(sessionFactory);
	}

}
