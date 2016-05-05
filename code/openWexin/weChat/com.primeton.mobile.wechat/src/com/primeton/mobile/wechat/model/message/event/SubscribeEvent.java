package com.primeton.mobile.wechat.model.message.event;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.primeton.mobile.wechat.model.message.AbstractMessage;

/**
 * 关注/取消关注事件。用户在关注与取消关注公众号时，微信会把这个事件推送到开发者填写的URL。
 * 方便开发者给用户下发欢迎消息或者做帐号的解绑。
 * @author huangjw(mailto:huangjw@primeton.com)
 * @event <code>subscribe</code>(订阅)、<code>unsubscribe</code>(取消订阅)
 */
public class SubscribeEvent extends AbstractEvent{

	public SubscribeEvent() {
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
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
		 +"<Event><![CDATA["+getEvent()+"]]></Event></xml>";
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
			this.setEvent(root.element("Event").getText());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
