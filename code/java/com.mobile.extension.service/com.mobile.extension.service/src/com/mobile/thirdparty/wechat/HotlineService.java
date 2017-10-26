package com.mobile.thirdparty.wechat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.wechat.message.ArticleMessage.Article;
import com.mobile.thirdparty.wechat.model.HotlineAccount;
import com.mobile.thirdparty.wechat.model.WechatAccessToken;


/**
 * 客服消息管理接口
 * <strong><ul>
 * <li>通过客服接口向用户推送消息时，用户必须在48小时内曾与公众号进行过互动
 * <li>公众号需要在微信管理控制台启用【客服功能】
 * <li>非回调模式下用户发给公众号的消息会推送到客服管理控制台
 * <li>回调模式下用户发给公众号的消息不会推送到客服管理控制台
 * </ul></strong>
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class HotlineService {

	/**
	 * 创建客服账号
	 * @param accessToken
	 * @param account
	 * @return
	 */
	public boolean addHotlineAccount(WechatAccessToken token, HotlineAccount account) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/add";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		StringEntity requestEntity = new StringEntity(JSONObject.toJSONString(account), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else return false;
	}
	
	/**
	 * 修改客服账号
	 * @param accessToken
	 * @param account
	 * @return
	 */
	public boolean updateAccount(WechatAccessToken token, HotlineAccount account) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/update";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		StringEntity requestEntity = new StringEntity(JSONObject.toJSONString(account), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else return false;
	}
	
	/**
	 * 删除客服账号
	 * @param accessToken
	 * @param account
	 * @return
	 */
	public boolean deleteHotlineAccount(WechatAccessToken token, HotlineAccount account) throws IOException{
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/del";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		StringEntity requestEntity = new StringEntity(JSONObject.toJSONString(account), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else return false;
	}
	
	/**
	 * 设置客服账号头像
	 * @param accessToken
	 * @param kfAccount
	 * @param imgPath
	 * @return
	 */
	//ATTENTION 
	public boolean setAccountImage(WechatAccessToken token, String kfAccount, String imgPath) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		queryStr.add(new BasicNameValuePair("kf_account", kfAccount));
		
		
		HttpEntity fileEntity = new FileEntity(new File(imgPath), ContentType.create("image/png"));
		String result = HttpExecuter.executePostAsString(uri, queryStr, fileEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else return false;
	}
	
	/**
	 * 获取所有客服账号
	 * @param accessToken
	 * @return
	 * @throws IOException
	 */
	public HotlineAccount[] getAllServiceAccount(WechatAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONArray.parseArray(json.getString("kf_list"), HotlineAccount.class).toArray(new HotlineAccount[]{});
		}
		else return null;
	}
	
	
	/**
	 * 获取当前所有在线客服
	 * @param accessToken
	 * @return
	 * @throws IOException
	 */
	public HotlineAccount[] getOnlineServiceAccounts(WechatAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONArray.parseArray(json.getString("kf_online_list"), HotlineAccount.class).toArray(new HotlineAccount[]{});
		}
		else return null;
	}
	
	/**
	 * 给用户发送文本消息
	 * @param accessToken
	 * @param touser
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public boolean sendText(String accessToken, String touser, String content) {
		return sendTextUseAccount(accessToken, touser, content, null);
	}
	
	/**
	 * 通过指定客服给用户发送文本消息
	 * @param accessToken
	 * @param touser
	 * @param content
	 * @param kfID
	 * @return
	 * @throws IOException
	 */
	public boolean sendTextUseAccount(String accessToken, String touser, String content, String kfID) {
		JSONObject json = new JSONObject();
		json.put("content", content);
//		return sendMessage(accessToken, touser, "text", json, kfID);
		return false;
	}
	
	/**
	 * 给用户发送图片消息
	 * @param accessToken
	 * @param touser
	 * @param mediaID
	 * @return
	 * @throws IOException
	 */
	public static boolean sendImage(String accessToken, String touser, String mediaID) throws IOException{
		return sendImageUseAccount(accessToken, touser, mediaID, null);
	}
	
	/**
	 * 通过指定客服给用户发送图片消息
	 * @param accessToken
	 * @param touser
	 * @param mediaID
	 * @param kfID
	 * @return
	 * @throws IOException
	 */
	public static boolean sendImageUseAccount(String accessToken, String touser, String mediaID, String kfID) throws IOException{
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		return sendMessage(accessToken, touser, "image", json, kfID);
	}
	
	/**
	 * 给用户发送语音消息
	 * @param accessToken
	 * @param touser
	 * @param mediaID
	 * @return
	 * @throws IOException
	 */
	public static boolean sendVoice(String accessToken, String touser, String mediaID) throws IOException{
		return sendVoiceUseAccount(accessToken, touser, mediaID, null);
	}
	
	/**
	 * 通过指定客服给用户发送图片消息
	 * @param accessToken
	 * @param touser
	 * @param mediaID
	 * @param kfID
	 * @return
	 * @throws IOException
	 */
	public static boolean sendVoiceUseAccount(String accessToken, String touser, String mediaID, String kfID) throws IOException{
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		return sendMessage(accessToken, touser, "voice", json, kfID);
	}
	
	/**
	 * 给用户发送视频消息
	 * @param accessToken
	 * @param touser
	 * @param mediaID
	 * @param thumbMediaID
	 * @param title
	 * @param description
	 * @return
	 * @throws IOException
	 */
	public static boolean sendVideo(String accessToken, String touser, String mediaID, String thumbMediaID, 
			String title, String description) throws IOException{
		return sendVideoUseAccount(accessToken, touser, mediaID, thumbMediaID, title, description, null);
	}
	
	/**
	 * 通过指定客服给用户发送视频消息
	 * @param accessToken
	 * @param touser
	 * @param mediaID
	 * @param thumbMediaID
	 * @param title
	 * @param description
	 * @param kfID
	 * @return
	 * @throws IOException
	 */
	public static boolean sendVideoUseAccount(String accessToken, String touser, String mediaID, String thumbMediaID, 
			String title, String description, String kfID) throws IOException{
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		json.put("thumb_media_id", thumbMediaID);
		json.put("title", title);
		json.put("description", description);
		return sendMessage(accessToken, touser, "video", json, kfID);
	}
	
	/**
	 * 给用户发送音乐消息
	 * @param accessToken
	 * @param touser
	 * @param title
	 * @param description
	 * @param musicUrl
	 * @param hqUrl
	 * @param thumbMediaID
	 * @return
	 * @throws IOException
	 */
	public static boolean sendMusic(String accessToken, String touser, String title, String description, 
			String musicUrl, String hqUrl, String thumbMediaID) throws IOException{
		return sendMusicUseAccount(accessToken, touser, title, description, musicUrl,
				hqUrl, thumbMediaID, null);
	}
	
	/**
	 * 通过指定客服给用户发送音乐消息
	 * @param accessToken
	 * @param touser
	 * @param title
	 * @param description
	 * @param musicUrl
	 * @param hqUrl
	 * @param thumbMediaID
	 * @param kfID
	 * @return
	 * @throws IOException
	 */
	public static boolean sendMusicUseAccount(String accessToken, String touser, String title, String description, 
			String musicUrl, String hqUrl, String thumbMediaID, String kfID) throws IOException{
		JSONObject json = new JSONObject();
		json.put("title", title);
		json.put("description", description);
		json.put("musicurl", musicUrl);
		json.put("hqmusicurl", hqUrl);
		json.put("thumb_media_id", thumbMediaID);
		return sendMessage(accessToken, touser, "music", json, kfID);
	}
	
	/**
	 * 给用户发送图文消息
	 * @param accessToken
	 * @param touser
	 * @param articles
	 * @return
	 * @throws IOException
	 */
	public static boolean sendNews(String accessToken, String touser, Article[] articles) throws IOException{
		return sendNewsUseAccount(accessToken, touser, articles, null);
	}
	
	/**
	 * 通过指定客服给用户发送图文消息
	 * @param accessToken
	 * @param touser
	 * @param articles
	 * @param kfID
	 * @return
	 * @throws IOException
	 */
	public static boolean sendNewsUseAccount(String accessToken, String touser, Article[] articles, String kfID) throws IOException{
		JSONObject json = new JSONObject();
		JSONArray arts = new JSONArray();
		arts.addAll(Arrays.asList(articles));
		json.put("articles", arts);
		return sendMessage(accessToken, touser, "news", json, kfID);
	}
	
	private static boolean sendMessage(String accessToken, String touser, String type, JSONObject data, String kfID) throws IOException{
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("touser", touser);
		postData.put("msgtype", type);
		if(data != null)
			postData.put(type, data);
		if(StringUtils.isNotBlank(kfID)){
			JSONObject kfAccount = new JSONObject();
			kfAccount.put("kf_account", kfID);
			postData.put("customservice", kfAccount);
		}
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else return false;

	}
}
