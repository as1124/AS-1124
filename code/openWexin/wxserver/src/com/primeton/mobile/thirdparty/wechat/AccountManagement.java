package com.primeton.mobile.thirdparty.wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.thirdparty.access.AbstractAccessToken;
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.wechat.model.qrcode.WechatQRCodeModel;

/**
 * WeChat 账户操作接口.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class AccountManagement {

	/**
	 * 创建临时二维码Ticket
	 * @param accessToken
	 * @param expireSeconds 有效时间，单位：秒
	 * @param qrID 二维码标识
	 * @return
	 */
	public WechatQRCodeModel createTempQRImage(AbstractAccessToken token, long expireSeconds, int qrID){
		String uri = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("expire_secondes", expireSeconds);
		jsonObj.put("action_name", WechatQRCodeModel.QR_IMAGE_TEMPORARY);
		JSONObject obj = new JSONObject();
		obj.put("scene_id", qrID);
		JSONObject obj1 = new JSONObject();
		obj1.put("scene", obj);
		jsonObj.put("action_info", obj1);
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatQRCodeModel.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 创建永久二维码Ticket
	 * @param accessToken
	 * @param qrID二维码标识
	 * @return
	 */
	public WechatQRCodeModel createQRImage(AbstractAccessToken token, int qrID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action_name", WechatQRCodeModel.QR_IMAGE_FOREVER);
		JSONObject obj = new JSONObject();
		obj.put("scene_id", qrID);
		JSONObject obj1 = new JSONObject();
		obj1.put("scene", obj);
		jsonObj.put("action_info", obj1);
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatQRCodeModel.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 创建永久字符串二维码Ticket
	 * @param accessToken
	 * @param sceneStr 场景值ID
	 * @return
	 */
	public WechatQRCodeModel createQRString(String accessToken, String sceneStr) {
		String uri = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action_name", WechatQRCodeModel.QR_STR_FOREVER);
		JSONObject obj = new JSONObject();
		obj.put("scene_str", sceneStr);
		JSONObject obj1 = new JSONObject();
		obj1.put("scene", obj);
		jsonObj.put("action_info", obj1);
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatQRCodeModel.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 通过Ticket换取二维码
	 * @param ticket
	 * @return
	 */
	public void getQRImageByTicket(String ticket, String savePath) {
		String uri = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
		FileOutputStream out = null;
		try {
			ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
			queryStr.add(new BasicNameValuePair("ticket", ticket));
			byte[] datas = HttpExecuter.executeGet(uri, queryStr);
			File file =new File(savePath);
			out = new FileOutputStream(file);
			out.write(datas);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(out != null)
					out.close();
			} catch (Exception e2) {
				
			}
		}
	}
	
	/**
	 * 将长链接转成短链接
	 * @param accessToken
	 * @param longUrl 长链接URL
	 * @return short_url
	 */
	public String connection2Short(AbstractAccessToken token, String longUrl) {
		String uri = "https://api.weixin.qq.com/cgi-bin/shorturl";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action", "long2short");
		jsonObj.put("long_url", longUrl);
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result).getString("short_url");
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
}
