package com.primeton.mobile.thirdparty.wechat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.thirdparty.access.AbstractAccessToken;
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.wechat.message.ArticleMessage.Article;
import com.primeton.mobile.thirdparty.wechat.model.HotlineAccount;

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
public class HotlineOperations {

	/**
	 * 创建客服账号
	 * @param token
	 * @param account
	 * @return
	 */
	public boolean addHotlineAccount(AbstractAccessToken token, HotlineAccount account) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/add";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		StringEntity requestEntity = new StringEntity(JSONObject.toJSONString(account), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 邀请微信用户绑定客服账号
	 * @param token
	 * @param kfAccount
	 * @param wxAccount
	 * @return
	 */
	public boolean invite4Binding(AbstractAccessToken token, String kfAccount, String wxAccount){
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/inviteworker";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		postData.put("kf_account", kfAccount);
		postData.put("invite_wx", wxAccount);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 修改客服账号
	 * @param token
	 * @param account
	 * @return
	 */
	public boolean updateHotlineAccount(AbstractAccessToken token, HotlineAccount account) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/update";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		StringEntity requestEntity = new StringEntity(JSONObject.toJSONString(account), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 删除客服账号
	 * @param token
	 * @param account
	 * @return
	 */
	public boolean deleteHotlineAccount(AbstractAccessToken token, HotlineAccount account) throws IOException{
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/del";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		StringEntity requestEntity = new StringEntity(JSONObject.toJSONString(account), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 设置客服账号头像
	 * @param token
	 * @param kfAccount 完整客服账号{@link HotlineAccount#getKf_account()}
	 * @param imgPath
	 * @return
	 */
	public boolean setAccountImage(AbstractAccessToken token, String kfAccount, String imgPath) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		queryStr.add(new BasicNameValuePair("kf_account", kfAccount));
		String url = uri + "?" + URLEncodedUtils.format(queryStr, HttpExecuter.getCharset(null));
		
		String result = HttpExecuter.executeUploadFile(null, url, imgPath, "media");
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 获取所有客服账号
	 * @param token
	 * @return
	 * @throws IOException
	 */
	public List<HotlineAccount> getAllServiceAccount(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONArray.parseArray(json.getString("kf_list"), HotlineAccount.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	
	/**
	 * 获取当前所有在线客服
	 * @param token
	 * @return
	 */
	public List<HotlineAccount> getOnlineServiceAccounts(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONArray.parseArray(json.getString("kf_online_list"), HotlineAccount.class);
		}
		else return null;
	}
	
	/**
	 * 给用户发送文本消息
	 * @param token
	 * @param touser
	 * @param content
	 * @return
	 */
	public boolean sendText(AbstractAccessToken token, String touser, String content) {
		return sendTextUseAccount(token, touser, content, null);
	}
	
	/**
	 * 通过指定客服给用户发送文本消息
	 * @param token
	 * @param touser
	 * @param content
	 * @param kfID
	 * @return
	 */
	public boolean sendTextUseAccount(AbstractAccessToken token, String touser, String content, String kfID) {
		JSONObject json = new JSONObject();
		json.put("content", content);
		return sendMessage(token, touser, "text", json, kfID);
	}
	
	/**
	 * 给用户发送图片消息
	 * {@link MediaOperations}
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口上传获取到的素材的<code>media_id</code>
	 * @return
	 */
	public boolean sendImage(AbstractAccessToken token, String touser, String mediaID) {
		return sendImageUseAccount(token, touser, mediaID, null);
	}
	
	/**
	 * 通过指定客服给用户发送图片消息
	 * {@link MediaOperations}
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口上传获取到的素材的<code>media_id</code>
	 * @param kfID
	 * @return
	 */
	public boolean sendImageUseAccount(AbstractAccessToken token, String touser, String mediaID, String kfID) {
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		return sendMessage(token, touser, "image", json, kfID);
	}
	
	/**
	 * 给用户发送语音消息
	 * {@link MediaOperations}
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口上传获取到的素材的<code>media_id</code>
	 * @return
	 */
	public boolean sendVoice(AbstractAccessToken token, String touser, String mediaID) {
		return sendVoiceUseAccount(token, touser, mediaID, null);
	}
	
	/**
	 * 通过指定客服给用户发送语音消息
	 * {@link MediaOperations}
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口上传获取到的素材的<code>media_id</code>
	 * @param kfID
	 * @return
	 */
	public boolean sendVoiceUseAccount(AbstractAccessToken token, String touser, String mediaID, String kfID) {
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		return sendMessage(token, touser, "voice", json, kfID);
	}
	
	/**
	 * 给用户发送视频消息
	 * {@link MediaOperations}
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口上传获取到的素材的<code>media_id</code>
	 * @param thumbMediaID 缩略图的媒体ID，通过素材接口获取
	 * @param title 标题
	 * @param description 描述信息
	 * @return
	 */
	public boolean sendVideo(AbstractAccessToken token, String touser, String mediaID, String thumbMediaID, 
			String title, String description) {
		return sendVideoUseAccount(token, touser, mediaID, thumbMediaID, title, description, null);
	}
	
	/**
	 * 通过指定客服给用户发送视频消息
	 * {@link MediaOperations}
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口上传获取到的素材的<code>media_id</code>
	 * @param thumbMediaID 缩略图的媒体ID，通过素材接口获取
	 * @param title 标题
	 * @param description 描述信息
	 * @param kfID
	 * @return
	 */
	public boolean sendVideoUseAccount(AbstractAccessToken token, String touser, String mediaID, String thumbMediaID, 
			String title, String description, String kfID) {
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		json.put("thumb_media_id", thumbMediaID);
		json.put("title", title);
		json.put("description", description);
		return sendMessage(token, touser, "video", json, kfID);
	}
	
	/**
	 * 给用户发送音乐消息
	 * @param token
	 * @param touser
	 * @param title 标题
	 * @param description 描述信息
	 * @param musicUrl 音乐链接
	 * @param hqUrl 高品质音乐链接
	 * @param thumbMediaID 缩略图的媒体ID，通过素材接口获取
	 * @return
	 */
	public boolean sendMusic(AbstractAccessToken token, String touser, String title, String description, 
			String musicUrl, String hqUrl, String thumbMediaID) {
		return sendMusicUseAccount(token, touser, title, description, musicUrl,
				hqUrl, thumbMediaID, null);
	}
	
	/**
	 * 通过指定客服给用户发送音乐消息
	 * @param token
	 * @param touser
	 * @param title 标题
	 * @param description 描述信息
	 * @param musicUrl 音乐链接
	 * @param hqUrl 高品质音乐链接
	 * @param thumbMediaID 缩略图的媒体ID，通过素材接口获取
	 * @param kfID
	 * @return
	 */
	public boolean sendMusicUseAccount(AbstractAccessToken token, String touser, String title, String description, 
			String musicUrl, String hqUrl, String thumbMediaID, String kfID) {
		JSONObject json = new JSONObject();
		json.put("title", title);
		json.put("description", description);
		json.put("musicurl", musicUrl);
		json.put("hqmusicurl", hqUrl);
		json.put("thumb_media_id", thumbMediaID);
		return sendMessage(token, touser, "music", json, kfID);
	}
	
	/**
	 * 给用户发送图文消息（点击跳转到外链） 
	 * @param token
	 * @param touser
	 * @param articles
	 * @return
	 */
	public boolean sendNews(AbstractAccessToken token, String touser, Article[] articles) {
		return sendNewsUseAccount(token, touser, articles, null);
	}
	
	/**
	 * 通过指定客服给用户发送图文消息（点击跳转到外链） 
	 * @param accessToken
	 * @param touser
	 * @param articles
	 * @param kfID
	 * @return
	 */
	public boolean sendNewsUseAccount(AbstractAccessToken token, String touser, Article[] articles, String kfID) {
		JSONObject json = new JSONObject();
		JSONArray arts = new JSONArray();
		arts.addAll(Arrays.asList(articles));
		json.put("articles", arts);
		return sendMessage(token, touser, "news", json, kfID);
	}
	
	/**
	 * 发送微信图文消息 （图文消息条数限制在8条以内）
	 * {@link MediaOperations}
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口获取的图文素材media_id
	 * @return
	 */
	public boolean sendMPNews(AbstractAccessToken token, String touser, String mediaID){
		return sendMPNewsUseAccount(token, touser, mediaID, null);
	}
	
	/**
	 * 通过指定客服发送微信图文消息 （图文消息条数限制在8条以内）
	 * {@link MediaOperations}
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口获取的图文素材media_id
	 * @param kfID
	 * @return
	 */
	public boolean sendMPNewsUseAccount(AbstractAccessToken token, String touser, String mediaID, String kfID){
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		return sendMessage(token, touser, "mpnews", json, kfID);
	}
	
	/**
	 * 发送微信卡券消息
	 * @param token
	 * @param touser
	 * @param cardID 卡券ID
	 * @return
	 */
	public boolean sendWXCard(AbstractAccessToken token, String touser, String cardID){
		return sendWXCard(token, touser, cardID, null);
	}
	
	/**
	 * 通过指定客服下发卡券消息
	 * @param token
	 * @param touser
	 * @param cardID 
	 * @param kfID
	 * @return
	 */
	public boolean sendWXCard(AbstractAccessToken token, String touser, String cardID, String kfID){
		JSONObject json = new JSONObject();
		json.put("card_id", cardID);
		return sendMessage(token, touser, "wxcard", json, kfID);
	}
	
	private boolean sendMessage(AbstractAccessToken token, String touser, String type, 
			JSONObject data, String kfID){
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
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
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else{
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
}
