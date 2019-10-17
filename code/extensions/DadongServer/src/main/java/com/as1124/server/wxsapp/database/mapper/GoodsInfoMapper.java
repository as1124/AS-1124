package com.as1124.server.wxsapp.database.mapper;

import java.util.List;

import com.as1124.server.wxsapp.resources.GoodsInfo;

/**
 * 商品信息查询服务
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface GoodsInfoMapper {

	/**
	 * 关键字查询商品信息
	 * @param goodsInfo
	 * @return
	 */
	public List<GoodsInfo> queryGoodsByKey(GoodsInfo goodsInfo);

	/**
	 * 列出所有商品
	 * @return
	 */
	public List<GoodsInfo> allGoods();
}
