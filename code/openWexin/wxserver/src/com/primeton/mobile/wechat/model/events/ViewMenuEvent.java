package com.primeton.mobile.wechat.model.events;


/**
 * <code>VIEW</code>类型菜单触发的事件模型。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ViewMenuEvent extends ClickMenuEvent{

	public ViewMenuEvent() {
		super();
		this.setEvent("VIEW");
	}
	
}
