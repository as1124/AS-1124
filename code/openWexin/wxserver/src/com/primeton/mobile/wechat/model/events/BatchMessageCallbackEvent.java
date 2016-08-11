package com.primeton.mobile.wechat.model.events;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * 群发消息成功提交后微信返回的回调事件消息。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class BatchMessageCallbackEvent extends AbstractWechatEvent{

	private String msgID;
	
	private String status;
	
	private int totalCount;
	
	private int filterCount;
	
	private int sentCount;
	
	private int errorCount;
	
	public BatchMessageCallbackEvent() {
	}
	
	@Override
	public Document decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes()));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setEvent(root.element("Event").getText());
			this.setMsgID(root.element("MsgID").getText());
			this.setStatus(root.element("Status").getText());
			this.setTotalCount(Integer.parseInt(root.element("TotalCount").getText()));
			this.setFilterCount(Integer.parseInt(root.element("FilterCount").getText()));
			this.setSentCount(Integer.parseInt(root.element("SentCount").getText()));
			this.setErrorCount(Integer.parseInt(root.element("ErrorCount").getText()));
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(int filterCount) {
		this.filterCount = filterCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getSentCount() {
		return sentCount;
	}

	public void setSentCount(int sentCount) {
		this.sentCount = sentCount;
	}

}
