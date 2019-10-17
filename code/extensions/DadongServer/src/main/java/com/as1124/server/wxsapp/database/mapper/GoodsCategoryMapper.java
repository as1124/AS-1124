package com.as1124.server.wxsapp.database.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 商品类别查询器
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface GoodsCategoryMapper {

	/**
	 * 查询商品类别
	 * @param categoryid 类别id，为空时查询所有类别
	 * @return
	 */
	public List<HashMap<String, Object>> queryGoodsCategory(@Param("categoryno") String categoryno);
}
