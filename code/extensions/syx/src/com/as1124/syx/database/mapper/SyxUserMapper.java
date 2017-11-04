package com.as1124.syx.database.mapper;

import java.util.List;

import com.as1124.syx.resources.SyxUser;

/**
 * User映射器
 * @see SyxUser
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public interface SyxUserMapper {

	public void insertUser(SyxUser userInfo);

	public SyxUser queryByID(int userID);

	public SyxUser queryByTele(String telephone);

	public List<SyxUser> queryAll();
	
}
