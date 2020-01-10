package com.as1124.spring.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * model 操作接口
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IUserAction {

	/**
	 * 根据主键ID 进行查找
	 * 
	 * @param uid
	 * @return
	 */
	public UserInfo findOne(int uid);

	/**
	 * 所有数据
	 * 
	 * @return
	 */
	public default List<UserInfo> allUsers() {
		return new ArrayList<>();
	}

	/**
	 * 插入新数据
	 * 
	 * @param user
	 * @return 新数据主键id
	 */
	public default int addUser(UserInfo user) {
		return user.getId();
	}

	/**
	 * 更新数据
	 * @param user
	 * @return true - 更新成功
	 */
	public boolean updateUser(UserInfo user);

}
