package com.as1124.server.wxsapp.database.mapper;

import java.util.HashMap;
import java.util.List;

/**
 * 商品类别查询器
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface GoodsCategoryMapper {

	public List<HashMap<String, Object>> queryAllCategory();
}
