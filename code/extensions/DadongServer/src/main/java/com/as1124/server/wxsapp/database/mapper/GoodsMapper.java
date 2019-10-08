package com.as1124.server.wxsapp.database.mapper;

import java.util.HashMap;
import java.util.List;

public interface GoodsMapper {

	public List<HashMap<String, Object>> queryGoods(String categoryno);

	public HashMap<String, Object> queryGoodsByNo(String goodsno);
}
