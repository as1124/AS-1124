package com.as1124.api.wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.as1124.api.access.AbstractAccessToken;
import com.as1124.api.access.HttpExecuter;
import com.as1124.api.access.exception.ThirdPartyRequestExceprion;
import com.as1124.api.wechat.model.qrcode.WechatQRCodeModel;

/**
 * WeChat 账号操作接口.
 * <p>为了满足用户渠道推广分析和用户帐号绑定等场景的需要，公众平台提供了生成带参数二维码的接口。
 * 使用该接口可以获得多个带不同场景值的二维码，用户扫描后，公众号可以接收到事件推送。所包含的接口只有认证的服务号有权调用<br>
 * 目前有2种类型的二维码：
 * <ol>
 * <li>临时二维码，是有过期时间的，最长可以设置为在二维码生成后的30天（即2592000秒）后过期，但能够生成较多数量。临时二维码主要用于帐号绑定等不要求二维码永久保存的业务场景
 * <li>永久二维码，是无过期时间的，但数量较少（目前为最多10万个）。永久二维码主要用于适用于帐号绑定、用户来源统计等场景
 * </ol>
 * 
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public class AccountOperations {

	private static final String QR_CODE_URI = "https://api.weixin.qq.com/cgi-bin/qrcode/create";

	private AccountOperations() {
	}

	/**
	 * 创建临时二维码Ticket
	 * @param token
	 * @param expireSeconds 有效时间，单位：秒
	 * @param sceneStr 二维码标识
	 * @return
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatQRCodeModel createTempQRImage(AbstractAccessToken token, long expireSeconds, String sceneStr)
			throws ThirdPartyRequestExceprion {
		WechatQRCodeModel qrCodeModel = new WechatQRCodeModel();

		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("expire_secondes", expireSeconds);
		jsonObj.put("action_name", WechatQRCodeModel.QR_IMAGE_TEMPORARY);
		JSONObject obj = new JSONObject();
		obj.put("scene_str", sceneStr);
		JSONObject obj1 = new JSONObject();
		obj1.put("scene", obj);
		jsonObj.put("action_info", obj1);
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(QR_CODE_URI, queryStr, requestEntity);
		JSONObject resultJSON = JSONObject.parseObject(result);
		if (resultJSON.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			qrCodeModel.setScene_str(sceneStr);
			qrCodeModel.setAction_name(WechatQRCodeModel.QR_IMAGE_TEMPORARY);
			qrCodeModel.setExpire_secondes(resultJSON.getIntValue("expire_seconds"));
			qrCodeModel.setTicket(resultJSON.getString("ticket"));
			qrCodeModel.setUrl(resultJSON.getString("url"));
			return qrCodeModel;
		} else
			throw new ThirdPartyRequestExceprion("[AccountOperations#createTempQRImage]" + result);
	}

	/**
	 * 创建永久二维码Ticket
	 * @param token
	 * @param sceneID 二维码标识
	 * @return
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatQRCodeModel createQRImage(AbstractAccessToken token, int sceneID)
			throws ThirdPartyRequestExceprion {
		WechatQRCodeModel qrCodeModel = new WechatQRCodeModel();

		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action_name", WechatQRCodeModel.QR_IMAGE_FOREVER);
		JSONObject obj = new JSONObject();
		obj.put("scene_id", sceneID);
		JSONObject obj1 = new JSONObject();
		obj1.put("scene", obj);
		jsonObj.put("action_info", obj1);
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(QR_CODE_URI, queryStr, requestEntity);
		JSONObject resultJSON = JSONObject.parseObject(result);
		if (resultJSON.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			qrCodeModel.setScene_id(sceneID);
			qrCodeModel.setAction_name(WechatQRCodeModel.QR_IMAGE_FOREVER);
			qrCodeModel.setExpire_secondes(resultJSON.getIntValue("expire_seconds"));
			qrCodeModel.setTicket(resultJSON.getString("ticket"));
			qrCodeModel.setUrl(resultJSON.getString("url"));
			return qrCodeModel;
		} else
			throw new ThirdPartyRequestExceprion("[AccountOperations#createQRImage]" + result);
	}

	/**
	 * 创建永久字符串二维码Ticket
	 * @param token
	 * @param sceneStr 二维码标识
	 * @return
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatQRCodeModel createQRString(AbstractAccessToken token, String sceneStr)
			throws ThirdPartyRequestExceprion {
		WechatQRCodeModel qrCodeModel = new WechatQRCodeModel();

		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action_name", WechatQRCodeModel.QR_IMAGE_STR_FOREVER);
		JSONObject obj = new JSONObject();
		obj.put("scene_str", sceneStr);
		JSONObject obj1 = new JSONObject();
		obj1.put("scene", obj);
		jsonObj.put("action_info", obj1);
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(QR_CODE_URI, queryStr, requestEntity);
		JSONObject resultJSON = JSONObject.parseObject(result);
		if (resultJSON.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			qrCodeModel.setScene_str(sceneStr);
			qrCodeModel.setAction_name(WechatQRCodeModel.QR_IMAGE_STR_FOREVER);
			qrCodeModel.setExpire_secondes(resultJSON.getIntValue("expire_seconds"));
			qrCodeModel.setTicket(resultJSON.getString("ticket"));
			qrCodeModel.setUrl(resultJSON.getString("url"));
			return qrCodeModel;
		} else
			throw new ThirdPartyRequestExceprion("[AccountOperations#createQRString]" + result);
	}

	/**
	 * 通过Ticket换取二维码
	 * @param ticket
	 * @param savePath 
	 * @return
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static void getQRImageByTicket(String ticket, String savePath) throws ThirdPartyRequestExceprion {
		String uri = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
		FileOutputStream out = null;
		try {
			// ticket = URLEncoder.encode(ticket, IOperationsConstants.DEFAULT_CHARSET);
			List<NameValuePair> queryStr = new ArrayList<>();
			queryStr.add(new BasicNameValuePair("ticket", ticket));
			byte[] datas = HttpExecuter.executeGet(uri, queryStr);
			File file = new File(savePath);
			out = new FileOutputStream(file);
			out.write(datas);
		} catch (IOException e) {
			ThirdPartyRequestExceprion ex = new ThirdPartyRequestExceprion(
					"[AccountOperations#createQRString]" + e.getMessage());
			ex.initCause(e);
			throw ex;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				// do not care
			}
		}
	}

	/**
	 * 将长链接转成短链接
	 * @param token
	 * @param longUrl 长链接URL
	 * @return short_url
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static String connection2Short(AbstractAccessToken token, String longUrl) throws ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/shorturl";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("long_url", longUrl);
		jsonObj.put("action", "long2short");
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject resultJSON = JSONObject.parseObject(result);
		if (resultJSON.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return resultJSON.getString("short_url");
		} else {
			throw new ThirdPartyRequestExceprion("[AccountOperations#connection2Short]" + result);
		}
	}

}
