package com.as1124.api.wechat;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.as1124.api.access.AbstractAccessToken;

/**
 * 微信卡券操作
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatCardOperations {

	public String uploadCardImg(AbstractAccessToken token, String imgPath) {
		return MediaOperations.uploadImgForNews(token, imgPath);
	}

	public void createWXCard(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/card/create";
		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccessToken()));

	}
}
