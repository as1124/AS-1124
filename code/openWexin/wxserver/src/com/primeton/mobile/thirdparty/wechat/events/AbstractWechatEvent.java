package com.primeton.mobile.thirdparty.wechat.events;

import com.primeton.mobile.thirdparty.wechat.model.AbstractDataPackage;

/**
 * 事件消息数据包模型
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public abstract class AbstractWechatEvent extends AbstractDataPackage {

	/**
	 * 事件类型
	 */
	protected String event;
	
	public AbstractWechatEvent(String event) {
		super("event");
		this.event = event;
	}

	public AbstractWechatEvent(String event, String xmlContent) {
		super("event", xmlContent);
		this.event = event;
	}
	
	/**
	 * 因为微信公众号Client端无法处理服务端发送的事件消息
	 * @return null
	 */
	public final String toSendText() {
		return null;
	}
	
	/**
	 * @see #event
	 * @return
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @see #event
	 * @param event
	 */
	protected void setEvent(String event) {
		this.event = event;
	}

}
