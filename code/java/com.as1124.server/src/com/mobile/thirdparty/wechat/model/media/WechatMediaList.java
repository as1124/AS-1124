package com.mobile.thirdparty.wechat.model.media;

import java.util.Collections;
import java.util.List;

import com.mobile.thirdparty.wechat.model.IWechatModel;

/**
 * 素材数量描述模型(image/voice/video)
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class WechatMediaList implements IWechatModel{

	/**
	 * 该类型的素材的总数 
	 */
	private int total_count = 0;
	
	/**
	 * 本次调用获取的素材的数量
	 */
	private int item_count = 0;
	
	/**
	 * 素材列表
	 */
	private List<WechatMedia> item = Collections.emptyList();
	
	public WechatMediaList() {
		// default constructor
	}

	/**
	 * @see #total_count
	 * @return
	 */
	public int getTotal_count() {
		return total_count;
	}

	/**
	 * @see #total_count
	 * @param totalCount
	 */
	public void setTotal_count(int totalCount) {
		this.total_count = totalCount;
	}

	/**
	 * @see #item_count
	 * @return
	 */
	public int getItem_count() {
		return item_count;
	}

	/**
	 * @see #item_count
	 * @param itemCount
	 */
	public void setItem_count(int itemCount) {
		this.item_count = itemCount;
	}

	/**
	 * @see #item
	 * @return
	 */
	public List getItem() {
		return item;
	}

	/**
	 * @see #item
	 * @param item
	 */
	public void setItem(List<WechatMedia> item) {
		this.item = item;
	}
}
