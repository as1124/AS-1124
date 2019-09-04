package com.as1124.server.wxsapp.database;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 获取数据库连接工厂
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class DatasourceFactory {

	static SqlSessionFactory sessionFactory = null;

	private DatasourceFactory() {
	}

	public static SqlSessionFactory getDatasource() throws IOException {
		if (sessionFactory == null) {
			String resource = "com/as1124/syx/database/datasource-config.xml";
			InputStream inputStream = null;
			try {
				inputStream = Resources.getResourceAsStream(resource);
				sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			} finally {
				if (inputStream != null)
					inputStream.close();
			}
		}
		return sessionFactory;
	}

}
