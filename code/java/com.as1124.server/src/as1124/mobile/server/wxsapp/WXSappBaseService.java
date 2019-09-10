package as1124.mobile.server.wxsapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.AbstractAccessToken;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.model.WechatAccessToken;

/**
 * 微信小程序开放能力对应的后台接口
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class WXSappBaseService {

	/**
	 * 微信小程序用户登录，通过客户端微信授权登录的code换取session_key<br/>
	 * 包含可用字段：<code>openid, session_key, unionid</code>
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static JSONObject login(String appid, String appSecret, String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("appid", appid));
		params.add(new BasicNameValuePair("SECRET", ""));
		params.add(new BasicNameValuePair("js_code", code));
		params.add(new BasicNameValuePair("js_code", "authorization_code"));
		String result = HttpExecuter.executeGetAsString(url, params);
		if (StringUtils.isNotBlank(result)) {
			return JSONObject.parseObject(result);
		} else {
			return null;
		}
	}

	/**
	 * 获取微信小程序接口调用凭证
	 * @param appid
	 * @param appSecret
	 * @return
	 * @throws ThirdPartyRequestExceprion
	 */
	public static WechatAccessToken getToken(String appid, String appSecret) throws ThirdPartyRequestExceprion {
		return WechatAccessToken.getWechatToken(appid, appSecret);
	}

	/**
	 * 用户支付完成后，获取该用户的<code> unionid </code>
	 */
	public static void getUserInfo(AbstractAccessToken apiToken, String openid) {
		String url = " https://api.weixin.qq.com/wxa/getpaidunionid";
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("access_token", apiToken.getAccessToken()));
		params.add(new BasicNameValuePair("openid", openid));
		String result = HttpExecuter.executeGetAsString(url, params);
		if (StringUtils.isNotBlank(result)) {

		} else {

		}
	}
}
