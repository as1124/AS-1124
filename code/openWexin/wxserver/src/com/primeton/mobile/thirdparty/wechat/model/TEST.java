package com.primeton.mobile.thirdparty.wechat.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.primeton.mobile.thirdparty.access.AccessTokenFactory;
import com.primeton.mobile.thirdparty.wechat.HotlineOperations;
import com.primeton.mobile.thirdparty.wechat.IWechatConstants;

public class TEST {

	public static void main(String[] args) {
		List<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>();
		//移动平台服务号
		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, "wxfad56e1d17a29e04"));
		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, "d048ee6acfb69cbd0fc3aaa591b1455b"));
		
		//EAII
//		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, "wx0b96b87f1deb8ffb"));
//		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, "d6ef682a84531ed8e3ef0adc44390a40"));
		try {
			WechatAccessToken token = AccessTokenFactory.getToken("huangjw", param, WechatAccessToken.class);
//			WechatAccessToken token = new WechatAccessToken();
//			token.setAccess_token("MveByun9Mb_IPXZyPM5zE_unkJ-l9QVn4s8Rz0Ln2xMypTvvnDuhpTLfjxP8QcRWbIpVnlg6kNSdxBF7GXfbSRc2TthcZjrXgbr8ycpMEfiI4FgutgCyK_UbooBbD3zSJTXaACAQAE");
			
			addKefu(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addKefu(WechatAccessToken token) throws IOException{
		HotlineAccount account = new HotlineAccount();
		account.setKf_account("kf2001@gh_c27d5e63d22d");
		account.setNickname("元宝");
		account.setPassword("000000");
		HotlineOperations service = new HotlineOperations();
//		service.addHotlineAccount(token, account);
//		service.getAllServiceAccount(token.getAccess_token());
		service.setAccountImage(token, account.getKf_account(), "D:/icon_app/icon.png");
//		service.getOnlineServiceAccounts(token);
		
	}
	
	public void testMedia(){
		
	}
}
