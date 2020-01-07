package com.as1124.spring.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * model 操作接口
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IUserAction {

	public UserInfo findOne(int uid);

	public default List<UserInfo> allUsers() {
		return new ArrayList<>();
	}

	public default void addUser(UserInfo user) {
	}

	public boolean updateUser(UserInfo user);

}
