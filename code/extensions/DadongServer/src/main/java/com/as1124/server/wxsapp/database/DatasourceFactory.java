package com.as1124.server.wxsapp.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

	public static void main(String[] args) {
		String dbUrl = "jdbc:mysql://101.37.158.174:3306/wxdadong";
		String dbUserName = "root";
		String dbPassword = "Root1234";
		String jdbcName = "com.mysql.jdbc.Driver";
		Connection con = null;
		try {
			Class.forName(jdbcName);
			con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			if (con != null) {
				System.out.println("成功");
			} else {
				System.out.println("失败");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
