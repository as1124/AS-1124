package com.primeton.mobile.wechat.model.events;


/**
 * 菜单事件。用户点击自定义菜单后，微信会把点击事件推送给开发者，
 * 请注意，点击菜单弹出子菜单，不会产生上报。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 */
public abstract class AbstractWechatMenuEvent extends AbstractWechatEvent {

	public AbstractWechatMenuEvent(String xmlContent){
		super(xmlContent);
	}
	
	/**
	 * 事件KEY值，与自定义菜单接口中KEY值对应（当是跳转事件时为对应的url）
	 */
	protected String eventKey;
	
	public AbstractWechatMenuEvent() {
		super();
	}

	/**
	 * 事件KEY值，与自定义菜单接口中KEY值对应；
	 * 当是菜单跳转事件时为设置的跳转URL
	 * @return
	 * @see #eventKey
	 */
	public String getEventKey() {
		return eventKey;
	}

	/**
	 * @see #eventKey
	 * @param eventKey
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

}
