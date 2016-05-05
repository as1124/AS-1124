package com.primeton.mobile.wechat.model.message;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 链接消息。仅接收。
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class LinkMessage extends AbstractCommonMessage {

	/**
	 * 消息标题
	 */
	protected String title;
	
	/**
	 * 消息描述
	 */
	protected String description;
	
	/**
	 * 链接的url
	 */
	protected String url;
	
	public LinkMessage() {
		super();
		this.setMsgType("link");
	}
	
	/**
	 * {@link AbstractMessage#toXML()}
	 * @return
	 */
	public String toXML() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+getCreateTime()+"</CreateTime>"
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
		 +"<Title><![CDATA["+getTitle()+"]]></Title>"
		 +"<Description><![CDATA["+getDescription()+"]]></Description>"
		 +"<Url><![CDATA["+getUrl()+"]]></Url>"
		 +"<MsgId>"+getMsgID()+"</MsgId></xml>";
		return result;
	}

	@Override
	public String toSendText() {
		return null;
	}
	
	/**
	 * {@link AbstractMessage#decodeFromXML(String)}
	 * @param xmlContent
	 * @return
	 */
	public void decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes()));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setTitle(root.element("Title").getText());
			this.setDescription(root.element("Description").getText());
			this.setUrl(root.element("Url").getText());
			long msgID = Long.parseLong(root.element("MsgId").getText());
			this.setMsgID(msgID);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see #title
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @see #title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @see #description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @see #description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @see #url
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @see #url
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
