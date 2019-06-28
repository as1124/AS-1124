/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年9月28日
 *******************************************************************************/

package com.volume2.ch4.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

/**
 * JDBC使用示例<br/>
 * 目前大部分数据库驱动程序是支持自动注册的（如果jar文件中包含MEAT-INF/services/java.sql.DRiver文件），所以
 * 可以不用<code> Class.forName(DriverClass)</code>
 *
 * This program tests the database and the JDBC driver are correctly configured.
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public class DatabaseCase {

	/**
	 * Runs a test by creating a table, adding a value, showing the table contents,
	 * and removing the table.
	 * @param args
	 */
	public static void main(String[] args) {
		ResultSet resultset = null;
		Statement statement = null;
		try (Connection con = getConnection()) {
			if (con == null)
				return;
			statement = con.createStatement();
			boolean result = statement.execute("CREATE TABLE IF NOT EXISTS greetings (Message varchar(100));");
			JavaCoreLogger.log(Level.INFO, "Create Table Result: " + result);

			int rows = statement.executeUpdate(
				"INSERT INTO greetings VALUES ('Hi, 你好，其实之前就有碰见过你, 第一次的时候，你刚好从那个拐弯的地方走出来；可能那天你是有开心的事情吧，笑的很明媚，让人觉得特别有气质，所以真的还挺希望能认识你的，真的很希望能留你一个微信...可以吗？');");
			JavaCoreLogger.log(Level.INFO, "Insert data: " + rows);

			resultset = statement.executeQuery("SELECT * FROM greetings;");
			while (resultset.next()) {
				JavaCoreLogger.log(Level.INFO, "greeting is " + resultset.getString(1));
			}

			rows = statement.executeUpdate("DROP TABLE greetings;");
			JavaCoreLogger.log(Level.INFO, "Drop Table Result: " + rows);

			statement.closeOnCompletion();
		} catch (SQLException ex) {
			handleSQLException(ex);
		} finally {
			try {
				// 先关闭结果集
				if (resultset != null)
					resultset.close();
			} catch (SQLException ex) {
				handleSQLException(ex);
			}

			try {
				if (statement != null)
					statement.close();
			} catch (SQLException ex) {
				handleSQLException(ex);
			}
		}
	}

	/**
	 * Gets a connection from the specified settings
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/eos750";
		String userName = "root";
		String dbPass = "huangjw";
		Connection con = DriverManager.getConnection(url, userName, dbPass);
		if (con != null) {
			// 启用JDBC调试跟踪特性，将调试信息传递给PrintWriter
			PrintWriter writer = new PrintWriter(System.out);
			DriverManager.setLogWriter(writer);
		}
		return con;
	}

	public static void handleSQLException(SQLException ex) {
		JavaCoreLogger.log(Level.SEVERE, ex.getMessage(), ex);
		Iterator<Throwable> exs = ex.iterator();
		while (exs.hasNext()) {
			Throwable throwEx = exs.next();
			JavaCoreLogger.log(Level.SEVERE, throwEx.getMessage(), throwEx);
		}
	}

}
