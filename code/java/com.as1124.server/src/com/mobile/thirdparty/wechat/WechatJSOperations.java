package com.mobile.thirdparty.wechat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.AccessTokenFactory;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.model.WechatJSAccessToken;
import com.mobile.thirdparty.wechat.model.WechatJSTicket;

/**
 * 微信JS-SDK工具类
 * 
 * @author huangjw(mailto:haungjw@primeton.com)
 *
 */

public class WechatJSOperations {

	private static Map<String, WechatJSTicket> jsticketManager = new HashMap<String, WechatJSTicket>();
	
	/**
	 * 获取微信js-sdk调用的票据
	 * 
	 * @param appid 公众号appID
	 * @param appSecret 公众号appsecret
	 * @return
	 * @throws ThirdPartyRequestExceprion 
	 */
	
	public String getJSTicket(String appid, String appSecret) throws ThirdPartyRequestExceprion{
		WechatJSTicket jsToken = jsticketManager.get(appid);
		if(jsToken==null || jsToken.isExpired()){
			String uri = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
			List<NameValuePair> query = new ArrayList<NameValuePair>();
			query.add(new BasicNameValuePair("access_token", 
					WechatJSAccessToken.getWechatToken(appid, appSecret).getAccessToken()));
			query.add(new BasicNameValuePair("type", "jsapi"));
			String result = HttpExecuter.executeGetAsString(uri, query);
			String returnCode = JSONObject.parseObject(result).getString(WechatConstants.ERROR_CODE);
			if(returnCode==null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
				jsToken = JSONObject.parseObject(result, WechatJSTicket.class);
				jsToken.setCreateTime(System.currentTimeMillis());
				jsticketManager.put(appid, jsToken);
				return jsToken.getTicket();
			} else {
				System.err.println(WechatConstants.MSG_TAG+result);
				return null;
			}
		} else {
			return jsToken.getTicket();
		}
		
		
	}
	
	/**
	 * 当前系统时间戳
	 * @return
	 */
	
	public String currentSystemTime(){
		return System.currentTimeMillis()+"";
	}
	
	/**
	 * 生成js接口使用签名
	 * @param jsticket
	 * @param nonceStr
	 * @param timeStamp
	 * @param url
	 * @return
	 */
	
	public String generateJSSignature(String jsticket, String nonceStr, String timeStamp, String url){
		jsticket = "jsapi_ticket="+jsticket;
		nonceStr = "noncestr="+nonceStr;
		timeStamp = "timestamp="+timeStamp;
		url = "url="+url;
		return DigestUtils.shaHex(jsticket+"&"+nonceStr+"&"+timeStamp+"&"+url);
	}
	
	public static void removeJSTicket(String appid){
		jsticketManager.remove(appid);
	}
	
}
