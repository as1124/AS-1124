package com.as1124.spring.jmx;

import java.util.ArrayList;
import java.util.List;

import javax.management.MXBean;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

/**
 * Spring Export 标准MBean
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@MXBean
public class UserActionImplMBean implements IUserAction {

	public static final int DEFAULT_SIZE_PER_PAGE = 25;

	/**
	 * MBean 的托管属性，演示通过托管方进行修改
	 */
	private int userPage = DEFAULT_SIZE_PER_PAGE;

	public List<UserInfo> findRecentUsers() {
		return new ArrayList<>();
	}

	@Override
	public UserInfo findOne(int uid) {
		return null;
	}

	@Override
	public List<UserInfo> allUsers() {
		return IUserAction.super.allUsers();
	}

	@Override
	public int addUser(UserInfo user) {
		return IUserAction.super.addUser(user);
	}

	@Override
	public boolean updateUser(UserInfo user) {
		return false;
	}

	public int getUserPage() {
		return userPage;
	}

	public void setUserPage(int userPage) {
		this.userPage = userPage;
	}
}
