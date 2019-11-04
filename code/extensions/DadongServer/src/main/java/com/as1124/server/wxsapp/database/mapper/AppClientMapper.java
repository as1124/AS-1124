package com.as1124.server.wxsapp.database.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * APP配置信息查询器
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface AppClientMapper {

	/**
	 * 查询APP配置信息
	 * @param clientType
	 * @param clientVersion
	 * @return
	 */
	public List<HashMap<String, Object>> queryAppSetting(@Param("clientType") int clientType,
			@Param("clientVersion") String clientVersion);
}
