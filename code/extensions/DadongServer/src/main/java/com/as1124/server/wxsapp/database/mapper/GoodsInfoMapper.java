package com.as1124.server.wxsapp.database.mapper;

import java.util.List;

import com.as1124.server.wxsapp.resources.GoodsInfo;

/**
 * 商品信息查询服务
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface GoodsInfoMapper {

	public List<GoodsInfo> queryGoodsByKey(GoodsInfo goodsInfo);

	public List<GoodsInfo> allGoods();
}
