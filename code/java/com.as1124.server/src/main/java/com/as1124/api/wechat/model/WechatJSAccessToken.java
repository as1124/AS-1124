package com.as1124.api.wechat.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.as1124.api.access.AccessTokenFactory;
import com.as1124.api.access.HttpExecuter;
import com.as1124.api.access.exception.ThirdPartyRequestExceprion;
import com.as1124.api.wechat.WechatConstants;

/**
 * 微信网页授权access_token模型，
 * access_token有效期一般是<code>7200s</code>，refresh_token的有效期是30天
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class WechatJSAccessToken extends WechatAccessToken {

	/**
	 * refresh_token的创建时间
	 */
	private long refreshCreateTime = 0;

	/**
	 * 网页授权后去的用户openid
	 */
	private String openid;

	@Override
	protected void initFields(List<NameValuePair> parameters) throws ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/sns/oauth2/access_token";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		//		queryStr.add(new BasicNameValuePair("grant_type", "authorization_code"));
		//		queryStr.add(new BasicNameValuePair("code", code));
		//		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, appID));
		//		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, appSecret));
		queryStr.addAll(parameters);
		JSONObject json = JSONObject.parseObject(HttpExecuter.executeGetAsString(uri, queryStr));
		String err = json.getString(WechatConstants.ERROR_CODE);
		if (err == null || err.equals(WechatConstants.RETURN_CODE_SUCCESS)) {
			setCreateTime(System.currentTimeMillis());
			setRefreshCreateTime(getCreateTime());
			setAccessToken(json.getString(WechatConstants.KEY_ACCESS_TOKEN));
			setExpireIn(json.getLong("expires_in"));
			setRefreshToken(json.getString("refresh_token"));
			setOpenid(json.getString("openid"));
			setScope(json.getString("scope"));
		} else {
			String errMsg = json.getString(WechatConstants.ERROR_MSG);
			ThirdPartyRequestExceprion exp = new ThirdPartyRequestExceprion("[errCode=" + err + "], " + errMsg);
			throw exp;
		}
	}

	@Override
	public boolean isExpired() {
		return true;
	}

	protected void refreshAccessToken(List<BasicNameValuePair> parameters) throws ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("grant_type", "refresh_token"));
		queryStr.add(new BasicNameValuePair("refresh_token", getRefreshToken()));
		for (BasicNameValuePair param : parameters) {
			String key = param.getName();
			if (key.equals(WechatConstants.KEY_APP_ID)) {
				queryStr.add(new BasicNameValuePair(WechatConstants.KEY_APP_ID, param.getValue()));
				break;
			}
		}

		JSONObject json = JSONObject.parseObject(HttpExecuter.executeGetAsString(uri, queryStr));
		String err = json.getString(WechatConstants.ERROR_CODE);
		if (err == null || err.equals(WechatConstants.RETURN_CODE_SUCCESS)) {
			setCreateTime(System.currentTimeMillis());
			setAccessToken(json.getString(WechatConstants.KEY_ACCESS_TOKEN));
			setExpireIn(json.getLong("expires_in"));
			setRefreshToken(json.getString("refresh_token"));
			this.openid = json.getString("openid");
			setScope(json.getString("scope"));
		} else {
			String errMsg = json.getString(WechatConstants.ERROR_MSG);
			ThirdPartyRequestExceprion exp = new ThirdPartyRequestExceprion("[errCode=" + err + "], " + errMsg);
			throw exp;
		}
	}

	/**
	 * @see #openid
	 * @return openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid 设置 <code>{@link #openid}</code>字段的值
	 * @see #openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @see #refreshCreateTime
	 * @return refresh_create_time
	 */
	protected long getRefreshCreateTime() {
		return refreshCreateTime;
	}

	/**
	 * @param refresh_create_time 设置 <code>{@link #refreshCreateTime}</code>字段的值
	 * @see #refresh_create_time
	 */
	protected void setRefreshCreateTime(long refresh_create_time) {
		this.refreshCreateTime = refresh_create_time;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}


	/**
	 * 通过微信网页授权获取网页接口调用的token
	 * @param appid 公众号appID
	 * @param appSecret 公众号appsecret
	 * @param code 微信网页授权返回的code码
	 * @return
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatJSAccessToken getWechatWebAuthorizeToken(String appid, String appSecret, String code)
			throws ThirdPartyRequestExceprion {
		List<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("grant_type", "authorization_code"));
		queryStr.add(new BasicNameValuePair("code", code));
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_APP_ID, appid));
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_APP_SECRET, appSecret));
		WechatJSAccessToken token = AccessTokenFactory.getToken(appid, queryStr, WechatJSAccessToken.class);
		return token;
	}
}
