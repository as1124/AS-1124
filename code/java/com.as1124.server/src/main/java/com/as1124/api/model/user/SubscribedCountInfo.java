package com.as1124.api.model.user;

import com.alibaba.fastjson.JSONObject;
import com.as1124.api.wechat.model.IWechatModel;

/**
 * 公众号关注用户的统计信息
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class SubscribedCountInfo implements IWechatModel {

	/**
	 * 关注该公众号的总人数
	 */
	private long total = 0;

	/**
	 * 本次拉取的openid个数，最大值为10000
	 */
	private int count = 0;

	/**
	 * json格式的openID列表数据
	 */
	private String data = "";

	/**
	 * 拉取列表的后一个用户的openid 
	 */
	private String next_openid = "";

	public SubscribedCountInfo() {
		// default constructor
	}

	/**
	 * @see #total
	 * @return
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @see #total
	 * @param total
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * @see #count
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @see #count
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 获取本次拉取到的关注该公众用户的openid
	 * @return
	 */
	public String[] getData() {
		return JSONObject.parseArray(JSONObject.parseObject(data).getString("openid")).toArray(new String[] {});
	}

	/**
	 * 
	 * @param jsonData json格式串(<code>{"openid":[openids]}</code>)
	 */
	public void setData(String jsonData) {
		this.data = jsonData;
	}

	/**
	 * @see #next_openid
	 * @return
	 */
	public String getNext_openid() {
		return next_openid;
	}

	/**
	 * @see #next_openid
	 * @param nextOpenID
	 */
	public void setNext_openid(String nextOpenID) {
		this.next_openid = nextOpenID;
	}

}
