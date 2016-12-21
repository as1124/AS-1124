package com.priemton.mobile.thirdparty.access;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.primeton.mobile.wechat.IWechatConstants;
import com.primeton.mobile.wechat.UserOperations;
import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.user.WechatUserInfo;

public class AccessTokenManager {

	private static Map<String, AbstractAccessToken> tokenMap = new HashMap<String, AbstractAccessToken>();
	
	@SuppressWarnings("unchecked")
	public static <T extends AbstractAccessToken> T getToken(String uniqueID, Map<String, String> parameters, Class<? extends AbstractAccessToken> clazz) {
		AbstractAccessToken token = tokenMap.get(uniqueID);
		if(token==null || token.isExpired()){
			try {
				token = clazz.newInstance();
				token.initFields(parameters);
				tokenMap.put(uniqueID, token);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return (T) token;
	}
	
	public static void main(String[] args) {
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put(IWechatConstants.KEY_APP_ID, "wxfad56e1d17a29e04");
			param.put(IWechatConstants.KEY_APP_SECRET, "d048ee6acfb69cbd0fc3aaa591b1455b");
			WechatAccessToken token = getToken("wxfad56e1d17a29e04", param, WechatAccessToken.class);
			WechatUserInfo info = UserOperations.getUserInfo(token.getAccess_token(), "ooyKpxCrG8A_Y23vsFETNXH4AAj0");
			System.out.println(info);
		} catch (WechatExceprion e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
