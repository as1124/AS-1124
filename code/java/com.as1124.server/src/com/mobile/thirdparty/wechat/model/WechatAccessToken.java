package com.mobile.thirdparty.wechat.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.AbstractAccessToken;
import com.mobile.thirdparty.access.AccessTokenFactory;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.WechatConstants;

/**
 * 接口调用时的票据对象（access_token）.
 * <p>access_token是公众号/企业号的全局唯一票据，公众号/企业号调用各接口时都需使用access_token。
 * access_token的有效期为2个小时，超时后调用{@link WechatAccessToken#getToken(String, String)}将自动刷新。
 * </p>
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class WechatAccessToken extends AbstractAccessToken {

	/**
	 * 请通过<code>AccessTokenFactory</code>来获取实例对象以实现token过期时系统的平滑过渡
	 * 
	 * @see {@link AccessTokenFactory#getToken(String, List, Class)}
	 */
	protected WechatAccessToken() {
	}

	/**
	 * 判断当前access_token是失效
	 * @return <code>true</code> 失效，<code>false</code> 有效
	 */
	public boolean isExpired() {
		if (getCreateTime() == 0)
			return true;

		// 已经花费的时间（单位：毫秒）
		long differ = System.currentTimeMillis() - getCreateTime();
		long differInSeconds = differ / 1000;

		// 预留10分钟的意外时间
		differInSeconds = differInSeconds + 10 * 60;

		return (differInSeconds >= this.getExpireIn());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && WechatAccessToken.class.isInstance(obj)) {
			return ((WechatAccessToken) obj).getAccessToken().equals(getAccessToken());
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return MessageFormat.format("[{0}] {1}", this.clientID, this.accessToken);
	}

	protected void initFields(List<NameValuePair> parameters) throws ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/token";

		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.addAll(parameters);
		JSONObject json = JSONObject.parseObject(HttpExecuter.executeGetAsString(uri, queryStr));
		int err = json.getIntValue(WechatConstants.ERROR_CODE);
		if (err == WechatConstants.RETURN_CODE_SUCCESS) {
			setCreateTime(System.currentTimeMillis());
			setAccessToken(json.getString(WechatConstants.KEY_ACCESS_TOKEN));
			setExpireIn(json.getLong("expires_in"));
		} else {
			String errMsg = json.getString(WechatConstants.ERROR_MSG);
			throw new ThirdPartyRequestExceprion("[errCode=" + err + "], " + errMsg);
		}
	}

	/**
	 * 获取微信公众号接口调用凭据
	 * @param appid 公众号appID
	 * @param appSecret 公众号appsecret
	 * @return
	 * @throws ThirdPartyRequestExceprion
	 */
	public static WechatAccessToken getWechatToken(String appid, String appSecret) throws ThirdPartyRequestExceprion {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("appid", appid));
		parameters.add(new BasicNameValuePair("secret", appSecret));
		parameters.add(new BasicNameValuePair("grant_type", "client_credential"));
		return AccessTokenFactory.getToken(appid, parameters, WechatAccessToken.class);
	}
}
