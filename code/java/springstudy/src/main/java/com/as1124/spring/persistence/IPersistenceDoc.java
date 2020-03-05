package com.as1124.spring.persistence;

import java.sql.Connection;
import java.sql.Driver;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * Spring 后端数据操作
 * <ul>
 * 数据源类型
 * <li>{@link DriverManagerDataSource}：非池化管理，每次调用{@link DataSource#getConnection()}都会创建一个新的{@link Connection}对象
 * <li>{@link SimpleDriverDataSource}：非池化管理，需要用户自己处理{@link Driver}来获取Connection
 * <li>{@link SingleConnectionDataSource}：只维护一个Connection的数据源
 * <li>JNDI 数据源（托管于J2EE容器管理）
 * <li>连接池型数据源：C3p0、Apache DBCP、阿里 Druid
 * </ul>
 * <ul>
 * 持久化方式
 * <li>{@link JdbcTemplate}：没有事物处理过程，简单方便；缺点=>没有事物概念
 * <li>Standard JPA Framework
 * <li>Spring Data JPA Framework
 * <li>ORM Framework: Hibernate、MyBatis
 * </ul>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IPersistenceDoc {

}
