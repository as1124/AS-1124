package com.primeton.mobile.wechat.exception;

/**
 * 微信处理结果异常时信息
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatExceprion extends Exception{

	private static final long serialVersionUID = 5178302636162279319L;

	public WechatExceprion() {
	}
	
	public WechatExceprion(String message) {
		super(message);
	}
	
}
