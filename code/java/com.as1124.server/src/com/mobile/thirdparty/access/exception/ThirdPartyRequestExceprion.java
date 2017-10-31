package com.mobile.thirdparty.access.exception;

/**
 * 调用第三方平台接口时异常.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ThirdPartyRequestExceprion extends Exception{

	private static final long serialVersionUID = 5178302636162279319L;

	public ThirdPartyRequestExceprion() {
	}
	
	public ThirdPartyRequestExceprion(String message) {
		super(message);
	}
	
}
