package com.primeton.mobile.wechat.model.events;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <code>CLICK</code>类型菜单触发的事件模型。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ClickMenuEvent extends AbstractWechatMenuEvent {

	public ClickMenuEvent() {
		super();
		this.setEvent("CLICK");
	}
	
	@Override
	public String toXML() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
			 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
			 +"<CreateTime>"+getCreateTime()+"</CreateTime>"
			 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
			 +"<Event><![CDATA["+getEvent()+"]]></Event>"
			 +"<EventKey><![CDATA["+getEventKey()+"]]></EventKey></xml>";
		return result;
	}
	
	@Override
	public void decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes()));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setEvent(root.element("Event").getText());
			this.setEventKey(root.element("EventKey").getText());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
