package com.as1124.api.events.menu;

import org.dom4j.Document;
import org.dom4j.Element;


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
		Document document = super.decodeFromXML(xmlContent);
		Element root = document.getRootElement();
		Element menuElement = root.element("MenuID");
		if(menuElement!=null)
			this.setEventKey(menuElement.getText());
		return document;
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
