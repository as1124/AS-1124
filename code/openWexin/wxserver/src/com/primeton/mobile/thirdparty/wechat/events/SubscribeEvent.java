package com.primeton.mobile.thirdparty.wechat.events;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.primeton.mobile.thirdparty.wechat.IWechatConstants;
import com.primeton.mobile.thirdparty.wechat.model.AbstractDataPackage;

/**
 * 关注/取消关注事件。用户在关注与取消关注公众号时，微信会把这个事件推送到开发者填写的URL。
 * 方便开发者给用户下发欢迎消息或者做帐号的解绑。
 * @author huangjw(mailto:huangjw@primeton.com)
 * @event <code>subscribe</code>(订阅)、<code>unsubscribe</code>(取消订阅)
 */
public class SubscribeEvent extends AbstractWechatEvent{

	/**
	 * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 */
	protected String eventKey;
	
	/**
	 * 二维码的ticket，可用来换取二维码图片
	 */
	protected String ticket;
	
	public SubscribeEvent(String xmlContent) {
		super("", xmlContent);
	}

	/**
	 * {@link AbstractDataPackage#decodeFromXML(String)}
	 * @param xmlContent
	 * @return
	 */
	public Document decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(IWechatConstants.DEFAULT_CHARSET)));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setEvent(root.element("Event").getText());
			
			//未关注公众号时扫码的事件
			this.setEventKey(root.element("EventKey").getText());
			this.setTicket(root.element("Ticket").getText());
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @return the {@link #eventKey}
	 */
	public String getEventKey() {
		return eventKey;
	}

	/**
	 * @param eventKey the {@link #eventKey} to set
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	/**
	 * @return the {@link #ticket}
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the {@link #ticket} to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}
