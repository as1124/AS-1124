package com.as1124.server.wxsapp.resources;

import org.apache.ibatis.type.Alias;

/**
 * 商品信息
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Alias("GoodsInfo")
public class GoodsInfo {

	private int id;

	/**
	 * 商品编号
	 */
	private String goodsno;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 缩略图URL
	 */
	private String previewIcon;

	/**
	 * 所属分类id, 属于多个分类用以用'#'连接
	 */
	private String categoryid;

	/**
	 * 商品详情介绍H5地址
	 */
	private String h5url;

	/**
	 * 售价
	 */
	private String price;

	/**
	 * 原价
	 */
	private String originalPrice;

	/**
	 * 存货量
	 */
	private int amount = 0;

	/**
	 * 显示顺序
	 */
	private int showOrder;

	/**
	 * 商品详细描述：颜色、尺码等
	 */
	private String detail;

	private String ext1, ext2, ext3, ext4, ext5;

	public GoodsInfo() {
		// default constructor
	}

	public GoodsInfo(String no) {
		this.goodsno = no;
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
	 * @return Returns the {@link goodsno}.
	 */
	public String getGoodsno() {
		return goodsno;
	}

	/**
	 * @param goodsno The {@link goodsno} to set.
	 */
	public void setGoodsno(String goodsno) {
		this.goodsno = goodsno;
	}

	/**
	 * @return Returns the {@link name}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The {@link name} to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the {@link previewIcon}.
	 */
	public String getPreviewIcon() {
		return previewIcon;
	}

	/**
	 * @param previewIcon The {@link previewIcon} to set.
	 */
	public void setPreviewIcon(String previewIcon) {
		this.previewIcon = previewIcon;
	}

	/**
	 * @return Returns the {@link categoryid}.
	 */
	public String getCategoryid() {
		return categoryid;
	}

	/**
	 * @param categoryid The {@link categoryid} to set.
	 */
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}

	/**
	 * @return Returns the {@link h5url}.
	 */
	public String getH5url() {
		return h5url;
	}

	/**
	 * @param h5url The {@link h5url} to set.
	 */
	public void setH5url(String h5url) {
		this.h5url = h5url;
	}

	/**
	 * @return Returns the {@link price}.
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price The {@link price} to set.
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return Returns the {@link originalPrice}.
	 */
	public String getOriginalPrice() {
		return originalPrice;
	}

	/**
	 * @param originalPrice The {@link originalPrice} to set.
	 */
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	/**
	 * @return Returns the {@link amount}.
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount The {@link amount} to set.
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return Returns the {@link showOrder}.
	 */
	public int getShowOrder() {
		return showOrder;
	}

	/**
	 * @param amount The {@link #showOrder} to set.
	 */
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	/**
	 * @return Returns the {@link detail}.
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail The {@link detail} to set.
	 */
	public void setDetail(String detail) {
		this.detail = detail;
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
