package com.as1124.spring.remote.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

public class UserHttpServiceImpl implements IUserAction, Serializable {

	private static final long serialVersionUID = -4142201941118063528L;

	@Override
	public UserInfo findOne(int uid) {
		return new UserInfo(uid, "用户是=" + uid);
	}

	@Override
	public List<UserInfo> allUsers() {
		List<UserInfo> list = new ArrayList<UserInfo>();
		list.add(new UserInfo("欢哥", "哈哈哈哪里---"));
		list.add(new UserInfo("傻狗2号", "Earth 天朝"));
		return list;
	}

	@Override
	public boolean updateUser(UserInfo user) {
		return false;
	}
}
