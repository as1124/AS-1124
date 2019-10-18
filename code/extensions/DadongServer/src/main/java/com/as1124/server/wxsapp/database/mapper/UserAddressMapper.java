package com.as1124.server.wxsapp.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.as1124.server.wxsapp.resources.UserExpressAddress;

/**
 * 用户收货地址查询器
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface UserAddressMapper {

	/**
	 * 新增收货地址
	 * @param addressData
	 */
	public void insertExpressAddress(UserExpressAddress addressData);

	/**
	 * 查询收货地址
	 * @param openid
	 * @param addressID
	 * @return
	 */
	public List<UserExpressAddress> queryExpressAddress(@Param("openid") String openid,
			@Param("addressid") int addressID);

	/**
	 * 更新地址信息
	 * @param addressData
	 * @return
	 */
	public boolean updateExpressAddress(UserExpressAddress addressData);

	/**
	 * 删除地址信息
	 * @param openid
	 * @param addressID
	 * @return
	 */
	public boolean deleteExpressAddress(@Param("openid") String openid, @Param("addressid") int addressID);
}
