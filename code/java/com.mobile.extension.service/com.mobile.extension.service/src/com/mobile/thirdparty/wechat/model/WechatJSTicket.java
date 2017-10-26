package com.mobile.thirdparty.wechat.model;

import com.mobile.thirdparty.wechat.IWechatModel;

/**
 * 微信js-api ticket模型对象
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatJSTicket implements IWechatModel{

	/**
	 * js-api ticket
	 */
	private String ticket;
	
	/**
	 * ticket有效期
	 */
	private long expires_in = 0;
	
	/**
	 * ticket创建时间
	 */
	private long createTime = 0;
	
	public WechatJSTicket() {
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
	
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * 判断当前js-api ticket是失效
	 * @return <code>true</code> 失效，<code>false</code> 有效
	 */
	public boolean isExpired(){
		if(this.createTime == 0)
			return true;
		
		// 已经花费的时间（单位：毫秒）
		long differ = System.currentTimeMillis() - this.createTime;
		long seconds = differ/1000;
		
		// 预留10分钟的意外时间
		seconds = seconds + 10*60;
		
		return (seconds >= this.expires_in);
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof WechatJSTicket){
			return this.ticket.equals(((WechatJSTicket)obj).getTicket());
		}
		else return false;
	}
}
