package com.as1124.api.wechat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.as1124.api.access.AbstractAccessToken;
import com.as1124.api.access.HttpExecuter;
import com.as1124.api.events.menu.ChoosePhotoMenuEvent;
import com.as1124.api.events.menu.ClickMenuEvent;
import com.as1124.api.events.menu.ScancodeMenuEvent;
import com.as1124.api.events.menu.ViewMenuEvent;
import com.as1124.api.wechat.events.ReportLocationEvent;
import com.as1124.api.wechat.events.ScancodeEvent;
import com.as1124.api.wechat.events.SubscribeEvent;
import com.as1124.api.wechat.model.AbstractDataPackage;
import com.as1124.api.wechat.model.media.WechatMedia;
import com.as1124.api.wechat.model.message.ImageMessage;
import com.as1124.api.wechat.model.message.LinkMessage;
import com.as1124.api.wechat.model.message.LocationMessage;
import com.as1124.api.wechat.model.message.TextMessage;
import com.as1124.api.wechat.model.message.VideoMessage;
import com.as1124.api.wechat.model.message.VoiceMessage;
import com.as1124.common.util.LoggerUtil;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * 消息、事件处理接口。所有消息数据包中的<code>MediaId</code>都必须是微信服务器上 上传永久素材后获取的media_id。<br>
 * 对消息、事件的处理是通用的，用户得到处理后的对象后若有需要请<code>自行强转</code>成 对应
 * 类型（处理后会返回对象的具体类型的class全名）。
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 * 
 */
public class MessageOperations {

	static Logger logger = LoggerUtil.getLogger(MessageOperations.class);

