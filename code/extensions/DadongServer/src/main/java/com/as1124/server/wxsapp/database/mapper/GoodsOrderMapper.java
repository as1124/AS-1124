package com.as1124.server.wxsapp.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.as1124.server.wxsapp.resources.GoodsOrder;

/**
 * 商品订单查询器
 * @see GoodsOrder
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface GoodsOrderMapper {

	/**
	 * 生成新订单信息
	 * @param orderInfo
	 */
	public void newGoodsOrder(GoodsOrder orderInfo);

	/**
	 * 根据订单号查询订单详情
	 * @param orderid
	 * @return
	 */
	public GoodsOrder queryOrderByID(@Param("orderid") String orderid);

	/**
	 * 查询用户的订单信息
	 * @param openid
	 * @param orderStatus
	 * @return
	 */
	public List<GoodsOrder> queryUserOrders(@Param("openid") String openid, @Param("orderStatus") int orderStatus);

	/**
	 * 查询用户在某时间段内的订单
	 * @param startDate 开始日期, YYYY-MM-DD, 包含
	 * @param endDate 结束日期, YYYY-MM-DD, 包含
	 * @param openid
	 * @return
	 */
	public List<GoodsOrder> queryOrdersInDate(@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("openid") String openid);

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
	public List<GoodsOrder> queryOrders(GoodsOrder orderInfo);

	/**
	 * 更新订单状态
	 * @param orderid
	 * @param newStatus
	 */
	public boolean updateOrderStatus(@Param("orderid") String orderid, @Param("orderStatus") int newStatus);

	/**
	 * 更新订单信息
	 * @param orderInfo
	 */
	public boolean updateOrder(GoodsOrder orderInfo);

	/**
	 * 删除订单
	 * @param orderid
	 */
	public boolean deleteOrder(@Param("orderid") String orderid);
}
