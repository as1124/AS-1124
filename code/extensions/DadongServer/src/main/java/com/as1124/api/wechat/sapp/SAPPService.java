package com.as1124.api.wechat.sapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.HttpExecuter;

public class SAPPService {

	/**
	 * 通过授权码获取session_key
	 * 
	 * @param appid
	 * @param secret
	 * @param authCode
	 * @return
	 */
	public static JSONObject authCode2Session(String appid, String secret, String authCode) {
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("appid", appid));
		params.add(new BasicNameValuePair("secret", secret));
		params.add(new BasicNameValuePair("js_code", authCode));
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		String result = HttpExecuter.executeGetAsString(url, params);
		return JSONObject.parseObject(result);
	}
}
