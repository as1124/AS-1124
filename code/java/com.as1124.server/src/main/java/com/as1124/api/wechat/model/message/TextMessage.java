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
 * 文本消息模型。支持收发。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class TextMessage extends AbstractMessage {

	/**
	 * 文本消息内容
	 */
	protected String content;
	
	public TextMessage() {
		super("text");
	}
	
	public TextMessage(String xmlContent) {
		super("text", xmlContent);
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
			this.setContent(root.element("Content").getText());
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
	
	public String toSendText() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+this.getSentTime()+"</CreateTime>"
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
		 +"<Content><![CDATA["+getContent()+"]]></Content></xml>";
		return result;
	}
}
