package com.as1124.spring.persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

/**
 * {@link JdbcTemplate} 实现了 {@link JdbcOperations}; Spring Java方式将{@link DataSource}装配到 {@link JdbcTemplate} 
 * 进行数据库 CRUD 操作的示例
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Component
public class JdbcTemplateUserActionImpl implements IUserAction {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * 注入的一般就是 {@link JdbcTemplate}
	 */
	@Autowired(required = true)
	private JdbcOperations dbOperations;

	public JdbcTemplateUserActionImpl() {
		// default constructor
	}

	@Override
	public UserInfo findOne(int uid) {
		UserInfo result = null;
		if (dbOperations != null) {
			result = dbOperations.queryForObject("select * from user_info where id=?", new Object[] { uid },
				(rs, rowNum) -> {
					UserInfo one = new UserInfo(rs.getString("user_name"), rs.getString("address"));
					one.setId(rs.getInt("id"));
					one.setBirthday(rs.getDate("birthday"));
					return one;
				});
		}
		return result;
	}

	@Override
	public boolean updateUser(UserInfo user) {
		if (dbOperations != null) {
			LocalDate localTime = LocalDate.now();
			String sql = String.format(
				"update user_info set user_name='%s', address='%s', birthday='%s' where id = %d;", user.getUserName(),
				user.getAddress(), localTime.format(dateFormatter), user.getId());
			int affectedRow = dbOperations.update(sql);
			return affectedRow > 0;
		} else {
			return false;
		}
	}

	/**
	 * 有没有 SET 方法都可以进行注入
	 * @param dbOperations The {@link #dbOperations} to set.
	 */
	public void setDbOperations(JdbcOperations dbOperations) {
		this.dbOperations = dbOperations;
	}

}
