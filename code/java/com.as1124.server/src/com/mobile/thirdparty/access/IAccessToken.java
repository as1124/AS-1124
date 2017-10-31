package com.mobile.thirdparty.access;

/**
 * 
 * 授权token模型接口
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public interface IAccessToken {

	/**
	 * 当前的接口票据是否失效
	 * 
	 * @return <code>true</code> 失效，<code>false</code> 有效
	 */
	public boolean isExpired();

	/**
	 * 获取接口调用凭证串码
	 * 
	 * @return
	 */
	public String getAccessToken();

}
