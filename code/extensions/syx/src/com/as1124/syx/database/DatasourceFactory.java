package com.as1124.syx.database;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.as1124.syx.database.mapper.SyxUserMapper;

/**
 * 获取数据库连接工厂
 *
 * @author huangjw (mailto:huangjw@primeton.com)
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

	public static void main(String[] args) {
		try (SqlSession sqlSession = getDatasource().openSession();) {
			SyxUserMapper mapper = sqlSession.getMapper(SyxUserMapper.class);
			Object result = mapper.queryByID(1);
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
