package com.primeton.mobile.thirdparty.wechat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONException;
import com.priemton.mobile.thirdparty.access.AccessTokenFactory;
import com.primeton.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.primeton.mobile.thirdparty.wechat.model.WechatAccessToken;
import com.primeton.mobile.thirdparty.wechat.model.media.WechatMedia;
import com.primeton.mobile.thirdparty.wechat.model.user.WechatUserInfo;

public class TEST {

	public static void main(String[] args) {
		try {
			List<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>();
			param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID,
					"wxfad56e1d17a29e04"));
			param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET,
					"d048ee6acfb69cbd0fc3aaa591b1455b"));
			WechatAccessToken token = AccessTokenFactory.getToken("wxfad56e1d17a29e04", param,
					WechatAccessToken.class);
			WechatMedia media = MediaOperations.uploadPermaentMedia(token.getAccess_token(), "image", "D:/icon.png");
			WechatUserInfo info = UserOperations.getUserInfo(
					token.getAccess_token(), "ooyKpxCrG8A_Y23vsFETNXH4AAj0");
			System.out.println(info);
		} catch (ThirdPartyRequestExceprion e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
