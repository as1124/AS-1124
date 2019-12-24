package com.as1124.wxsapp.sign.database;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
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

	public static SqlSessionFactory getDatasource(String environment) throws IOException {
		if (sessionFactory == null) {
			if (StringUtils.isBlank(environment)) {
				environment = "development";
			}
			InputStream inputStream = null;
			try {
				inputStream = Resources.getResourceAsStream("config/datasource-config.xml");
				sessionFactory = new SqlSessionFactoryBuilder().build(inputStream, environment);
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		return sessionFactory;
	}

}
