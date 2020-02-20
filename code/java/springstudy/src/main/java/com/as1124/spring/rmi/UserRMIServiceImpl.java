package com.as1124.spring.rmi;

import java.util.ArrayList;
import java.util.List;

import com.as1124.spring.web.model.UserInfo;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class UserRMIServiceImpl implements IUserRMIService {

	@Override
	public String getName(int uid) {
		return "Spring-User-RMI-Service";
	}

	@Override
	public List<UserInfo> getAllUsers() {
		ArrayList<UserInfo> users = new ArrayList<>();
		users.add(new UserInfo(1, "欢哥"));
		users.add(new UserInfo("傻狗1号", "Earth 大天朝"));
		return users;
	}

	@Override
	public void saveUser(UserInfo user) {
	}

}
