package com.primeton.mobile.thirdparty.wechat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
import com.priemton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.wechat.model.AbstractDataPackage;
import com.primeton.mobile.thirdparty.wechat.model.events.BatchMessageCallbackEvent;
import com.primeton.mobile.thirdparty.wechat.model.events.ChoosePhotoMenuEvent;
import com.primeton.mobile.thirdparty.wechat.model.events.ClickMenuEvent;
import com.primeton.mobile.thirdparty.wechat.model.events.LocationSelectMenuEvent;
import com.primeton.mobile.thirdparty.wechat.model.events.ReportLocationEvent;
import com.primeton.mobile.thirdparty.wechat.model.events.ScancodeEvent;
import com.primeton.mobile.thirdparty.wechat.model.events.ScancodeMenuEvent;
import com.primeton.mobile.thirdparty.wechat.model.events.SubscribeEvent;
import com.primeton.mobile.thirdparty.wechat.model.events.ViewMenuEvent;
import com.primeton.mobile.thirdparty.wechat.model.events.WIFIConnectedEvent;
import com.primeton.mobile.thirdparty.wechat.model.media.WechatNews;
import com.primeton.mobile.thirdparty.wechat.model.message.ImageMessage;
import com.primeton.mobile.thirdparty.wechat.model.message.LinkMessage;
import com.primeton.mobile.thirdparty.wechat.model.message.LocationMessage;
import com.primeton.mobile.thirdparty.wechat.model.message.RecognitionMessage;
import com.primeton.mobile.thirdparty.wechat.model.message.TextMessage;
import com.primeton.mobile.thirdparty.wechat.model.message.VideoMessage;
import com.primeton.mobile.thirdparty.wechat.model.message.VoiceMessage;


