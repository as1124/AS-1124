package com.primeton.mobile.thirdparty.wechat.model.events;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.primeton.mobile.thirdparty.wechat.IWechatConstants;


/**
 * <code>VIEW</code>类型菜单触发的事件模型。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ViewMenuEvent extends ClickMenuEvent{

	protected String menuId;
	
	public ViewMenuEvent(String xmlContent) {
		super(xmlContent);
		this.setEvent("VIEW");
	}
	
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
			Element menuElement = root.element("MenuID");
			if(menuElement!=null)
				this.setEventKey(menuElement.getText());
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the {@link #menuId}
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the {@link #menuId} to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	
}
