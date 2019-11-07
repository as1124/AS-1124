package com.as1124.server.wxsapp.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	 * 根据商品编号查询商品信息
	 * @param goodsno
	 * @return
	 */
	public GoodsInfo queryGoodsByID(@Param("goodsno")String goodsno);

	/**
	 * 列出所有商品
	 * @return
	 */
	public List<GoodsInfo> allGoods();
	
	/**
	 * 显示热销产品
	 * @return
	 */
	public List<GoodsInfo> queryTopHots();
}
