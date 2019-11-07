package com.as1124.server.wxsapp.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.as1124.server.wxsapp.resources.UserInfo;

/**
 * 用户信息查询器
 * @see UserInfo
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface UserInfoMapper {

	/**
	 * 新用户
	 * @param userInfo
	 */
	public void insertUser(UserInfo userInfo);

	/**
	 * 根据id查询单个用户
	 * @param userid
	 * @return
	 */
	public UserInfo queryByID(@Param("userid") int userid);

	/**
	 * 关键字查询用户
	 * @param userInfo
	 * @return
	 */
	public List<UserInfo> queryByKey(UserInfo userInfo);

	/**
	 * 列出所有用户信息
	 * @return
	 */
	public List<UserInfo> queryAll();

	/**
	 * 更新用户信息
	 * @param userInfo
	 * @return
	 */
	public boolean updateUser(UserInfo userInfo);

	/**
	 * 更新用户购物车信息
	 * @param openid
	 * @param goodscar
	 * @return
	 */
	public boolean updateGoodsCar(@Param("openid") String openid, @Param("goodscar") String goodscar);
	
	/**
	 * 查询用户的购物车信息
	 * @param openid
	 * @return
	 */
	public String queryGoodsCar(@Param("openid")String openid);
}
