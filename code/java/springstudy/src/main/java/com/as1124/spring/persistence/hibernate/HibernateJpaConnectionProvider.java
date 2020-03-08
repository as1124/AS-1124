package com.as1124.spring.persistence.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.as1124.spring.persistence.IPersistenceConstants;

/**
 * 使用Hibernate 配置JPA进行数据持久化时需要提供数据源配置，通过自定义 {@link ConnectionProvider} 是其中的一种方式
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class HibernateJpaConnectionProvider implements ConnectionProvider {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier(IPersistenceConstants.DBCP_JAVA)
	private DataSource dbcpDataSource;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return ConnectionProvider.class.equals(unwrapType)
				|| HibernateJpaConnectionProvider.class.isAssignableFrom(unwrapType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		if (isUnwrappableAs(unwrapType)) {
			return (T) this;
		} else {
			return null;
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dbcpDataSource.getConnection();
	}

	@Override
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

}
