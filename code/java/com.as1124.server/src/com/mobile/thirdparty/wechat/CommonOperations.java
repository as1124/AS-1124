package com.mobile.thirdparty.wechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.AbstractAccessToken;
import com.mobile.thirdparty.access.HttpExecuter;

/**
 * 微信基础功能接口
 * 
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public class CommonOperations {

	static Logger logger = Logger.getLogger(CommonOperations.class.getName());

	private CommonOperations() {
	}

	/**
	 * 获取微信服务器的IP地址列表
	 * <p>如果公众号基于消息接收安全上的考虑，需要获知微信服务器的IP地址列表，以便识别出哪些消息是微信官方推送给你的，
	 * 哪些消息可能是他人伪造的，可以通过该接口获得微信服务器IP地址列表。</p>
	 * @param token
	 * @return 微信服务器地址列表
	 */
	public static String[] getWechatIPAddresses(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject result = JSONObject.parseObject(response);
		String returnCode = result.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(result.getString("ip_list")).toArray(new String[] {});
		} else {
			logger.log(Level.SEVERE, response);
			return new String[0];
		}
	}

	/**
	 * 清零公众号的所有api调用（包括第三方帮其调用）次数. <br/>
	 * <strong>每个帐号每月共10次清零操作机会，清零生效一次即用掉一次机会</strong>
	 * 
	 * @param token
	 * @param appid
	 * @return
	 */
	public static boolean clearQuota(AbstractAccessToken token, String appid) {
		String uri = "https://api.weixin.qq.com/cgi-bin/clear_quota";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject json = new JSONObject();
		json.put("appid", appid);
		HttpEntity requestEntity = new StringEntity(json.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String response = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject result = JSONObject.parseObject(response);
		String returnCode = result.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return true;
		} else {
			logger.log(Level.SEVERE, response);
			return false;
		}
	}

	/**
	 * 校验请求或消息签名是否合法
	 * <li>web端在第一次接入微信时如果校验确认请求来自微信，此时应当原样向微信返回
	 * <code>echostr</code>字段值才能保证接入成功。<br>
	 * <li>每次开发者接收用户消息的时候，微信也都会带上三个参数（signature、timestamp、nonce）
	 * 发送到开发者设置的URL上，开发者依然可以通过对签名的效验判断此条消息是否来自微信
	 * 
	 * @param signature 微信加密签名
	 * @param token 公众号申请时所配置的token串，不是access_token
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	public static boolean checkSignature(String signature, String token, String timestamp, String nonce) {
		String[] array = new String[] { token, timestamp, nonce };
		Arrays.sort(array, new Comparator<String>() {

			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		String tempStr = array[0] + array[1] + array[2];
		// SHA1签名
		String resultStr = DigestUtils.sha1Hex(tempStr);
		return resultStr.equals(signature);
	}

	/**
	 * 将字段按照微信的要求（自定排序后拼接做sha1运算）生成签名串
	 * 
	 * @param keys 参与签名的字段
	 * @return
	 */
	public static String generateWechatSignature(String... keys) {
		Arrays.sort(keys, new Comparator<String>() {

			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});

		StringBuilder tempStr = new StringBuilder();
		for (String str : keys) {
			tempStr.append(str);
		}
		// SHA1签名
		return DigestUtils.sha1Hex(tempStr.toString());
	}
}
