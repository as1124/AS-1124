package com.primeton.mobile.thirdparty.wechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.thirdparty.access.AbstractAccessToken;
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;

/**
 * 
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class CommonOperations {

	/**
	 * 获取微信服务器的IP地址列表
	 * <p>如果公众号基于消息接收安全上的考虑，需要获知微信服务器的IP地址列表，以便识别出哪些消息是微信官方推送给你的，
	 * 哪些消息可能是他人伪造的，可以通过该接口获得微信服务器IP地址列表。</p>
	 * @param accessToken
	 * @return list of callback-IPs
	 * @throws ThirdPartyRequestExceprion 
	 */
	public String[] getWechatIPAddresses(AbstractAccessToken token) throws ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject result =  JSONObject.parseObject(response);
		String returnCode = result.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseArray(result.getString("ip_list")).toArray(new String[]{});
		}
		else throw new ThirdPartyRequestExceprion(response);
	}

	/**
	 * 清零公众号的所有api调用（包括第三方帮其调用）次数. 
	 * <strong>每个帐号每月共10次清零操作机会，清零生效一次即用掉一次机会</strong>
	 * <br/>
	 * @param token
	 */
	public boolean clearQuota(AbstractAccessToken token, String appid){
		String uri = "https://api.weixin.qq.com/cgi-bin/clear_quota";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject json = new JSONObject();
		json.put("appid", appid);
		HttpEntity requestEntity = new StringEntity(json.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String response = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject result =  JSONObject.parseObject(response);
		String returnCode = result.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else return false;
	}
	
	/**
	 * 校验接入请求是否来自微信。
	 * <li>web端在第一次接入微信时如果校验确认请求来自微信，此时应当原样向微信返回
	 * <code>echostr</code>字段值才能保证接入成功。<br>
	 * <li>每次开发者接收用户消息的时候，微信也都会带上三个参数（signature、timestamp、nonce）
	 * 发送到开发者设置的URL上，开发者依然可以通过对签名的效验判断此条消息是否真是
	 * 
	 * @param signature 微信加密签名
	 * @param token 公众号申请时所配置的token串，不是access_token
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	public boolean checkSignature(String signature, String token, String timestamp, String nonce) {
		String[] array = new String[] { token, timestamp, nonce };
		Arrays.sort(array, new Comparator<String>() {

			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		String tempStr = array[0] + array[1] + array[2];
		// SHA1签名
		String resultStr = DigestUtils.sha1Hex(tempStr);
		if (resultStr.equals(signature)) {
			return true;
		} else
			return false;
	}
}
