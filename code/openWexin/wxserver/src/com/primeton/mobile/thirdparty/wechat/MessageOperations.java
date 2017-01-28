package com.primeton.mobile.thirdparty.wechat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
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
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.wechat.events.ScancodeEvent;
import com.primeton.mobile.thirdparty.wechat.events.SubscribeEvent;
import com.primeton.mobile.thirdparty.wechat.events.menu.ChoosePhotoMenuEvent;
import com.primeton.mobile.thirdparty.wechat.events.menu.ClickMenuEvent;
import com.primeton.mobile.thirdparty.wechat.events.menu.ScancodeMenuEvent;
import com.primeton.mobile.thirdparty.wechat.events.menu.ViewMenuEvent;
import com.primeton.mobile.thirdparty.wechat.message.ImageMessage;
import com.primeton.mobile.thirdparty.wechat.message.LinkMessage;
import com.primeton.mobile.thirdparty.wechat.message.LocationMessage;
import com.primeton.mobile.thirdparty.wechat.message.TextMessage;
import com.primeton.mobile.thirdparty.wechat.message.VideoMessage;
import com.primeton.mobile.thirdparty.wechat.message.VoiceMessage;
import com.primeton.mobile.thirdparty.wechat.model.AbstractDataPackage;
import com.primeton.mobile.thirdparty.wechat.model.media.WechatArticle;

/**
 * 消息、事件处理接口。所有消息数据包中的<code>MediaId</code>都必须微信服务器上 上传永久素材后获取的media_id。<br>
 * 对消息、事件的处理是通用的，用户得到处理后的对象后若有需要请<code>自行强转</code>成 对应类型（处理后会返回对象的具体类型的class全名）。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 * 
 */
public class MessageOperations {

