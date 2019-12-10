package com.as1124.api.model.user;

import com.as1124.api.wechat.model.IWechatModel;

/**
 * 
 * 微信用户组模型。
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class WechatGroup implements IWechatModel{

	/**
	 * 分组ID
	 */
	private int id = -1;

	/**
	 * 分组名称
	 */
	private String name = "";
	
	/**
	 * 该分组下用户数量
	 */
	private int count = 0;
	
	public WechatGroup() {
		// default constructor
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
