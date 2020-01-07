package com.as1124.spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

/**
 * {@link JdbcTemplate} 实现了 {@link JdbcOperations}
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Component
public class UserActionImpl implements IUserAction {

	/**
	 * 注入的一般就是 {@link JdbcTemplate}
	 */
	@Autowired(required = true)
	private JdbcOperations dbOperations;

	public UserActionImpl() {
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
					return one;
				});
		}
		return result;
	}

	@Override
	public boolean updateUser(UserInfo user) {
		if (dbOperations != null) {
			String sql = String.format("update user_info set user_name='%s', address='%s' where id = %d;",
				user.getUserName(), user.getAddress(), user.getId());
			int affectedRow = dbOperations.update(sql);
			return affectedRow > 0;
		} else {
			return false;
		}
	}

	/**
	 * @param dbOperations The {@link #dbOperations} to set.
	 */
	@Autowired
	public void setDbOperations(JdbcOperations dbOperations) {
		this.dbOperations = dbOperations;
	}

}
