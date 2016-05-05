package com.primeton.mobile.wechat.model.message;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 语音识别消息
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class RecognitionMessage extends VoiceMessage {

	/**
	 * 语音识别结果，UTF-8编码
	 */
	protected String recognition;
	
	public RecognitionMessage() {
		super();
	}
	
	/**
	 * {@link AbstractMessage#toXML()}
	 * @return
	 */
	public String toXML() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+getCreateTime()+"</CreateTime>"
		 +"<MsgType><![CDATA[voice]]></MsgType>"
		 +"<MediaId><![CDATA["+getMediaID()+"]]></MediaId>"
		 +"<Format><![CDATA["+getFormat()+"]]></Format>"
		 +"<Recognition><![CDATA["+getRecognition()+"]]></Recognition>"
		 +"<MsgId>"+getMsgID()+"</MsgId></xml>";
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
			int createTime = Integer.parseInt(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setMediaID(root.element("MediaId").getText());
			this.setFormat(root.element("Format").getText());
			this.setRecognition(root.element("Recognition").getText());
			long msgID = Long.parseLong(root.element("MsgId").getText());
			this.setMsgID(msgID);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

}
