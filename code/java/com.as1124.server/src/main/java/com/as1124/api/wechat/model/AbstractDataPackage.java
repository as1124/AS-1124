package com.as1124.api.wechat.model;

import org.dom4j.Document;

/**
 * 微信通信交互数据包模型（消息/事件）.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public abstract class AbstractDataPackage {

	/**
	 * 接收方微信号(openID/AppID)
	 */
	protected String toUser;
	
	/**
	 * 发送方账号(openID/AppID)
	 */
	protected String fromUser;
	
	/**
	 * 消息创建时间，整型
	 */
	protected long createTime = 0;
	
	/**
	 * 消息类型
	 */
	protected String msgType;
	
	/**
	 * 用于记录消息报文的原始文本数据
	 */
	protected String xmlContent;
	
	/**
	 * @param msgType 消息类别
	 */
	public AbstractDataPackage(String msgType) {
		this.msgType = msgType;
	}

	/**
	 * @param msgType 消息类别
	 * @param xmlContent 消息报文
	 */
	public AbstractDataPackage(String msgType, String xmlContent) {
		this.msgType = msgType;
		this.xmlContent = xmlContent;
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
	 * 获取原始报文数据
	 * @return the {@link #xmlContent}
	 */
	public String getXmlContent() {
		return xmlContent;
	}

	/**
	 * 设置报文信息
	 * @param msgContent the {@link #xmlContent} to set
	 */
	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
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
