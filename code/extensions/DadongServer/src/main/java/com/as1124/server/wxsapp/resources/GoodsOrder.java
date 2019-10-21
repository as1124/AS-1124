package com.as1124.server.wxsapp.resources;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

/**
 * 商品订单信息
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Alias("GoodsOrder")
public class GoodsOrder {

	private int id;

	/**
	 * 订单编号
	 */
	private String orderid;

	/**
	 * 购买用户的openid
	 */
	private String openid;

	/**
	 * 购买用户的unionid
	 */
	private String unionid;

	/**
	 * 购买的商品信息，JSON文本
	 */
	private String goodsInfo;

	/**
	 * 订单总价，实付款为减去{{@link #discount}后的值
	 */
	private String totalPrice;

	/**
	 * 折扣价格
	 */
	private String discount;

	/**
	 * 下单时间
	 */
	private Date orderTime;

	/**
	 * 订单状态：<code>1-预订单，2-已付款，3-已发货，4-已签收，5-已失效</code>
	 */
	private int orderStatus;

	/**
	 * 微信预支付订单号
	 */
	private String wxPrepayid;

	/**
	 * 微信支付订单号
	 */
	private String wxPayid;

	/**
	 * 订单物流信息，JSON文本
	 */
	private String expressInfo;

	private String ext1;

	private String ext2;

	private String ext3;

	private String ext4;

	private String ext5;

	public GoodsOrder() {
		// default constructor
	}

	/**
	 * @return Returns the {@link id}.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id The {@link id} to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Returns the {@link orderid}.
	 */
	public String getOrderid() {
		return orderid;
	}

	/**
	 * @param orderid The {@link orderid} to set.
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	/**
	 * @return Returns the {@link openid}.
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid The {@link openid} to set.
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @return Returns the {@link unionid}.
	 */
	public String getUnionid() {
		return unionid;
	}

	/**
	 * @param unionid The {@link unionid} to set.
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	/**
	 * @return Returns the {@link goodsInfo}.
	 */
	public String getGoodsInfo() {
		return goodsInfo;
	}

	/**
	 * @param goodsInfo The {@link goodsInfo} to set.
	 */
	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	/**
	 * @return Returns the {@link totalPrice}.
	 */
	public String getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice The {@link totalPrice} to set.
	 */
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return Returns the {@link discount}.
	 */
	public String getDiscount() {
		return discount;
	}

	/**
	 * @param discount The {@link discount} to set.
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	/**
	 * @return Returns the {@link orderTime}.
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime The {@link orderTime} to set.
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * @return Returns the {@link orderStatus}.
	 */
	public int getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus The {@link orderStatus} to set.
	 */
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return Returns the {@link wxPrepayid}.
	 */
	public String getWxPrepayid() {
		return wxPrepayid;
	}

	/**
	 * @param wxPrepayid The {@link wxPrepayid} to set.
	 */
	public void setWxPrepayid(String wxPrepayid) {
		this.wxPrepayid = wxPrepayid;
	}

	/**
	 * @return Returns the {@link wxPayid}.
	 */
	public String getWxPayid() {
		return wxPayid;
	}

	/**
	 * @param wxPayid The {@link wxPayid} to set.
	 */
	public void setWxPayid(String wxPayid) {
		this.wxPayid = wxPayid;
	}

	/**
	 * @return Returns the {@link expressInfo}.
	 */
	public String getExpressInfo() {
		return expressInfo;
	}

	/**
	 * @param expressInfo The {@link expressInfo} to set.
	 */
	public void setExpressInfo(String expressInfo) {
		this.expressInfo = expressInfo;
	}

	/**
	 * @return Returns the {@link ext1}.
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * @param ext1 The {@link ext1} to set.
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * @return Returns the {@link ext2}.
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * @param ext2 The {@link ext2} to set.
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * @return Returns the {@link ext3}.
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * @param ext3 The {@link ext3} to set.
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	/**
	 * @return Returns the {@link ext4}.
	 */
	public String getExt4() {
		return ext4;
	}

	/**
	 * @param ext4 The {@link ext4} to set.
	 */
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	/**
	 * @return Returns the {@link ext5}.
	 */
	public String getExt5() {
		return ext5;
	}

	/**
	 * @param ext5 The {@link ext5} to set.
	 */
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

}
