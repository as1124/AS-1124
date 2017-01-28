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
 * 扫描带参数二维码事件
 * 
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 */
public class ScancodeEvent extends AbstractWechatEvent {

	/**
	 * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 */
	protected String eventKey;
	
	/**
	 * 二维码的ticket，可用来换取二维码图片
	 */
	protected String ticket;

	public ScancodeEvent(String xmlContent) {
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
	 * 
	 * @return
	 */
	public String getEventKey() {
		return eventKey;
	}

	/**
	 * 
	 * @param eventKey
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	/**
	 * 获取二维码的ticket
	 * @return
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * 
	 * @param ticket
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
