package com.as1124.server.wxsapp.database.mapper;

import java.util.List;

import com.as1124.server.wxsapp.resources.UserInfo;

/**
 * 用户信息映射器
 * @see UserInfo
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface UserInfoMapper {

	public void insertUser(UserInfo userInfo);

	public UserInfo queryByID(int userid);

	public UserInfo queryByOpenid(String openid);

	public UserInfo queryByPhoneNo(String telephone);

	public List<UserInfo> queryAll();

	public boolean updateUser(UserInfo userInfo);

}
