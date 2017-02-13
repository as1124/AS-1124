package com.primeton.mobile.thirdparty.wechat.model.user;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;


public class UserBlackList {

	long total;
	
	long count;
	
	String data;
	
	String next_openid;
	
	public UserBlackList() {
	}

	/**
	 * @return the {@link #total}
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total the {@link #total} to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * @return the {@link #count}
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count the {@link #count} to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @return the {@link #data}
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the {@link #data} to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	public String[] getOpenIDs(){
		if(StringUtils.isNotBlank(data)){
			String json = JSONObject.parseObject(this.data).getString("openid");
			List<String> openids = JSONObject.parseArray(json, String.class);
			return openids.toArray(new String[openids.size()]);
		} else {
			return null;
		}
	}
	
	/**
	 * @return the {@link #next_openid}
	 */
	public String getNext_openid() {
		return next_openid;
	}

	/**
	 * @param next_openid the {@link #next_openid} to set
	 */
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}

}
