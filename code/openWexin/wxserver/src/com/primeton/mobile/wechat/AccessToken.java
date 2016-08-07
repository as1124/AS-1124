package com.primeton.mobile.wechat;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.IWechatModel;

/**
 * 微信接口调用时的票据对象（access_token）.
 * <p>access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。
 * access_token的有效期为2个小时，超时后调用{@link AccessToken#getToken(String, String)}将自动刷新。</p>
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class AccessToken implements IWechatModel{
	
	private static HashMap<String, AccessToken> tokenMap = new HashMap<String, AccessToken>();
	
	/**
	 * 获取的凭证
	 */
	private String access_token = null;
	
	/**
	 * 有效时间，单位：秒
	 */
	private long expires_in = 0;
	
	/**
	 * 时间戳，记录凭证的创建时间
	 */
	private long createTime = 0;
	
	/**
	 * 请使用AccessToken#getInstance方法来实现token过期时系统的平滑过渡
	 * 
	 * @see AccessToken#getInstance(String, String)
	 */
	protected AccessToken() {
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
	 * 获取的是当前access_token的有效时间而不是access_token的剩余时间。
	 * 这个有效时间是创建时从微信官方获取的。
	 * {@link AccessToken#expires_in}
	 * @see #isExpired()
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
		if(obj instanceof AccessToken){
			return this.access_token.equals(((AccessToken)obj).getAccess_token());
		}
		else return false;
	}
	
	/**
	 * @param appID
	 * @param appSecret
	 * @return
	 * @throws WechatExceprion
	 */
	public static AccessToken getToken(String appID, String appSecret) throws WechatExceprion{
		AccessToken token = tokenMap.get(appID);
		if(token==null || token.isExpired()){
			token = getAccessToken(appID, appSecret);
			tokenMap.put(appID, token);
		}
		return token;
	}
	
	/**
	 * 
	 * @param appID 申请的公众号ID
	 * @param appSecret 申请的公众号的appsecret
	 * @return access_token
	 * @throws WechatExceprion 
	 */
	protected static AccessToken getAccessToken(String appID, String appSecret) throws WechatExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/token";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("grant_type", "client_credential"));
		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, appID));
		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, appSecret));
		HttpExecuter.executeGetAsString(uri, queryStr);
		
		return null;
		
	}
	
	public static void main(String[] args){
		try {
			AccessToken.getToken("wxc70c5d9aab4f6a2b", "f3ca23ccf76c301f2158862db65cfdad");
		} catch (WechatExceprion e) {
			e.printStackTrace();
		}
		
	}
	
}
