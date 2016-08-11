package com.primeton.mobile.wechat.model;

import org.dom4j.Document;

/**
 * 微信通信交互数据包模型（消息/事件）.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public abstract class AbstractDataPackage {

	/**
	 * 接收方微信号, openID
	 */
	protected String toUser;
	
	/**
	 * 发送方账号, openID
	 */
	protected String fromUser;
	
	/**
	 * 消息创建时间，整型
	 */
	protected long createTime;
	
	/**
	 * 消息类型
	 */
	protected String msgType;
	
	public AbstractDataPackage() {
	}

	/**
	 * 
	 * @param xmlContent 需要解析的XML报文
	 */
	public AbstractDataPackage(String xmlContent) {
		this.decodeFromXML(xmlContent);
	}
	
	/**
	 * @see #toUser
	 * @return
	 */
	public String getToUser() {
		return toUser;
	}

	/**
	 * @see #toUser
	 * @param toUser
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	/**
	 * @see #fromUser
	 * @return
	 */
	public String getFromUser() {
		return fromUser;
	}

	/**
	 * @see #fromUser
	 * @param fromUser
	 */
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * @see #createTime
	 * @return
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * @see #createTime
	 * @param createTime
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @see #msgType
	 * @return
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * @see #msgType
	 * @param msgType
	 */
	protected void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	/**
	 * 生成可以发送到用户Client端的文本消息内容，xml格式
	 * @return
	 */
	public abstract String toSendText();
	
	/**
	 * 将微信Server转发过来的xml格式的文本消息解析成java对象
	 * 
	 * @param xmlContent 微信发送过来的消息内容
	 * @return
	 */
	public abstract Document decodeFromXML(String xmlContent);
}
