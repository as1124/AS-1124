package com.mobile.thirdparty.wechat;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mobile.thirdparty.access.AbstractAccessToken;

/**
 * 微信卡券操作
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatCardOperations {

	public String uploadCardImg(AbstractAccessToken token, String imgPath){
		return new MediaOperations().uploadNewsImg(token, imgPath);
	}
	
	public void createWXCard(AbstractAccessToken token){
		String uri = "https://api.weixin.qq.com/card/create";
		List<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		
	}
}
