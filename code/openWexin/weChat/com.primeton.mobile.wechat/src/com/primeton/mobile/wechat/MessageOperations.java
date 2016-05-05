package com.primeton.mobile.wechat;

/**
 * 消息/时间处理接口。所有消息数据包中的<code>MediaId</code>都必须微信服务器上
 * 上传永久素材后获取的media_id。<br>
 * 对消息/事件的处理是通用的，用户得到处理后的对象后若需要请<code>自行强
 * 转</code>成对应类型（处理后会返回对象的具体类型的class全名）。
 * 
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eos.system.annotation.Bizlet;
import com.primeton.mobile.wechat.model.media.WechatNews;
import com.primeton.mobile.wechat.model.message.AbstractMessage;
import com.primeton.mobile.wechat.model.message.ImageMessage;
import com.primeton.mobile.wechat.model.message.LinkMessage;
import com.primeton.mobile.wechat.model.message.LocationMessage;
import com.primeton.mobile.wechat.model.message.RecognitionMessage;
import com.primeton.mobile.wechat.model.message.TextMessage;
import com.primeton.mobile.wechat.model.message.VideoMessage;
import com.primeton.mobile.wechat.model.message.VoiceMessage;
import com.primeton.mobile.wechat.model.message.event.BatchMessageCallbackEvent;
import com.primeton.mobile.wechat.model.message.event.ChoosePhotoMenuEvent;
import com.primeton.mobile.wechat.model.message.event.ClickMenuEvent;
import com.primeton.mobile.wechat.model.message.event.LocationSelectMenuEvent;
import com.primeton.mobile.wechat.model.message.event.ReportLocationEvent;
import com.primeton.mobile.wechat.model.message.event.ScancodeEvent;
import com.primeton.mobile.wechat.model.message.event.ScancodeMenuEvent;
import com.primeton.mobile.wechat.model.message.event.SubscribeEvent;
import com.primeton.mobile.wechat.model.message.event.ViewMenuEvent;

/**
 * 
 * WeChat 消息操作接口.
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class MessageOperations {
	
	/**
	 * 校验接入请求是否来自微信。web端在方法返回<code>true</code>的
	 * 情况下应原样向微信返回<code>echostr</code>
	 * @param signature 微信加密签名
	 * @param accessToken 令牌
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	@Bizlet("checkSignature")
	public static boolean checkSignature(String signature, String accessToken, String timestamp, String nonce){
		ArrayList<String> list = new ArrayList<String>();
		list.add(accessToken);
		list.add(timestamp);
		list.add(nonce);
		list.sort(new Comparator<String>() {

			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		String tempStr = list.get(0)+list.get(1)+list.get(2);
		//SHA1签名
		String resultStr = DigestUtils.shaHex(tempStr);
		if(resultStr.equals(signature)){
			return true;
		}
		else return false;
	}
	
	/**
	 * 因为用户不知道消息到底是什么类型，所以只能如此处理。用户在接收到POST请求发来的数据包后调用
	 * 这个方法处理xml格式的数据报文。<br>
	 * <strong>微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次.
	 * 假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试。 
	 * 等数据处理完成后再响应</strong>
	 * @param xmlContent 处理微信端请求得到的消息数据包
	 * @return Map key: String, class 的全名, value: 消息对象
	 */
	@Bizlet("dealReceivedMessage")
	public static Map<String, AbstractMessage> dealReceivedMessage(String xmlContent){
		Map<String, AbstractMessage> result = new HashMap<String, AbstractMessage>();
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes()));
			Element root = document.getRootElement();
			String msgType = root.element("MsgType").getText();
			if("text".equals(msgType)){
				//文本消息
				TextMessage message = receiveText(xmlContent);
				result.put(message.getClass().getName(), message);
			}else if("image".equals(msgType)){
				//图片消息
				ImageMessage message = receiveImage(xmlContent);
				result.put(message.getClass().getName(), message);
			}else if("voice".equals(msgType)){
				if(root.element("Recognition") != null){
					//语音识别消息
					RecognitionMessage message = receiveRecognitionMessage(xmlContent);
					result.put(message.getClass().getName(), message);
				}else{
					//普通语音消息
					VoiceMessage message = receiveVoice(xmlContent);
					result.put(message.getClass().getName(), message);
				}
			}else if("video".equals(msgType)){
				//视频消息
				VideoMessage message = receiveVideo(xmlContent);
				result.put(message.getClass().getName(), message);
			}else if("shortvideo".equals(msgType)){
				//小视频消息
				VideoMessage message = receiveVideo(xmlContent);
				result.put(message.getClass().getName(), message);
			}else if("location".equals(msgType)){
				//地理位置消息
				LocationMessage message = receiveLocationMessage(xmlContent);
				result.put(message.getClass().getName(), message);
			}else if("link".equals(msgType)){
				//链接消息
				LinkMessage message = receiveLinkMessage(xmlContent);
				result.put(message.getClass().getName(), message);
			}else if("event".equals(msgType)){
				//事件数据包
				String eventType = root.element("Event").getText();
				if("subscribe".equals(eventType)){
					if(root.element("Ticket") != null){
						ScancodeEvent event = receiveScancodeEvent(xmlContent);
						result.put(event.getClass().getName(), event);
					}else{
						SubscribeEvent event = receiveSubscribeEvent(xmlContent);
						result.put(event.getClass().getName(), event);
					}
				}else if("unsubscribe".equals(eventType)){
					SubscribeEvent event = receiveSubscribeEvent(xmlContent);
					result.put(event.getClass().getName(), event);
				}else if("SCAN".equals(eventType)){
					ScancodeEvent event = receiveScancodeEvent(xmlContent);
					result.put(event.getClass().getName(), event);
				}else if("LOCATION".equals(eventType)){
					//上报地理位置事件
					ReportLocationEvent event = receiveLocationEvent(xmlContent);
					result.put(event.getClass().getName(), event);
				}else if("CLICK".equals(eventType)){
					//CLICK 菜单事件
					ClickMenuEvent event = receiveClickMenuEvent(xmlContent);
					result.put(event.getClass().getName(), event);
				}else if("VIEW".equals(eventType)){
					//VIEW 菜单事件
					ViewMenuEvent event = receiveViewMenuEvent(xmlContent);
					result.put(event.getClass().getName(), event);
				}else if(eventType.startsWith("scancode")){
					//扫码菜单
					ScancodeMenuEvent event = receiveScancodeMenuEvent(xmlContent);
					result.put(event.getClass().getName(), event);
				}else if(eventType.startsWith("pic_")){
					//发图菜单
					ChoosePhotoMenuEvent event = receiveChoosePhotoMenuEvent(xmlContent);
					result.put(event.getClass().getName(), event);
				}else if("location_select".equals(eventType)){
					LocationSelectMenuEvent event = receiveSelectLocationMenuEvent(xmlContent);
					result.put(event.getClass().getName(), event);
				}else if("MASSSENDJOBFINISH".equals(eventType)){
					BatchMessageCallbackEvent event = receiveBatchMsgCallbackEvent(xmlContent);
					result.put(BatchMessageCallbackEvent.class.getName(), event);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 接收普通文本消息
	 * @param xmlContent 从微信接收到的消息内容，xml格式
	 * @return
	 */
	private static TextMessage receiveText(String xmlContent){
		TextMessage message = new TextMessage();
		message.decodeFromXML(xmlContent);
		return message;
	}
	
	/**
	 * 接收图片消息
	 * @param xmlContent 从微信接收到的消息内容，xml格式
	 * @return
	 */
	private static ImageMessage receiveImage(String xmlContent){
		ImageMessage message = new ImageMessage();
		message.decodeFromXML(xmlContent);
		return message;
	}

	/**
	 * 接收视频消息
	 * @param xmlContent 从微信接收到的消息内容，xml格式
	 * @return
	 */
	private static VideoMessage receiveVideo(String xmlContent){
		VideoMessage message = new VideoMessage();
		message.decodeFromXML(xmlContent);
		return message;
	}
	
	/**
	 * 接收语音消息
	 * @param xmlContent 从微信接收到的消息内容，xml格式
	 * @return
	 */
	private static VoiceMessage receiveVoice(String xmlContent){
		VoiceMessage message = new VoiceMessage();
		message.decodeFromXML(xmlContent);
		return message;
	}
	
	/**
	 * 接收位置消息
	 * @param xmlContent 从微信接收到的消息内容，xml格式
	 * @return
	 */
	private static LocationMessage receiveLocationMessage(String xmlContent){
		LocationMessage message = new LocationMessage();
		message.decodeFromXML(xmlContent);
		return message;
	}

	/**
	 * 接收连接消息
	 * @param xmlContent 从微信接收到的消息内容，xml格式
	 * @return
	 */
	private static LinkMessage receiveLinkMessage(String xmlContent){
		LinkMessage message = new LinkMessage();
		message.decodeFromXML(xmlContent);
		return message;
	}
	
	/**
	 * 接收语音识别消息
	 * @param xmlContent 从微信接收到的消息内容，xml格式
	 * @return
	 */
	private static RecognitionMessage receiveRecognitionMessage(String xmlContent){
		RecognitionMessage message = new RecognitionMessage();
		message.decodeFromXML(xmlContent);
		return message;
	}
	
	/**
	 * 接收关注、取消关注事件
	 * @param xmlContent
	 * @return
	 */
	private static SubscribeEvent receiveSubscribeEvent(String xmlContent){
		SubscribeEvent event = new SubscribeEvent();
		event.decodeFromXML(xmlContent);
		return event;
	}
	
	/**
	 * 接收扫码事件
	 * @param xmlContent
	 * @return
	 */
	private static ScancodeEvent receiveScancodeEvent(String xmlContent){
		ScancodeEvent event = new ScancodeEvent();
		event.decodeFromXML(xmlContent);
		return event;
	}
	
	/**
	 * 接收上报地理位置事件
	 * @param xmlContent
	 * @return
	 */
	private static ReportLocationEvent receiveLocationEvent(String xmlContent){
		ReportLocationEvent event = new ReportLocationEvent();
		event.decodeFromXML(xmlContent);
		return event;
	}
	
	/**
	 * 接收普通点击菜单事件
	 * @param xmlContent
	 * @return
	 */
	private static ClickMenuEvent receiveClickMenuEvent(String xmlContent){
		ClickMenuEvent event = new ClickMenuEvent();
		event.decodeFromXML(xmlContent);
		return event;
	}
	
	/**
	 * 接收VIEW类型菜单事件
	 * @param xmlContent
	 * @return
	 */
	private static ViewMenuEvent receiveViewMenuEvent(String xmlContent){
		ViewMenuEvent event = new ViewMenuEvent();
		event.decodeFromXML(xmlContent);
		return event;
	}
	
	/**
	 * 接收扫码菜单事件
	 * @param xmlContent
	 * @return
	 */
	private static ScancodeMenuEvent receiveScancodeMenuEvent(String xmlContent){
		ScancodeMenuEvent event = new ScancodeMenuEvent();
		event.decodeFromXML(xmlContent);
		return event;
	}
	
	/**
	 * 接收发图菜单事件
	 * @param xmlContent
	 * @return
	 */
	private static ChoosePhotoMenuEvent receiveChoosePhotoMenuEvent(String xmlContent){
		ChoosePhotoMenuEvent event = new ChoosePhotoMenuEvent();
		event.decodeFromXML(xmlContent);
		return event;
	}
	
	/**
	 * 由于群发任务提交后，群发任务可能在一定时间后才完成，因此，群发接口调用时，仅会给出群发任务是否提交成功的提示，
	 * 若群发任务提交成功，则在群发任务结束时，会向开发者在公众平台填写的开发者URL（callback URL）推送事件。 
	 * 需要注意，由于群发任务彻底完成需要较长时间，将会在群发任务即将完成的时候，就推送群发结果，此时的推送人数数据将会与实际情形存在一定误差 
	 * @param xmlContent
	 * @return
	 */
	private static LocationSelectMenuEvent receiveSelectLocationMenuEvent(String xmlContent){
		LocationSelectMenuEvent event = new LocationSelectMenuEvent();
		event.decodeFromXML(xmlContent);
		return event;
	}
	
	/**
	 * 接收地理位置菜单事件
	 * @param xmlContent
	 * @return
	 */
	private static BatchMessageCallbackEvent receiveBatchMsgCallbackEvent(String xmlContent){
		BatchMessageCallbackEvent event = new BatchMessageCallbackEvent();
		event.decodeFromXML(xmlContent);
		return event;
	}
	
	//ATTENTION 回复消息用户就直接 new一个对象，然后调用toSendText()就好。
	
	/**
	 * 上传图文消息素材
	 * @param accessToken
	 * @param news
	 * @return media_id
	 * @throws IOException
	 */
	@Bizlet("uploadNews")
	public static String uploadNews(String accessToken, List<WechatNews> news) throws IOException{
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONArray articles = new JSONArray();
		articles.addAll(news);
		JSONObject postData = new JSONObject();
		postData.put("articles", articles);
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString(), IWechatConstants.CONTENT_TYPE, 
				IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result).getString("media_id");
		}
        return null;
	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return
	 * @throws IOException 
	 */
	@Bizlet("sendText2Group")
	public static String sendText2Group(String accessToken, String groupID, String content) throws IOException{
		JSONObject data = new JSONObject();
		data.put("content", content);
		return sendMessage2Group(accessToken, "text", groupID, data);
	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return
	 * @throws IOException 
	 */
	@Bizlet("sendImage2Group")
	public static String sendImage2Group(String accessToken, String groupID, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Group(accessToken, "image", groupID, data);

	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return
	 * @throws IOException 
	 */
	@Bizlet("sendNews2Group")
	public static String sendNews2Group(String accessToken, String groupID, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Group(accessToken, "mpnews", groupID, data);

	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return 
	 * @throws IOException 
	 */
	@Bizlet("sendVoice2Group")
	public static String sendVoice2Group(String accessToken, String groupID, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Group(accessToken, "voice", groupID, data);
	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return
	 * @throws IOException 
	 */
	@Bizlet("sendVideo2Group")
	public static String sendVideo2Group(String accessToken, String groupID, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Group(accessToken, "mpvideo", groupID, data);

	}
	
	private static String sendMessage2Group(String accessToken, String type, String groupID, JSONObject data) throws IOException{
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject postData = new JSONObject();
		JSONObject group = new JSONObject();
		group.put("is_to_all", false);
		group.put("group_id", groupID);
		postData.put("filter", group);
		postData.put(type, data);
		postData.put("msgtype", type);
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString(), 
				IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result).getString("msg_id");
		}
        return null;
	}
	
	/**
	 * 群发消息给指定的用户
	 * @return 
	 * @throws IOException 
	 */
	@Bizlet("sendText2Users")
	public static String sendText2Users(String accessToken, List<String> userOpenIDs, String content) throws IOException{
		JSONObject data = new JSONObject();
		data.put("content", content);
		return sendMessage2Users(accessToken, userOpenIDs, "text", data);

	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return 
	 * @throws IOException 
	 */
	@Bizlet("sendImage2Users")
	public static String sendImage2Users(String accessToken, List<String> userOpenIDs, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Users(accessToken, userOpenIDs, "image", data);

	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return 
	 * @throws IOException 
	 */
	@Bizlet("sendNews2Users")
	public static String sendNews2Users(String accessToken, List<String> userOpenIDs, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Users(accessToken, userOpenIDs, "mpnews", data);

	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return 
	 */
	@Bizlet("sendVoice2Users")
	public static String sendVoice2Users(String accessToken, List<String> userOpenIDs, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Users(accessToken, userOpenIDs, "mpnews", data);

	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return 
	 */
	@Bizlet("sendVideo2Users")
	public static String sendVideo2Users(String accessToken, List<String> userOpenIDs, String mediaID, String title, String description) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		data.put("title", title);
		data.put("description", description);
		return sendMessage2Users(accessToken, userOpenIDs, "video", data);
	} 
	
	private static String sendMessage2Users(String accessToken, List<String> userOpenIDs, String type, JSONObject data) throws IOException{
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject postData = new JSONObject();
		JSONArray users = new JSONArray();
		users.addAll(userOpenIDs);
		postData.put("touser", users);
		postData.put(type, data);
		postData.put("msgtype", type);
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString(), 
				IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result).getString("msg_id");
		}
        return null;
	}
	
	/**
	 * 删除群发消息
	 * @param accessToken
	 * @param userOpenIDs
	 * @param type
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@Bizlet("deleteSendBatchMessage")
	public static boolean deleteSendBatchMessage(String accessToken, String msgID) throws IOException{
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/delete";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject postData = new JSONObject();
		postData.put("msg_id", msgID);
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString(), 
				IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
        return false;
	}
	
	/**
	 * 获取群发消息的状态
	 * @param accessToken
	 * @param userOpenIDs
	 * @param type
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@Bizlet("getBatchMessageStatus")
	public static String getBatchMessageStatus(String accessToken, String msgID) throws IOException{
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/get";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject postData = new JSONObject();
		postData.put("msg_id", msgID);
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString(), 
				IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result).getString("msg_status");
		}
        return "";
	}
	
}
