package com.primeton.mobile.wechat.model.events;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.primeton.mobile.wechat.IWechatConstants;

/**
 * <code>CLICK</code>类型菜单触发的事件模型。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ClickMenuEvent extends AbstractWechatMenuEvent {

	public ClickMenuEvent(String xmlContentString) {
		super(xmlContentString);
		this.setEvent("CLICK");
	}
	
	@Override
	public Document decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(
					IWechatConstants.DEFAULT_CHARSET)));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setEvent(root.element("Event").getText());
			this.setEventKey(root.element("EventKey").getText());
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
