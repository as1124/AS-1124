package com.primeton.mobile.thirdparty.wechat.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.thirdparty.access.AbstractAccessToken;
import com.primeton.mobile.thirdparty.access.AccessTokenFactory;
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.primeton.mobile.thirdparty.wechat.IWechatConstants;

/**
 * 接口调用时的票据对象（access_token）.
 * <p>access_token是公众号/企业号的全局唯一票据，公众号/企业号调用各接口时都需使用access_token。
 * access_token的有效期为2个小时，超时后调用{@link WechatAccessToken#getToken(String, String)}将自动刷新。</p>
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatAccessToken extends AbstractAccessToken {
	
	/**
	 * 请通过<code>AccessTokenFactory</code>来获取实例对象以实现token过期时系统的平滑过渡
	 * 
	 * @see {@link AccessTokenFactory#getToken(String, List, Class)}
	 */
	protected WechatAccessToken(){
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
	
	protected void initFields(List<BasicNameValuePair> parameters) throws ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/token";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("grant_type", "client_credential"));
//		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, appID));
//		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, appSecret));
		queryStr.addAll(parameters);
		JSONObject json = JSONObject.parseObject(HttpExecuter.executeGetAsString(uri, queryStr));
		String err = json.getString(IWechatConstants.ERROR_CODE);
		if(err==null || err.equals(IWechatConstants.RETURN_CODE_SUCCESS)){
			this.createTime = System.currentTimeMillis();
			this.access_token = json.getString(IWechatConstants.KEY_ACCESS_TOKEN);
			this.expires_in = json.getLong("expires_in");
		} else {
			String errMsg = json.getString(IWechatConstants.ERROR_MSG);
			ThirdPartyRequestExceprion exp = new ThirdPartyRequestExceprion("[errCode="+err+"], "+errMsg);
			throw exp;
		}
	}
	
}