	/**
	 * 加密消息报文
	 * @param text 报文正文
	 * @param encodingAESKey 公众号加密的AES密钥
	 * @param token 公众号配置的消息token（不是接口调用的access_token）
	 * @param appid 公众号appid
	 * @return
	 */
	public String encryptText(String text, String encodingAESKey, String token, String appid) {
		String result = text;
		try {
			WXBizMsgCrypt tool = new WXBizMsgCrypt(token, encodingAESKey, appid);
			result = tool.encryptMsg(text, "", WechatPayOperations.generateNonceStr());
		} catch (AesException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 解密消息报文得到明文
	 * @param text 接收到的报文正文
	 * @param encodingAESKey 公众号加密的AES密钥
	 * @param token 公众号配置的消息token（不是接口调用的access_token）
	 * @param appid 公众号的appid
	 * @param signature 微信发来请求中的<code>"signature"</code>字段
	 * @param timestamp 微信发来请求中的<code>"timestamp"</code>字段
	 * @param nonce 微信发来请求中的<code>"nonce"</code>字段
	 */

	public String decryptText(String text, String encodingAESKey, String token, String appid, String signature,
			String timestamp, String nonce) {
		String result = text;
		try {
			WXBizMsgCrypt tool = new WXBizMsgCrypt(token, encodingAESKey, appid);
			result = tool.decryptMsg(signature, timestamp, nonce, text);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 服务器在接收到POST请求发来的数据包后调用这个方法处理xml格式的数据报文。<br>
	 * <strong>微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次.
	 * 假如服务器无法保证在五秒内处理并回复，可以直接回复空串，</strong>微信服务器不会对此作任何处理， 并且不会发起重试。等数据处理完成后再响应
	 * 
	 * @param xmlContent
	 *            处理微信端请求得到的消息数据包
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public <T extends AbstractDataPackage> T dealReceivedMessage(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader
					.read(new ByteArrayInputStream(xmlContent.getBytes(WechatConstants.CHARSET_UTF8)));
			Element root = document.getRootElement();
			String msgType = root.element("MsgType").getText();

			Class<? extends AbstractDataPackage> decoder = null;
			// 事件数据包
			if ("event".equals(msgType)) {
				String eventType = root.element("Event").getText();
				decoder = getMessageDecoder(eventType);
			} else {
				decoder = getMessageDecoder(msgType);
			}
			if (decoder == null) {
				return null;
			}
			Constructor<? extends AbstractDataPackage> ct = decoder.getConstructor(String.class);
			AbstractDataPackage message = ct.newInstance(xmlContent);
			message.decodeFromXML(xmlContent);

			return (T) message;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 群发图文消息给指定分组标签下的用户
	 * 
	 * @param token
	 * @param groupID
	 * @param mediaID
	 * @param ignoreReprint 若文章为转载是否继续群发
	 * @return
	 */

	public static JSONObject sendNews2Group(AbstractAccessToken token, int groupID, String mediaID,
			boolean reprintContinue) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();

		JSONObject filterData = new JSONObject();
		filterData.put("is_to_all", false);
		filterData.put("tag_id", groupID);
		postData.put("filter", filterData);

		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		postData.put("mpnews", data);
		postData.put("msgtype", "mpnews");
		postData.put("sned_ignore_reprint", reprintContinue ? 1 : 0);

		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		JSONObject resultJson = JSONObject.parseObject(result);
		int returnCode = resultJson.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return resultJson;
		} else {
			System.err.println(WechatConstants.MSG_TAG + result);
			return null;
		}
	}

	/**
	 * 群发文本消息给指定分组标签下的用户
	 * 
	 * @param token
	 * @param groupID
	 * @param content
	 * @return
	 */
	public static String sendText2Group(AbstractAccessToken token, int groupID, String content) {
		JSONObject data = new JSONObject();
		data.put("content", content);
		return sendMessage2Group(token, "text", groupID, data);
	}

	/**
	 * 群发语音消息给指定分组下的用户
	 * 
	 * @param token
	 * @param groupID
	 * @param localFile 本地语音文件
	 * @return
	 */

	public static String sendVoice2Group(AbstractAccessToken token, int groupID, File localFile) {
		WechatMedia voiceMedia = MediaOperations.uploadTempVoice(token, localFile.getAbsolutePath());
		JSONObject data = new JSONObject();
		data.put("media_id", voiceMedia.getMedia_id());
		return sendMessage2Group(token, "voice", groupID, data);
	}

	/**
	 * 群发图片消息给指定分组下的用户
	 * 
	 * @param token
	 * @param groupID
	 * @param localFile 本地图片文件
	 * @return
	 */
	public static String sendImage2Group(AbstractAccessToken token, int groupID, File localFile) {
		WechatMedia imageMedia = MediaOperations.uploadTempImage(token, localFile.getAbsolutePath());
		JSONObject data = new JSONObject();
		data.put("media_id", imageMedia.getMedia_id());
		return sendMessage2Group(token, "image", groupID, data);
	}

	/**
	 * 群发微信卡券消息给指定分组下的用户
	 * @param token
	 * @param cardID
	 * @param groupID
	 * @return
	 */

	public static String sendWXCard2Group(AbstractAccessToken token, String cardID, int groupID) {
		JSONObject data = new JSONObject();
		data.put("card_id", cardID);
		return sendMessage2Group(token, "wxcard", groupID, data);
	}

	/**
	 * 群发视频消息给指定分组下的用户
	 * 
	 * @param token
	 * @param groupID
	 * @param localFile 本地视频文件
	 * @param title
	 * @param desc
	 * @return
	 */

	public static String sendVideo2Group(AbstractAccessToken token, int groupID, File localFile, String title,
			String desc) {
		WechatMedia videoMedia = MediaOperations.uploadTempVideo(token, localFile.getAbsolutePath());
		String uri = "http://api.weixin.qq.com/cgi-bin/media/uploadvideo";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));

		JSONObject postData = new JSONObject();
		postData.put("media_id", videoMedia.getMedia_id());
		postData.put("title", title);
		postData.put("description", desc);

		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject uploadResult = JSONObject.parseObject(result);
		int returnCode = uploadResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			postData.clear();
			postData.put("media_id", uploadResult.getString("media_id"));
			return sendMessage2Group(token, "mpvideo", groupID, postData);
		} else {
			System.err.println(WechatConstants.MSG_TAG + result);
			return null;
		}

	}