	/**
	 * 校验接入请求是否来自微信。web端在第一次接入微信时如果校验确认请求来自微信，此时应当 原样向微信返回
	 * <code>echostr</code>字段值才能保证接入成功。<br>
	 * 此后，每次开发者接收用户消息的时候，微信也都会带上三个参数（signature、timestamp、nonce）
	 * 发送到开发者设置的URL上，开发者依然通过对签名的效验判断此条消息的真实性。
	 * 
	 * @param signature
	 *            微信加密签名
	 * @param token
	 *            公众号申请时所配置的token串，不是access_token
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @return
	 */
	public boolean checkSignature(String signature, String token, String timestamp, String nonce) {
		String[] array = new String[] { token, timestamp, nonce };
		Arrays.sort(array, new Comparator<String>() {

			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		String tempStr = array[0] + array[1] + array[2];
		// SHA1签名
		String resultStr = DigestUtils.sha1Hex(tempStr);
		if (resultStr.equals(signature)) {
			return true;
		} else
			return false;
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
	public <T extends AbstractDataPackage>T dealReceivedMessage(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(IWechatConstants.DEFAULT_CHARSET)));
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

	
	// ATTENTION 回复消息用户就直接 new一个对象，然后调用toSendText()就好。

	/**
	 * 上传图文消息素材
	 * 
	 * @param accessToken
	 * @param news
	 * @return media_id
	 * @throws IOException
	 */
	public static String uploadNews(String accessToken, WechatArticle[] news)
			throws IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONArray articles = new JSONArray();
		articles.addAll(Arrays.asList(news));
		JSONObject postData = new JSONObject();
		postData.put("articles", articles);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON,
						IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(url, queryStr,
				requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(
				IWechatConstants.ERROR_CODE);
		if (returnCode == null
				|| IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseObject(result).getString("media_id");
		}
		return null;
	}

	/**
	 * 群发消息给指定分组下的用户
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String sendText2Group(String accessToken, String groupID,
			String content) throws IOException {
		JSONObject data = new JSONObject();
		data.put("content", content);
		return sendMessage2Group(accessToken, "text", groupID, data);
	}

	/**
	 * 群发消息给指定分组下的用户
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String sendImage2Group(String accessToken, String groupID,
			String mediaID) throws IOException {
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Group(accessToken, "image", groupID, data);

	}

	/**
	 * 群发消息给指定分组下的用户
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String sendNews2Group(String accessToken, String groupID,
			String mediaID) throws IOException {
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Group(accessToken, "mpnews", groupID, data);

	}

	/**
	 * 群发消息给指定分组下的用户
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String sendVoice2Group(String accessToken, String groupID,
			String mediaID) throws IOException {
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Group(accessToken, "voice", groupID, data);
	}

	/**
	 * 群发消息给指定分组下的用户
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String sendVideo2Group(String accessToken, String groupID,
			String mediaID) throws IOException {
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Group(accessToken, "mpvideo", groupID, data);

	}

	private static String sendMessage2Group(String accessToken, String type,
			String groupID, JSONObject data) throws IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		JSONObject group = new JSONObject();
		group.put("is_to_all", false);
		group.put("group_id", groupID);
		postData.put("filter", group);
		postData.put(type, data);
		postData.put("msgtype", type);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON,
						IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(url, queryStr,
				requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(
				IWechatConstants.ERROR_CODE);
		if (returnCode == null
				|| IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseObject(result).getString("msg_id");
		}
		return null;
	}

	/**
	 * 群发消息给指定的用户
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String sendText2Users(String accessToken,
			String[] userOpenIDs, String content) throws IOException {
		JSONObject data = new JSONObject();
		data.put("content", content);
		return sendMessage2Users(accessToken, userOpenIDs, "text", data);

	}

	/**
	 * 群发消息给指定分组下的用户
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String sendImage2Users(String accessToken,
			String[] userOpenIDs, String mediaID) throws IOException {
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Users(accessToken, userOpenIDs, "image", data);

	}

	/**
	 * 群发消息给指定分组下的用户
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String sendNews2Users(String accessToken,
			String[] userOpenIDs, String mediaID) throws IOException {
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Users(accessToken, userOpenIDs, "mpnews", data);

	}

	/**
	 * 群发消息给指定分组下的用户
	 * 
	 * @return
	 */
	public static String sendVoice2Users(String accessToken,
			String[] userOpenIDs, String mediaID) throws IOException {
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Users(accessToken, userOpenIDs, "mpnews", data);

	}

	/**
	 * 群发消息给指定分组下的用户
	 * 
	 * @return
	 */
	public static String sendVideo2Users(String accessToken,
			String[] userOpenIDs, String mediaID, String title,
			String description) throws IOException {
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		data.put("title", title);
		data.put("description", description);
		return sendMessage2Users(accessToken, userOpenIDs, "video", data);
	}

	private static String sendMessage2Users(String accessToken,
			String[] userOpenIDs, String type, JSONObject data)
			throws IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		JSONArray users = new JSONArray();
		users.addAll(Arrays.asList(userOpenIDs));
		postData.put("touser", users);
		postData.put(type, data);
		postData.put("msgtype", type);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON,
						IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(url, queryStr,
				requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(
				IWechatConstants.ERROR_CODE);
		if (returnCode == null
				|| IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseObject(result).getString("msg_id");
		}
		return null;
	}

	/**
	 * 删除群发消息
	 * 
	 * @param accessToken
	 * @param userOpenIDs
	 * @param type
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static boolean deleteSendBatchMessage(String accessToken,
			String msgID) throws IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/delete";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("msg_id", msgID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON,
						IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(url, queryStr,
				requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(
				IWechatConstants.ERROR_CODE);
		if (returnCode == null
				|| IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取群发消息的状态
	 * 
	 * @param accessToken
	 * @param userOpenIDs
	 * @param type
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static String getBatchMessageStatus(String accessToken, String msgID)
			throws IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("msg_id", msgID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON,
						IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(url, queryStr,
				requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(
				IWechatConstants.ERROR_CODE);
		if (returnCode == null
				|| IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseObject(result).getString("msg_status");
		}
		return "";
	}

	public static Map<String, Class<? extends AbstractDataPackage>> DECODERS = null;
	
	private static void initDecoders(){
		DECODERS = new HashMap<String, Class<? extends AbstractDataPackage>>();
		//菜单事件
		DECODERS.put("VIEW", ViewMenuEvent.class);
		DECODERS.put("CLICK", ClickMenuEvent.class);
		DECODERS.put("scancode_push", ScancodeMenuEvent.class);
		DECODERS.put("scancode_waitmsg", ScancodeMenuEvent.class);
		DECODERS.put("pic_sysphoto", ChoosePhotoMenuEvent.class);
		DECODERS.put("pic_photo_or_album", ChoosePhotoMenuEvent.class);
		DECODERS.put("pic_weixin", ChoosePhotoMenuEvent.class);
		DECODERS.put("location_select", ChoosePhotoMenuEvent.class);
		
		//普通消息
		DECODERS.put("text", TextMessage.class);
		DECODERS.put("image", ImageMessage.class);
		DECODERS.put("voice", VoiceMessage.class);
		DECODERS.put("video", VideoMessage.class);
		DECODERS.put("shortvideo", VideoMessage.class);
		DECODERS.put("location", LocationMessage.class);
		DECODERS.put("link", LinkMessage.class);
		
		//事件
		DECODERS.put("subscribe", SubscribeEvent.class);
		DECODERS.put("unsubscribe", SubscribeEvent.class);
		DECODERS.put("SCAN", ScancodeEvent.class);
		DECODERS.put("LOCATION", ScancodeEvent.class);
	}
	
	/**
	 * 如果是普通消息则type为<code>MsgType</code>的值，如果是事件则type为<code>Event</code>
	 * 的值
	 * @param type 
	 * @return
	 */
	public static Class<? extends AbstractDataPackage> getMessageDecoder(String type){
		if(DECODERS==null || DECODERS.isEmpty()){
			initDecoders();
		}
		return DECODERS.get(type);
	}
	
	/**
	 * @param type
	 * @param clazz
	 */
	public static void registMessageDecoder(String type, Class<? extends AbstractDataPackage> clazz){
		DECODERS.put(type, clazz);
	}
	
}
