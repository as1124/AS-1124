package com.primeton.mobile.thirdparty.wechat.message;

import com.primeton.mobile.thirdparty.wechat.model.AbstractDataPackage;

/**
 * 微信Client和服务端通信时抽象的通用消息数据包模型。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public abstract class AbstractMessage extends AbstractDataPackage {
	
	/**
	 * 消息ID，64位整型
	 */
	protected long msgID;
	
	public AbstractMessage(String msgType) {
		super(msgType);
	}

	public AbstractMessage(String msgType, String xmlContent){
		super(msgType, xmlContent);
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
	
	/**
	 * 消息发送到微信服务器的实践
	 * @return
	 */
	protected long getSentTime(){
		if(this.getCreateTime()>0)
			return this.getCreateTime();
		else return System.currentTimeMillis()/1000;
	}
}
