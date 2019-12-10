package com.as1124.api.wechat.model.message;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.as1124.api.wechat.WechatConstants;
import com.as1124.api.wechat.model.AbstractDataPackage;

/**
 * 语音消息。支持收发。
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class VoiceMessage extends AbstractMessage {

	/**
	 * 语音消息媒体id，可以调用多媒体素材接口拉取数据。
	 */
	protected String mediaID;
	
	/**
	 * 语音格式
	 */
	protected String format;
	
	/**
	 * 语音识别结果，UTF-8编码
	 */
	protected String recognition;
	
	public VoiceMessage() {
		super("voice");
	}
	
	public VoiceMessage(String xmlContent) {
		super("voice", xmlContent);
	}

	public String toSendText() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+this.getSentTime()+"</CreateTime>"
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
		 +"<Voice><MediaId><![CDATA["+getMediaID()+"]]></MediaId></Voice></xml>";
		return result;
	}

	/**
	 * {@link AbstractDataPackage#decodeFromXML(String)}
	 * @param xmlContent
	 * @return
	 */
	public Document decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(WechatConstants.CHARSET_UTF8)));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			Element mediaIDElement = root.element("MediaId");
			if(mediaIDElement != null){
				this.setMediaID(mediaIDElement.getText());
			}

			Element formatElement = root.element("Format");
			if(formatElement != null)
				this.setFormat(formatElement.getText());

			Element recognitionElement = root.element("Recognition");
			if(recognitionElement != null)
				this.setRecognition(recognitionElement.getText());
			
			Element msgIDElement = root.element("MsgId");
			if(msgIDElement != null){
				long msgID = Long.parseLong(msgIDElement.getText());
				this.setMsgID(msgID);
			}
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @see #mediaID
	 * @return
	 */
	public String getMediaID() {
		return mediaID;
	}

	/**
	 * @see #mediaID
	 * @param mediaID
	 */
	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	/**
	 * @see #format
	 * @return
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @see #format
	 * @param format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the {@link #recognition}
	 */
	public String getRecognition() {
		return recognition;
	}

	/**
	 * @param recognition the {@link #recognition} to set
	 */
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	
}
