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

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
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
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatQRCodeModel createTempQRImage(String accessToken, long expireSeconds, int qrID) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("expire_secondes", expireSeconds);
		jsonObj.put("action_name", WechatQRCodeModel.QR_IMAGE_TEMPORARY);
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
		}
		else throw new ThirdPartyRequestExceprion("[AccountOperations#createTempQRImage]"+result);
	}
	
	/**
	 * 创建永久二维码Ticket
	 * @param accessToken
	 * @param qrID二维码标识
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatQRCodeModel createQRImage(String accessToken, int qrID) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
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
		}
		else throw new ThirdPartyRequestExceprion("[AccountOperations#createQRImage]"+result);
	}
	
	/**
	 * 创建永久字符串二维码Ticket
	 * @param accessToken
	 * @param qrID 二维码标识
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatQRCodeModel createQRString(String accessToken, String qrID) throws JSONException, IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action_name", WechatQRCodeModel.QR_STR_FOREVER);
		JSONObject obj = new JSONObject();
		obj.put("scene_str", qrID);
		JSONObject obj1 = new JSONObject();
		obj1.put("scene", obj);
		jsonObj.put("action_info", obj1);
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatQRCodeModel.class);
		}
		else throw new ThirdPartyRequestExceprion("[AccountOperations#createQRString]"+result);
	}
	
	/**
	 * 通过Ticket换取二维码
	 * @param ticket
	 * @return
	 */
	public static void getQRImageByTicket(String ticket, String savePath) {
		String uri = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
		FileOutputStream out = null;
		try {
			//ticket = URLEncoder.encode(ticket, IOperationsConstants.DEFAULT_CHARSET);
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
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static String connection2Short(String accessToken, String longUrl) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/shorturl";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("long_url", longUrl);
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result).getString("short_url");
		}
		else throw new ThirdPartyRequestExceprion("[AccountOperations#connection2Short]"+result);
	}
	
}
