package com.primeton.mobile.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * 微信接口调用时的票据对象（access_token）.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@JSONType
public class AccessToken implements IWechatModel{
	
	/**
	 * 获取的凭证
	 */
	@JSONField
	private String access_token;
	
	/**
	 * 有效时间，单位：秒
	 */
	@JSONField
	private long expires_in;
	
	/**
	 * 时间戳，记录凭证的创建时间
	 */
	private long createTime;
	
	public AccessToken() {
		
	}

	/**
	 * {@link AccessToken#access_token}
	 * @return
	 */
	public String getAccess_token() {
		return access_token;
	}

	/**
	 * {@link AccessToken#access_token}
	 * @param access_token
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * {@link AccessToken#expires_in}
	 * @return
	 */
	public long getExpires_in() {
		return expires_in;
	}

	/**
	 * {@link AccessToken#expires_in}
	 * @param expires_in
	 */
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	/**
	 * {@link AccessToken#createTime}
	 * @return
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * {@link AccessToken#createTime}
	 * @param createTime
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 获取当前access_token是失效
	 * @return <code>true</code> 失效，<code>false</code> 有效
	 */
	public boolean isExpired(){
		long differ = System.currentTimeMillis() - this.createTime;
		long seconds = differ/6000;
		return (seconds >= this.expires_in);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AccessToken){
			return this.access_token.equals(((AccessToken)obj).getAccess_token());
		}
		else return false;
	}
}
