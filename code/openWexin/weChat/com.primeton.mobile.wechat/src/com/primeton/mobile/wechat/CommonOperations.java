package com.primeton.mobile.wechat;

import java.io.IOException;

import org.apache.commons.httpclient.NameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.eos.system.annotation.Bizlet;
import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.AccessToken;

/**
 * 基础接口。
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class CommonOperations {

	/**
	 * access_token是公众号、服务号的全局唯一票据，在调用各接口时都需使用access_token。
	 * 默认的token有效时间为7200秒(2小时)，需定时刷新，重复获取将导致上次获取的access_token失效。 
	 * @param appID 申请的公众号ID
	 * @param appSecret 申请的公众号的appsecret
	 * @return access_token
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("getAccessToken")
	public static AccessToken getAccessToken(String appID, String appSecret) throws IOException, WechatExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/token";
		NameValuePair[] queryStr = new NameValuePair[3];
		queryStr[0] = new NameValuePair("grant_type", "client_credential");
		queryStr[1] = new NameValuePair(IWechatConstants.KEY_APP_ID, appID);
		queryStr[2] = new NameValuePair(IWechatConstants.KEY_APP_SECRET, appSecret);
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject result =  JSONObject.parseObject(response);
		String returnCode = result.getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			AccessToken token = JSONObject.parseObject(response, AccessToken.class);
			token.setCreateTime(System.currentTimeMillis());
			return token;
		}
		else return null;
	}
	
	/**
	 * 获取微信服务器的IP地址列表
	 * @param accessToken
	 * @return list of callback-IPs
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	@Bizlet("getWechatIPAddresses")
	public static String[] getWechatIPAddresses(String accessToken) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject result =  JSONObject.parseObject(response);
		String returnCode = result.getString(IWechatConstants.ERROR_CODE);
		if(returnCode ==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseArray(result.getString("ip_list")).toArray(new String[]{});
		}
		else return null;
	}

}
