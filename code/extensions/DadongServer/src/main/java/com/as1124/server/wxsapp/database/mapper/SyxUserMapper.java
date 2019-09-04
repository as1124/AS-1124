package com.as1124.server.wxsapp.database.mapper;

import java.util.List;

import com.as1124.server.wxsapp.resources.UserInfo;

/**
 * User映射器
 * @see UserInfo
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public interface SyxUserMapper {

	public void insertUser(UserInfo userInfo);

	public UserInfo queryByID(int userID);

	public UserInfo queryByTele(String telephone);

	public List<UserInfo> queryAll();
	
}
