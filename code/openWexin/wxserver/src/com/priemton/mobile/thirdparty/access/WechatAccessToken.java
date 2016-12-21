package com.priemton.mobile.thirdparty.access;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.wechat.HttpExecuter;
import com.primeton.mobile.wechat.IWechatConstants;
import com.primeton.mobile.wechat.exception.WechatExceprion;

/**
 * 微信接口调用时的票据对象（access_token）.
 * <p>access_token是公众号/企业号的全局唯一票据，公众号/企业号调用各接口时都需使用access_token。
 * access_token的有效期为2个小时，超时后调用{@link WechatAccessToken#getToken(String, String)}将自动刷新。</p>
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatAccessToken extends AbstractAccessToken {
	
	/**
	 * 获取的凭证
	 */
	private String access_token = null;
	
	/**
	 * 有效时间，单位：秒
	 */
	private long expires_in = 0;
	
	/**
	 * 请使用AccessToken#getInstance方法来实现token过期时系统的平滑过渡
	 * @throws Exception 
	 * 
	 * @see WechatAccessToken#getInstance(String, String)
	 */
	protected WechatAccessToken(){
	}

	/**
	 * {@link WechatAccessToken#access_token}
	 * @return
	 */
	public String getAccess_token() {
		return access_token;
	}

	/**
	 * {@link WechatAccessToken#access_token}
	 * @param access_token
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * 获取的是当前access_token的有效时间而不是access_token的剩余时间。
	 * 这个有效时间是创建时从微信官方获取的。
	 * {@link WechatAccessToken#expires_in}
	 * @see #isExpired()
	 * @return
	 */
	public long getExpires_in() {
		return expires_in;
	}

	/**
	 * {@link WechatAccessToken#expires_in}
	 * @param expires_in
	 */
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
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
		if(obj instanceof WechatAccessToken){
			return this.access_token.equals(((WechatAccessToken)obj).getAccess_token());
		}
		else return false;
	}
	
	protected void initFields(Map<String, String> parameters) throws Exception{
		String uri = "https://api.weixin.qq.com/cgi-bin/token";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("grant_type", "client_credential"));
//		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, appID));
//		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, appSecret));
		Iterator<String> it = parameters.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			queryStr.add(new BasicNameValuePair(key, parameters.get(key)));
		}
		JSONObject json = JSONObject.parseObject(HttpExecuter.executeGetAsString(uri, queryStr));
		String err = json.getString(IWechatConstants.ERROR_CODE);
		if(err==null || err.equals(IWechatConstants.RETURN_CODE_SUCCESS)){
			this.createTime = System.currentTimeMillis();
			this.access_token = json.getString(IWechatConstants.KEY_ACCESS_TOKEN);
			this.expires_in = json.getLong("expires_in");
		} else {
			String errMsg = json.getString(IWechatConstants.ERROR_MSG);
			Exception exp = new WechatExceprion("[errCode="+err+"], "+errMsg);
			throw exp;
		}
	}
	
}
