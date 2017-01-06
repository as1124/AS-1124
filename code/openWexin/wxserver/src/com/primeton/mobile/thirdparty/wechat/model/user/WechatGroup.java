package com.primeton.mobile.thirdparty.wechat.model.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.primeton.mobile.thirdparty.wechat.IWechatModel;

/**
 * 
 * 微信用户组模型。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@JSONType
public class WechatGroup implements IWechatModel{

	/**
	 * 分组ID
	 */
	@JSONField
	int id;

	/**
	 * 分组名称
	 */
	@JSONField
	String name;
	
	/**
	 * 该分组下用户数量
	 */
	@JSONField
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
