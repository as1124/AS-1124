package com.as1124.api.model.user;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.as1124.api.wechat.model.IWechatModel;

/**
 * 黑名单模型
 * 
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public class UserBlackList implements IWechatModel {

	private long total = 0;

	private long count = 0;

	private String data = "";

	private String next_openid = "";

	public UserBlackList() {
		// default constructor
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

	public String[] getOpenIDs() {
		if (StringUtils.isNotBlank(data)) {
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
	 * @param nextOpenID the {@link #next_openid} to set
	 */
	public void setNext_openid(String nextOpenID) {
		this.next_openid = nextOpenID;
	}

}
