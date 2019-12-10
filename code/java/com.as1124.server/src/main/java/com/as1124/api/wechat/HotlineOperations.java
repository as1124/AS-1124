package com.as1124.api.wechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.as1124.api.access.AbstractAccessToken;
import com.as1124.api.access.HttpExecuter;
import com.as1124.api.wechat.model.hotline.HotlineAccount;
import com.as1124.api.wechat.model.message.ArticleMessage.Article;
import com.as1124.common.util.LoggerUtil;

/**
 * 客服消息管理接口
 * <strong><ul>
 * <li>通过客服接口向用户推送消息时，用户必须在48小时内曾与公众号进行过互动
 * <li>公众号需要在微信管理控制台启用【客服功能】
 * <li>非回调模式下用户发给公众号的消息会推送到客服管理控制台
 * <li>回调模式下用户发给公众号的消息不会推送到客服管理控制台
 * </ul></strong>
 *
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public class HotlineOperations {

	static Logger logger = LoggerUtil.getLogger(HotlineOperations.class);

	private HotlineOperations() {
	}

	/**
	 * 创建客服账号
	 * 
	 * @param token
	 * @param account
	 * @return
	 */
	public static boolean addHotlineAccount(AbstractAccessToken token, HotlineAccount account) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/add";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		StringEntity requestEntity = new StringEntity(JSONObject.toJSONString(account),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 邀请微信用户绑定客服账号
	 * 
	 * @param token
	 * @param kfAccount
	 * @param wxAccount
	 * @return
	 */
	public static boolean invite4Binding(AbstractAccessToken token, String kfAccount, String wxAccount) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/inviteworker";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("kf_account", kfAccount);
		postData.put("invite_wx", wxAccount);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 修改客服账号
	 * 
	 * @param token
	 * @param account
	 * @return
	 */
	public static boolean updateHotlineAccount(AbstractAccessToken token, HotlineAccount account) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/update";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		StringEntity requestEntity = new StringEntity(JSONObject.toJSONString(account),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 删除客服账号
	 * 
	 * @param token
	 * @param account
	 * @return
	 */
	public static boolean deleteHotlineAccount(AbstractAccessToken token, HotlineAccount account) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/del";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		queryStr.add(new BasicNameValuePair("kf_account", account.getKf_account()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
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
	public static boolean setAccountImage(AbstractAccessToken token, String kfAccount, String imgPath) {
		String uri = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		queryStr.add(new BasicNameValuePair("kf_account", kfAccount));
		String url = uri + "?" + URLEncodedUtils.format(queryStr, WechatConstants.CHARSET_UTF8);

		String result = HttpExecuter.executeUploadFile(null, url, imgPath, "media");
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 获取所有客服账号
	 * 
	 * @param token
	 * @return
	 */
	public static List<HotlineAccount> getAllServiceAccount(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
		int returnCode = json.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONArray.parseArray(json.getString("kf_list"), HotlineAccount.class);
		} else {
			logger.log(Level.SEVERE, result);
			return Collections.emptyList();
		}
	}

	/**
	 * 获取当前所有在线客服
	 * 
	 * @param token
	 * @return
	 */
	public static List<HotlineAccount> getOnlineServiceAccounts(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
		int returnCode = json.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONArray.parseArray(json.getString("kf_online_list"), HotlineAccount.class);
		} else {
			logger.log(Level.SEVERE, result);
			return Collections.emptyList();
		}
	}

	/**
	 * 此接口在客服和用户之间创建一个会话，如果该客服和用户会话已存在，
	 * 则直接返回0。指定的客服帐号必须已经绑定微信号且在线。
	 * @param token
	 * @param kfAccount
	 * @param openID
	 */
	public static boolean openConversation(AbstractAccessToken token, String kfAccount, String openID) {
		String uri = "https://api.weixin.qq.com/customservice/kfsession/create";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));

		JSONObject postData = new JSONObject();
		postData.put("kf_account", kfAccount);
		postData.put("openid", openID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
		int returnCode = json.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 关闭客服和粉丝之间的会话
	 * 
	 * @param token
	 * @param kfAccount
	 * @param openID
	 * @return
	 */
	public static boolean closeConversation(AbstractAccessToken token, String kfAccount, String openID) {
		String uri = "https://api.weixin.qq.com/customservice/kfsession/close";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));

		JSONObject postData = new JSONObject();
		postData.put("kf_account", kfAccount);
		postData.put("openid", openID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
		int returnCode = json.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 返回当前为指定粉丝服务的客服ID，没有则为空（会话未创建）
	 * @param token
	 * @param openID
	 * @return
	 */
	public static String getConversationStatus(AbstractAccessToken token, String openID) {
		String uri = "https://api.weixin.qq.com/customservice/kfsession/getsession";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		queryStr.add(new BasicNameValuePair("openid", openID));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
		int returnCode = json.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return json.getString("kf_account");
		} else {
			logger.log(Level.SEVERE, result);
			return "";
		}
	}

	/**
	 * 获取指定客服当前处理的会话列表
	 * 
	 * @param token
	 * @param kfAccount
	 * @return
	 */
	public static List<String> getConversationList(AbstractAccessToken token, String kfAccount) {
		String uri = "https://api.weixin.qq.com/customservice/kfsession/getsession";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		queryStr.add(new BasicNameValuePair("kf_account", kfAccount));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
		int returnCode = json.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			List<String> openids = new ArrayList<>();
			JSONArray array = json.getJSONArray("sessionlist");
			for (int i = 0; i < array.size(); i++)
				openids.add(array.getJSONObject(i).getString("openid"));
			return openids;
		} else {
			logger.log(Level.SEVERE, result);
			return Collections.emptyList();
		}
	}

	/**
	 * 获取等待客服接入的粉丝
	 * 
	 * @param token
	 * @return
	 */
	// ATTENTION 未完成
	public static List<String> getWaitFans(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/customservice/kfsession/getwaitcase";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
		int returnCode = json.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			List<String> openids = new ArrayList<>();
			JSONArray array = json.getJSONArray("waitcaselist");
			for (int i = 0; i < array.size(); i++)
				openids.add(array.getJSONObject(i).getString("openid"));
			return openids;
		} else {
			logger.log(Level.SEVERE, result);
			return Collections.emptyList();
		}
	}

	/**
	 * 给用户发送文本消息
	 * @param token
	 * @param touser
	 * @param content
	 * @return
	 */
	public static boolean sendText(AbstractAccessToken token, String touser, String content) {
		return sendTextUseAccount(token, touser, content, null);
	}

	/**
	 * 通过指定客服给用户发送文本消息
	 * 
	 * @param token
	 * @param touser
	 * @param content
	 * @param kfID
	 * @return
	 */
	public static boolean sendTextUseAccount(AbstractAccessToken token, String touser, String content, String kfID) {
		JSONObject json = new JSONObject();
		json.put("content", content);
		return sendMessage(token, touser, "text", json, kfID);
	}

	/**
	 * 给用户发送图片消息
	 * 
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口 {@link MediaOperations}上传获取到的素材的<code>media_id</code>
	 * @return
	 */
	public static boolean sendImage(AbstractAccessToken token, String touser, String mediaID) {
		return sendImageUseAccount(token, touser, mediaID, null);
	}

	/**
	 * 通过指定客服给用户发送图片消息
	 * 
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口 {@link MediaOperations}上传获取到的素材的<code>media_id</code>
	 * @param kfID
	 * @return
	 */
	public static boolean sendImageUseAccount(AbstractAccessToken token, String touser, String mediaID, String kfID) {
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		return sendMessage(token, touser, "image", json, kfID);
	}

	/**
	 * 给用户发送语音消息 {@link MediaOperations}
	 * 
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口上传获取到的素材的<code>media_id</code>
	 * @return
	 */
	public static boolean sendVoice(AbstractAccessToken token, String touser, String mediaID) {
		return sendVoiceUseAccount(token, touser, mediaID, null);
	}

	/**
	 * 通过指定客服给用户发送语音消息 {@link MediaOperations}
	 * 
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口上传获取到的素材的<code>media_id</code>
	 * @param kfID
	 * @return
	 */
	public static boolean sendVoiceUseAccount(AbstractAccessToken token, String touser, String mediaID, String kfID) {
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		return sendMessage(token, touser, "voice", json, kfID);
	}

	/**
	 * 给用户发送视频消息 {@link MediaOperations}
	 * 
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口上传获取到的素材的<code>media_id</code>
	 * @param thumbMediaID 缩略图的媒体ID，通过素材接口获取
	 * @param title 标题
	 * @param description 描述信息
	 * @return
	 */
	public static boolean sendVideo(AbstractAccessToken token, String touser, String mediaID, String thumbMediaID,
			String title, String description) {
		return sendVideoUseAccount(token, touser, mediaID, thumbMediaID, title, description, null);
	}

	/**
	 * 通过指定客服给用户发送视频消息 {@link MediaOperations}
	 * 
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口上传获取到的素材的<code>media_id</code>
	 * @param thumbMediaID 缩略图的媒体ID，通过素材接口获取
	 * @param title 标题
	 * @param description 描述信息
	 * @param kfID
	 * @return
	 */
	public static boolean sendVideoUseAccount(AbstractAccessToken token, String touser, String mediaID,
			String thumbMediaID, String title, String description, String kfID) {
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		json.put("thumb_media_id", thumbMediaID);
		json.put("title", title);
		json.put("description", description);
		return sendMessage(token, touser, "video", json, kfID);
	}

	/**
	 * 给用户发送音乐消息
	 * 
	 * @param token
	 * @param touser
	 * @param title 标题
	 * @param description 描述信息
	 * @param musicUrl 音乐链接
	 * @param hqUrl 高品质音乐链接
	 * @param thumbMediaID 缩略图的媒体ID，通过素材接口获取
	 * @return
	 */
	public static boolean sendMusic(AbstractAccessToken token, String touser, String title, String description,
			String musicUrl, String hqUrl, String thumbMediaID) {
		return sendMusicUseAccount(token, touser, title, description, musicUrl, hqUrl, thumbMediaID, null);
	}

	/**
	 * 通过指定客服给用户发送音乐消息
	 * 
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
	public static boolean sendMusicUseAccount(AbstractAccessToken token, String touser, String title,
			String description, String musicUrl, String hqUrl, String thumbMediaID, String kfID) {
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
	 * 
	 * @param token
	 * @param touser
	 * @param articles
	 * @return
	 */
	public static boolean sendNews(AbstractAccessToken token, String touser, Article[] articles) {
		return sendNewsUseAccount(token, touser, articles, null);
	}

	/**
	 * 通过指定客服给用户发送图文消息（点击跳转到外链） 
	 * 
	 * @param token
	 * @param touser
	 * @param articles
	 * @param kfID
	 * @return
	 */
	public static boolean sendNewsUseAccount(AbstractAccessToken token, String touser, Article[] articles,
			String kfID) {
		JSONObject json = new JSONObject();
		JSONArray arts = new JSONArray();
		arts.addAll(Arrays.asList(articles));
		json.put("articles", arts);
		return sendMessage(token, touser, "news", json, kfID);
	}

	/**
	 * 发送微信图文消息 （图文消息条数限制在8条以内）{@link MediaOperations}
	 * 
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口获取的图文素材media_id
	 * @return
	 */
	public static boolean sendMPNews(AbstractAccessToken token, String touser, String mediaID) {
		return sendMPNewsUseAccount(token, touser, mediaID, null);
	}

	/**
	 * 通过指定客服发送微信图文消息 （图文消息条数限制在8条以内）{@link MediaOperations}
	 * 
	 * @param token
	 * @param touser
	 * @param mediaID 通过素材接口获取的图文素材media_id
	 * @param kfID
	 * @return
	 */
	public static boolean sendMPNewsUseAccount(AbstractAccessToken token, String touser, String mediaID, String kfID) {
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		return sendMessage(token, touser, "mpnews", json, kfID);
	}

	/**
	 * 发送微信卡券消息
	 * 
	 * @param token
	 * @param touser
	 * @param cardID 卡券ID
	 * @return
	 */
	public boolean sendWXCard(AbstractAccessToken token, String touser, String cardID) {
		return sendWXCard(token, touser, cardID, null);
	}

	/**
	 * 通过指定客服下发卡券消息
	 * 
	 * @param token
	 * @param touser
	 * @param cardID 
	 * @param kfID
	 * @return
	 */
	public static boolean sendWXCard(AbstractAccessToken token, String touser, String cardID, String kfID) {
		JSONObject json = new JSONObject();
		json.put("card_id", cardID);
		return sendMessage(token, touser, "wxcard", json, kfID);
	}

	private static boolean sendMessage(AbstractAccessToken token, String touser, String type, JSONObject data,
			String kfID) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("touser", touser);
		postData.put("msgtype", type);
		if (data != null)
			postData.put(type, data);

		if (StringUtils.isNotBlank(kfID)) {
			JSONObject kfAccount = new JSONObject();
			kfAccount.put("kf_account", kfID);
			postData.put("customservice", kfAccount);
		}

		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

}
