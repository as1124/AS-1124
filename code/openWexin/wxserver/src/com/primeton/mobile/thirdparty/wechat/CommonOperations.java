package com.primeton.mobile.thirdparty.wechat;


import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.priemton.mobile.thirdparty.access.AbstractAccessToken;
import com.priemton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;

/**
 * 
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class CommonOperations {

	/**
	 * 获取微信服务器的IP地址列表
	 * <p>如果公众号基于消息接收安全上的考虑，需要获知微信服务器的IP地址列表，以便识别出哪些消息是微信官方推送给你的，
	 * 哪些消息可能是他人伪造的，可以通过该接口获得微信服务器IP地址列表。</p>
	 * @param accessToken
	 * @return list of callback-IPs
	 * @throws ThirdPartyRequestExceprion 
	 */
	public String[] getWechatIPAddresses(AbstractAccessToken token) throws ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject result =  JSONObject.parseObject(response);
		String returnCode = result.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseArray(result.getString("ip_list")).toArray(new String[]{});
		}
		else throw new ThirdPartyRequestExceprion(response);
	}

	/**
	 * 清零公众号的所有api调用（包括第三方帮其调用）次数. 
	 * <strong>每个帐号每月共10次清零操作机会，清零生效一次即用掉一次机会</strong>
	 * <br/>
	 * @param token
	 */
	public boolean clearQuota(AbstractAccessToken token, String appid){
		String uri = "https://api.weixin.qq.com/cgi-bin/clear_quota";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject json = new JSONObject();
		json.put("appid", appid);
		HttpEntity requestEntity = new StringEntity(json.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String response = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject result =  JSONObject.parseObject(response);
		String returnCode = result.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else return false;
	}
	
}
