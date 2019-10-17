package com.as1124.server.wxsapp.database.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 用户收货地址查询器
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface UserAddressMapper {

	public void insertExpressAddress(Map<String, ?> address);

	public List<HashMap<String, Object>> queryExpressAddress(@Param("openid") String openid);

	public boolean updateExpressAddress();

	public boolean deleteExpressAddress(int addressid, String openid);
}
