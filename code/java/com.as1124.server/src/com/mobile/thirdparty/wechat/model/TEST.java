package com.mobile.thirdparty.wechat.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mobile.thirdparty.access.AccessTokenFactory;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.CommonOperations;
import com.mobile.thirdparty.wechat.MediaOperations;

public class TEST {

	public static void main(String[] args) {
		String appid = "wxc70c5d9aab4f6a2b";
		String appSecret = "f3ca23ccf76c301f2158862db65cfdad";
		List<NameValuePair> parameters = new ArrayList<>();
		parameters.add(new BasicNameValuePair("appid", appid));
		parameters.add(new BasicNameValuePair("secret", appSecret));
		parameters.add(new BasicNameValuePair("grant_type", "client_credential"));
//		try {
//			WechatAccessToken token = AccessTokenFactory.getToken(appid, parameters, WechatAccessToken.class);
			WechatAccessToken token = new WechatAccessToken();
			token.setAccessToken("6ciG3zhqe1nNfv0ZM9SoEAiT14fXqEiLAdULx6-bB6vDTQ9ufcy2UP542YVgBb3oZ3W_gWIW_ghdGkEli-bhlGyV0Ln29u0WK_2snHSfmSUROBgAFANKX");
			
			
			System.out.println(token.getAccessToken());
//		} catch (ThirdPartyRequestExceprion e) {
//			e.printStackTrace();
//		}

	}

}
