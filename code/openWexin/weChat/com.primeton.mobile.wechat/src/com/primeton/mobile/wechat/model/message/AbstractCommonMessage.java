package com.primeton.mobile.wechat.model.message;

/**
 * 微信Client和服务端通信时抽象的通用消息数据包模型。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public abstract class AbstractCommonMessage extends AbstractMessage {
	
	/**
	 * 消息ID，64位整型
	 */
	protected long msgID;

	public AbstractCommonMessage() {
	}
	
	/**
	 * @see #msgID
	 * @return
	 */
	public long getMsgID() {
		return msgID;
	}

	/**
	 * @see #msgID
	 * @param msgID
	 */
	public void setMsgID(long msgID) {
		this.msgID = msgID;
	}
}
