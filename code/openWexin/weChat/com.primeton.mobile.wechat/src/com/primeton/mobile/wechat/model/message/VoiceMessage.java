package com.primeton.mobile.wechat.model.message;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 语音消息。支持收发。
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class VoiceMessage extends AbstractCommonMessage {

	/**
	 * 语音消息媒体id，可以调用多媒体素材接口拉取数据。
	 */
	protected String mediaID;
	
	/**
	 * 语音格式
	 */
	protected String format;
	
	public VoiceMessage() {
		super();
		this.setMsgType("voice");
	}
	
	/**
	 * {@link AbstractMessage#toXML()}
	 * @return
	 */
	public String toXML() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+getCreateTime()+"</CreateTime>"
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
		 +"<MediaId><![CDATA["+getMediaID()+"]]></MediaId>"
		 +"<Format><![CDATA["+getFormat()+"]]></Format>"
		 +"<MsgId>"+getMsgID()+"</MsgId></xml>";
		return result;
	}

	@Override
	public String toSendText() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+getCreateTime()+"</CreateTime>"
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
		 +"<Voice><MediaId><![CDATA["+getMediaID()+"]]></MediaId></Voice></xml>";
		return result;
	}

	/**
	 * {@link AbstractMessage#decodeFromXML(String)}
	 * @param xmlContent
	 * @return
	 */
	public void decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes()));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			Element mediaIDElement = root.element("MediaId");
			if(mediaIDElement != null){
				this.setMediaID(mediaIDElement.getText());
			}
			Element voiceElement = root.element("Voice");
			if(voiceElement != null){
				this.setMediaID(voiceElement.element("MediaId").getText());
			}
			Element msgIDElement = root.element("MsgId");
			if(msgIDElement != null){
				long msgID = Long.parseLong(msgIDElement.getText());
				this.setMsgID(msgID);
			}
			Element formatElement = root.element("Format");
			if(formatElement != null)
				this.setFormat(formatElement.getText());
			this.setMsgID(msgID);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
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

}
