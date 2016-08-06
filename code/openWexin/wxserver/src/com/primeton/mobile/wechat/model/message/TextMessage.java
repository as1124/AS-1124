package com.primeton.mobile.wechat.model.message;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 文本消息模型。支持收发。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class TextMessage extends AbstractCommonMessage {

	/**
	 * 文本消息内容
	 */
	protected String content;
	
	public TextMessage() {
		super();
		this.setMsgType("text");
	}
	
	/**
	 * @see #content
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @see #content
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
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
		 +"<Content><![CDATA["+getContent()+"]]></Content>"
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
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setContent(root.element("Content").getText());
			Element msgIDElement = root.element("MsgId");
			if(msgIDElement != null){
				long msgID = Long.parseLong(msgIDElement.getText());
				this.setMsgID(msgID);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
        
	}
	
	@Override
	public String toSendText() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+getCreateTime()+"</CreateTime>"
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
		 +"<Content><![CDATA["+getContent()+"]]></Content></xml>";
		return result;
	}
}
