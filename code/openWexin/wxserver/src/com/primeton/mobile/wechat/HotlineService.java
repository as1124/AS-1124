package com.primeton.mobile.wechat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.wechat.model.HotlineAccount;
import com.primeton.mobile.wechat.model.message.ArticleMessage.Article;


/**
 * 客服管理接口。
 * <strong>通过客服接口向用户推送消息时，用户必须在48小时内曾与公众号进行过互动</strong>
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
	 * @throws IOException
	 */
	public static boolean createHotlineAccount(String accessToken, HotlineAccount account) throws IOException{
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/add";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		RequestEntity requestEntity = new StringRequestEntity(JSONObject.toJSONString(account), IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET);
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
	 * @throws IOException
	 */
	public static boolean updateAccount(String accessToken, HotlineAccount account) throws IOException{
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/update";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		RequestEntity requestEntity = new StringRequestEntity(JSONObject.toJSONString(account), IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET);
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
	 * @throws IOException
	 */
	public static boolean deleteHotlineAccount(String accessToken, HotlineAccount account) throws IOException{
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/del";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		RequestEntity requestEntity = new StringRequestEntity(JSONObject.toJSONString(account), 
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET);
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
	 * @throws IOException
	 */
	public static boolean setAccountImage(String accessToken, String kfAccount, String imgPath) throws IOException{
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg";
		NameValuePair[] queryStr = new NameValuePair[2];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		queryStr[1] = new NameValuePair("kf_account", kfAccount);
		Part[] parts = new Part[1];
		File image = new File(imgPath);
		parts[0] = new FilePart(image.getName(), image);
		String result = HttpExecuter.executePostAsString(uri, queryStr, parts);
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
	public static HotlineAccount[] getAllServiceAccount(String accessToken) throws IOException{
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/getkflist";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONArray.parseArray(result, HotlineAccount.class).toArray(new HotlineAccount[]{});
		}
		else return null;
	}
	
	
	/**
	 * 获取当前所有在线客服
	 * @param accessToken
	 * @return
	 * @throws IOException
	 */
	public static HotlineAccount[] getOnlineServiceAccounts(String accessToken) throws IOException{
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/getonlinekflist";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONArray.parseArray(result, HotlineAccount.class).toArray(new HotlineAccount[]{});
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
	public static boolean sendText(String accessToken, String touser, String content) throws IOException{
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
	public static boolean sendTextUseAccount(String accessToken, String touser, String content, String kfID) throws IOException{
		JSONObject json = new JSONObject();
		json.put("content", content);
		return sendMessage(accessToken, touser, "text", json, kfID);
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
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
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
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString(), IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else return false;

	}
}