	private static String sendMessage2Group(AbstractAccessToken token, String type, int groupID, JSONObject data) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();

		JSONObject group = new JSONObject();
		group.put("is_to_all", false);
		group.put("tag_id", groupID);
		postData.put("filter", group);
		postData.put(type, data);
		postData.put("msgtype", type);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result).getString("msg_id");
		} else {
			System.err.println(WechatConstants.MSG_TAG + result);
			return null;
		}
	}

	/**
	 * @param token
	 * @param userOpenIDs
	 * @param mediaID
	 * @param reprintContinue
	 * @return
	 */
	public static JSONObject sendNews2Users(AbstractAccessToken token, String[] userOpenIDs, String mediaID,
			boolean reprintContinue) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		JSONArray users = new JSONArray();
		users.addAll(Arrays.asList(userOpenIDs));
		postData.put("touser", users);

		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		postData.put("mpnews", data);
		postData.put("msgtype", "mpnews");
		postData.put("sned_ignore_reprint", reprintContinue ? 1 : 0);

		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		JSONObject resultJson = JSONObject.parseObject(result);
		int returnCode = resultJson.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return resultJson;
		} else {
			System.err.println(WechatConstants.MSG_TAG + result);
			return null;
		}
	}

	/**
	 * @param token
	 * @param userOpenIDs
	 * @param content
	 * @return
	 */

	public static String sendText2Users(AbstractAccessToken token, String[] userOpenIDs, String content) {
		JSONObject data = new JSONObject();
		data.put("content", content);
		return sendMessage2Users(token, userOpenIDs, "text", data);
	}

	/**
	 * @param token
	 * @param userOpenIDs
	 * @param localFile 本地音频文件
	 * @return
	 */

	public static String sendVoice2Users(AbstractAccessToken token, String[] userOpenIDs, File localFile) {
		WechatMedia voiceMedia = MediaOperations.uploadTempVoice(token, localFile.getAbsolutePath());
		JSONObject data = new JSONObject();
		data.put("media_id", voiceMedia.getMedia_id());
		return sendMessage2Users(token, userOpenIDs, "voice", data);

	}

	/**
	 * @param token
	 * @param userOpenIDs
	 * @param localFile 本地图片文件
	 * @return
	 */

	public static String sendImage2Users(AbstractAccessToken token, String[] userOpenIDs, File localFile) {
		WechatMedia imageMedia = MediaOperations.uploadTempImage(token, localFile.getAbsolutePath());
		JSONObject data = new JSONObject();
		data.put("media_id", imageMedia.getMedia_id());
		return sendMessage2Users(token, userOpenIDs, "image", data);
	}

	/**
	 * @param token
	 * @param userOpenIDs
	 * @param localFile
	 * @param title
	 * @param desc
	 * @return
	 */

	public static String sendVideo2Users(AbstractAccessToken token, String[] userOpenIDs, File localFile, String title,
			String desc) {
		WechatMedia videoMedia = MediaOperations.uploadTempVideo(token, localFile.getAbsolutePath());
		String uri = "http://api.weixin.qq.com/cgi-bin/media/uploadvideo";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));

		JSONObject postData = new JSONObject();
		postData.put("media_id", videoMedia.getMedia_id());
		postData.put("title", title);
		postData.put("description", desc);

		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject uploadResult = JSONObject.parseObject(result);
		int returnCode = uploadResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			postData.clear();
			postData.put("media_id", uploadResult.getString("media_id"));
			return sendMessage2Users(token, userOpenIDs, "mpvideo", postData);
		} else {
			System.err.println(WechatConstants.MSG_TAG + result);
			return null;
		}

	}

	/**
	 * @param token
	 * @param cardID
	 * @param userOpenIDsD
	 * @return
	 */

	public static String sendWXCard2Users(AbstractAccessToken token, String cardID, String[] userOpenIDsD) {
		JSONObject data = new JSONObject();
		data.put("card_id", cardID);
		return sendMessage2Users(token, userOpenIDsD, "wxcard", data);
	}

	private static String sendMessage2Users(AbstractAccessToken token, String[] userOpenIDs, String type,
			JSONObject data) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		JSONArray users = new JSONArray();
		users.addAll(Arrays.asList(userOpenIDs));
		postData.put("touser", users);
		postData.put(type, data);
		postData.put("msgtype", type);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result).getString("msg_id");
		}
		return null;
	}

	/**
	 * 删除群发消息
	 * 
	 * @param token
	 * @param msgID
	 * @return
	 */

	public static boolean deleteSendBatchMessage(AbstractAccessToken token, String msgID) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/delete";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("msg_id", msgID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		return (returnCode == WechatConstants.RETURN_CODE_SUCCESS);
	}

	/**
	 * 查看群发消息是否成功发送
	 * 
	 * @param token
	 * @param msgID
	 * @return
	 */

	public static boolean isBatchMessageSuccess(AbstractAccessToken token, String msgID) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("msg_id", msgID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			String isSuccess = JSONObject.parseObject(result).getString("msg_status");
			return isSuccess.equals("SEND_SUCCESS");
		}
		return false;
	}

	/**
	 * 获取公众号自动回复规则
	 * 
	 * @param token
	 * @return 规则信描述（json格式）
	 */

	public static String getAutoReplyRule(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		return HttpExecuter.executeGetAsString(uri, queryStr);
	}

	/**
	 * 消息解码器
	 */
	protected static Map<String, Class<? extends AbstractDataPackage>> decoders = new HashMap<>();

	private static void initDecoders() {

		//菜单事件
		decoders.put("VIEW", ViewMenuEvent.class);
		decoders.put("CLICK", ClickMenuEvent.class);
		decoders.put("scancode_push", ScancodeMenuEvent.class);
		decoders.put("scancode_waitmsg", ScancodeMenuEvent.class);
		decoders.put("pic_sysphoto", ChoosePhotoMenuEvent.class);
		decoders.put("pic_photo_or_album", ChoosePhotoMenuEvent.class);
		decoders.put("pic_weixin", ChoosePhotoMenuEvent.class);
		decoders.put("location_select", ChoosePhotoMenuEvent.class);

		//普通消息
		decoders.put("text", TextMessage.class);
		decoders.put("image", ImageMessage.class);
		decoders.put("voice", VoiceMessage.class);
		decoders.put("video", VideoMessage.class);
		decoders.put("shortvideo", VideoMessage.class);
		decoders.put("location", LocationMessage.class);
		decoders.put("link", LinkMessage.class);

		//事件
		decoders.put("subscribe", SubscribeEvent.class);
		decoders.put("unsubscribe", SubscribeEvent.class);
		decoders.put("SCAN", ScancodeEvent.class);
		decoders.put("LOCATION", ReportLocationEvent.class);
	}

	/**
	 * 如果是普通消息则type为<code>MsgType</code>的值，如果是事件则type为<code>Event</code>
	 * 的值
	 * @param type 
	 * @return
	 */

	public static Class<? extends AbstractDataPackage> getMessageDecoder(String type) {
		if (decoders == null || decoders.isEmpty()) {
			initDecoders();
		}
		return decoders.get(type);
	}

	/**
	 * @param type
	 * @param clazz
	 */

	public static void registMessageDecoder(String type, Class<? extends AbstractDataPackage> clazz) {
		decoders.put(type, clazz);
	}

	/**
	 * @param type
	 */

	public static void removeMessageDecoder(String type) {
		decoders.remove(type);
	}
}
