package com.primeton.mobile.wechat.exception;

/**
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatExceprion extends Throwable{

	private static final long serialVersionUID = 5178302636162279319L;

	public WechatExceprion() {
	}
	
	public WechatExceprion(String message) {
		super(message);
	}
	
}
