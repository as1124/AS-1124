package com.mobile.thirdparty.wechat.model.user;

import com.mobile.thirdparty.wechat.IWechatModel;

/**
 * 
 * 微信用户组模型。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatGroup implements IWechatModel{

	/**
	 * 分组ID
	 */
	int id;

	/**
	 * 分组名称
	 */
	String name;
	
	/**
	 * 该分组下用户数量
	 */
	int count = 0;
	
	public WechatGroup() {
	}

	/**
	 * @see #id
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @see #id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @see #name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see #name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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
	
}
