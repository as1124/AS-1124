package com.mobile.thirdparty.wechat.message;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mobile.thirdparty.wechat.IWechatConstants;
import com.mobile.thirdparty.wechat.model.AbstractDataPackage;

/**
 * 链接消息。仅接收。
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class LinkMessage extends AbstractMessage {

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
	
	public LinkMessage(String xmlContent) {
		super("link", xmlContent);
	}

	public String toSendText() {
		return null;
	}
	
	/**
	 * {@link AbstractDataPackage#decodeFromXML(String)}
	 * @param xmlContent
	 * @return
	 */
	public Document decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(IWechatConstants.DEFAULT_CHARSET)));
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
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
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