/**
 * 消息、事件处理接口。所有消息数据包中的<code>MediaId</code>都必须微信服务器上
 * 上传永久素材后获取的media_id。<br>
 * 对消息、事件的处理是通用的，用户得到处理后的对象后若有需要请<code>自行强转</code>成
 * 对应类型（处理后会返回对象的具体类型的class全名）。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class MessageOperations {
	
	/**
	 * 校验接入请求是否来自微信。web端在第一接入微信时如果校验结果返回<code>true</code>，此时应当
	 * 原样向微信返回<code>echostr</code>参数值才能保证接入成功。<br>
	 * 此后，每次开发者接收用户消息的时候，微信也都会带上三个参数（signature、timestamp、nonce）
	 * 访问开发者设置的URL，开发者依然通过对签名的效验判断此条消息的真实性。
	 * @param signature 微信加密签名
	 * @param token 令牌。公众号申请时所配置的token串，不是access_token
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	public static boolean checkSignature(String signature, String token, String timestamp, String nonce){

		String[] array = new String[]{token, timestamp, nonce};
		Arrays.sort(array, new Comparator<String>() {
			
			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		String tempStr = array[0]+array[1]+array[2];
		//SHA1签名
		String resultStr = DigestUtils.sha1Hex(tempStr);
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
	 * @return
	 */
	public static AbstractDataPackage dealReceivedMessage(String xmlContent){
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(
					IWechatConstants.DEFAULT_CHARSET)));
			Element root = document.getRootElement();
			String msgType = root.element("MsgType").getText();
			if("text".equals(msgType)){
				//文本消息
				return receiveText(xmlContent);
			}else if("image".equals(msgType)){
				//图片消息
				return receiveImage(xmlContent);
			}else if("voice".equals(msgType)){
				if(root.element("Recognition") != null){
					//语音识别消息
					return receiveRecognitionMessage(xmlContent);
				}else{
					//普通语音消息
					return receiveVoice(xmlContent);
				}
			}else if("video".equals(msgType)){
				//视频消息
				return receiveVideo(xmlContent);
			}else if("shortvideo".equals(msgType)){
				//小视频消息
				return receiveVideo(xmlContent);
			}else if("location".equals(msgType)){
				//地理位置消息
				return receiveLocationMessage(xmlContent);
			}else if("link".equals(msgType)){
				//链接消息
				return receiveLinkMessage(xmlContent);
			}else if("event".equals(msgType)){
				//事件数据包
				String eventType = root.element("Event").getText();
				if("subscribe".equals(eventType)){
					if(root.element("Ticket") != null){
						return receiveScancodeEvent(xmlContent);
					}else{
						return receiveSubscribeEvent(xmlContent);
					}
				}else if("unsubscribe".equals(eventType)){
					return receiveSubscribeEvent(xmlContent);
				}else if("SCAN".equals(eventType)){
					return receiveScancodeEvent(xmlContent);
				}else if("LOCATION".equals(eventType)){
					//上报地理位置事件
					return receiveLocationEvent(xmlContent);
				}else if("CLICK".equals(eventType)){
					//CLICK 菜单事件
					return receiveClickMenuEvent(xmlContent);
				}else if("VIEW".equals(eventType)){
					//VIEW 菜单事件
					return receiveViewMenuEvent(xmlContent);
				}else if(eventType.startsWith("scancode")){
					//扫码菜单
					return receiveScancodeMenuEvent(xmlContent);
				}else if(eventType.startsWith("pic_")){
					//发图菜单
					return receiveChoosePhotoMenuEvent(xmlContent);
				}else if("location_select".equals(eventType)){
					return receiveSelectLocationMenuEvent(xmlContent);
				}else if("MASSSENDJOBFINISH".equals(eventType)){
					return receiveBatchMsgCallbackEvent(xmlContent);
				}else if("WifiConnected".equals(eventType)){
					// 微信连接WIFI
					return receiveWIFIConnectedEvent(xmlContent);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
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
		ClickMenuEvent event = new ClickMenuEvent(xmlContent);
		return event;
	}
	
	/**
	 * 接收VIEW类型菜单事件
	 * @param xmlContent
	 * @return
	 */
	private static ViewMenuEvent receiveViewMenuEvent(String xmlContent){
		ViewMenuEvent event = new ViewMenuEvent(xmlContent);
		return event;
	}
	
	/**
	 * 接收扫码菜单事件
	 * @param xmlContent
	 * @return
	 */
	private static ScancodeMenuEvent receiveScancodeMenuEvent(String xmlContent){
		ScancodeMenuEvent event = new ScancodeMenuEvent(xmlContent);
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
		LocationSelectMenuEvent event = new LocationSelectMenuEvent(xmlContent);
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
	
	private static WIFIConnectedEvent receiveWIFIConnectedEvent(String xmlContent){
		WIFIConnectedEvent event = new WIFIConnectedEvent();
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
	public static String uploadNews(String accessToken, WechatNews[] news) throws IOException{
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONArray articles = new JSONArray();
		articles.addAll(Arrays.asList(news));
		JSONObject postData = new JSONObject();
		postData.put("articles", articles);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
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
	public static String sendVideo2Group(String accessToken, String groupID, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Group(accessToken, "mpvideo", groupID, data);

	}
	
	private static String sendMessage2Group(String accessToken, String type, String groupID, JSONObject data) throws IOException{
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
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
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
	public static String sendText2Users(String accessToken, String[] userOpenIDs, String content) throws IOException{
		JSONObject data = new JSONObject();
		data.put("content", content);
		return sendMessage2Users(accessToken, userOpenIDs, "text", data);

	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return 
	 * @throws IOException 
	 */
	public static String sendImage2Users(String accessToken, String[] userOpenIDs, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Users(accessToken, userOpenIDs, "image", data);

	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return 
	 * @throws IOException 
	 */
	public static String sendNews2Users(String accessToken, String[] userOpenIDs, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Users(accessToken, userOpenIDs, "mpnews", data);

	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return 
	 */
	public static String sendVoice2Users(String accessToken, String[] userOpenIDs, String mediaID) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		return sendMessage2Users(accessToken, userOpenIDs, "mpnews", data);

	}
	
	/**
	 * 群发消息给指定分组下的用户
	 * @return 
	 */
	public static String sendVideo2Users(String accessToken, String[] userOpenIDs, String mediaID, String title, String description) throws IOException{
		JSONObject data = new JSONObject();
		data.put("media_id", mediaID);
		data.put("title", title);
		data.put("description", description);
		return sendMessage2Users(accessToken, userOpenIDs, "video", data);
	} 
	
	private static String sendMessage2Users(String accessToken, String[] userOpenIDs, String type, JSONObject data) throws IOException{
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		JSONArray users = new JSONArray();
		users.addAll(Arrays.asList(userOpenIDs));
		postData.put("touser", users);
		postData.put(type, data);
		postData.put("msgtype", type);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
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
	public static boolean deleteSendBatchMessage(String accessToken, String msgID) throws IOException{
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/delete";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("msg_id", msgID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
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
	public static String getBatchMessageStatus(String accessToken, String msgID) throws IOException{
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("msg_id", msgID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result).getString("msg_status");
		}
        return "";
	}
	
}
