package com.as1124.server.wxsapp.database.mapper;

import java.util.HashMap;
import java.util.List;

import com.as1124.server.wxsapp.resources.GoodsOrder;

/**
 * 商品订单查询器
 * @see GoodsOrder
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface GoodsOrderMapper {

	public void newGoodsOrder(GoodsOrder orderInfo);

	/**
	 * 根据订单号查询订单详情
	 * @param orderid
	 * @return
	 */
	public GoodsOrder queryOrder(String orderid);

	/**
	 * 根据字段查询订单信息
	 * <ol>
	 * <li>用户openid</li>
	 * <li>用户unionid</li>
	 * <li>订单状态orderStatus</li>
	 * <li>订单时间范围</li>
	 * </ol>
	 * @param exprs
	 * @return
	 */
	public List<GoodsOrder> queryOrders(HashMap<String, Object> exprs);

	/**
	 * 更新订单状态
	 * @param orderid
	 * @param newStatus
	 */
	public void updateOrderStatus(String orderid, int newStatus);

	public void updateOrder(GoodsOrder orderInfo);

	public void deleteOrder(String orderid);
}
