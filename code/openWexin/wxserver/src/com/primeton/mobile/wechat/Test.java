package com.primeton.mobile.wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.user.WechatUserInfo;


public class Test {
	
	public static void main(String[] args){
		try {
			AccessToken token = AccessToken.getToken("wxfad56e1d17a29e04", "d048ee6acfb69cbd0fc3aaa591b1455b");
//			WechatUserInfo info = UserOperations.getUserInfo(token.getAccess_token(), "ooyKpxCrG8A_Y23vsFETNXH4AAj0");
//			
//			System.out.println(info);
//			getJSTicket(token);
			MediaOperations.getTemporaryMedia(token.getAccess_token(), "oCpwxLPSs2ACPUawkaMwM0XSIy_bx3jx77DgZZJtXN4PzBGX_bLTysoVbwTqccmV", "D:/my.jpg");
			
		} catch (WechatExceprion e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取微信JS接口调用签名
	 * 
	 * @param token
	 */
	public static void getJSTicket(AccessToken token){
		System.out.println("token="+token.getAccess_token());
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		params.add(new BasicNameValuePair("type", "jsapi"));
		
		//使用HttpClient去获取token
		String responseStr = HttpExecuter.executeGetAsString(url, params);
		JSONObject json = JSONObject.parseObject(responseStr);
		String jsApi = json.getString("ticket");
		System.out.println("js接口调用票据ticket="+jsApi);
		
		String nonce = RandomStringUtils.random(20,
						"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		System.out.println("随机数串nonce="+nonce);
		long timestamp = System.currentTimeMillis();
		timestamp = timestamp/1000;
		System.out.println("时间戳timestamp="+timestamp);
		
		//需要使用微信js的页面URL
		String pageUrl = "http://weixin.mobile.primeton.com/default/testFileUpload.jsp";
		
		String tempStr = "jsapi_ticket="+ jsApi +"&noncestr="+ nonce +"&timestamp="
				+ timestamp +"&url="+ pageUrl;
		System.out.println("tempStr="+tempStr);
		String sign = "";
		try {
			sign = DigestUtils.sha1Hex(tempStr.getBytes("UTF-8"));
			System.out.println("sign="+sign);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据素材ID从微信下载用户上传的图片
	 * 
	 * @param token 微信API接口调用票据
	 * @param arrIDs 字符串数组，素材ID列表
	 */
	public static void downloadWechatImg(AccessToken token, String arrIDs){
		JSONArray array = JSONArray.parseArray(arrIDs);
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		for(int i=0; i<array.size(); i++){
			String mediaID = array.getString(i);
			String savePath = mediaID+".jpg";
			try {
				ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
				queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
				JSONObject json = new JSONObject();
				json.put("media_id", mediaID);
				StringEntity reqEntity = new StringEntity(json.toJSONString(), ContentType.create("text/json", "UTF-8"));
		        byte[] datas = HttpExecuter.executePost(uri, queryStr, reqEntity);
		        OutputStream out = new FileOutputStream(new File(savePath));
	        	out.write(datas);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
