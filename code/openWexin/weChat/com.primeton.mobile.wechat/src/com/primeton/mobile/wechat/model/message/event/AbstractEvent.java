package com.primeton.mobile.wechat.model.message.event;

import com.primeton.mobile.wechat.model.message.AbstractMessage;

/**
 * 事件消息数据包模型
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public abstract class AbstractEvent extends AbstractMessage {

	/**
	 * 事件类型
	 */
	protected String event;
	
	public AbstractEvent() {
		this.setMsgType("event");
	}

	/**
	 * 因为微信公众号Client端无法处理服务端发送的事件消息
	 * @return null
	 */
	final public String toSendText() {
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
	public void setEvent(String event) {
		this.event = event;
	}

}
