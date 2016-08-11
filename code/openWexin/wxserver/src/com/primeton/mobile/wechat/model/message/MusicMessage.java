package com.primeton.mobile.wechat.model.message;

import java.io.ByteArrayInputStream;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.primeton.mobile.wechat.model.AbstractDataPackage;


/**
 * 音乐消息，仅用于回复时。
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class MusicMessage extends AbstractCommonMessage {

	/**
	 * 音乐标题
	 */
	protected String title;
	
	/**
	 * 音乐描述
	 */
	protected String description;
	
	/**
	 * 音乐链接
	 */
	protected String musicUrl;
	
	/**
	 * 高质量音乐链接
	 */
	protected String hqMusicUrl;
	
	/**
	 * 缩略图
	 */
	protected String thumbMediaID;
	
	public MusicMessage() {
	}

	@Override
	public String toSendText() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+getCreateTime()+"</CreateTime>"
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
		 +"<Music>";
		if(StringUtils.isNotBlank(this.title))
			result = result + "<Title><![CDATA["+getTitle()+"]]></Title>";
		if(StringUtils.isNotBlank(this.description))
			result = result + "<Description><![CDATA["+getDescription()+"]]></Description>";
		if(StringUtils.isNotBlank(this.musicUrl))
			result = result + "<MusicUrl><![CDATA["+getMusicUrl()+"]]></MusicUrl>";
		if(StringUtils.isNotBlank(this.hqMusicUrl))
			result = result + "<HQMusicUrl><![CDATA["+getHqMusicUrl()+"]]></HQMusicUrl>";
		if(StringUtils.isNotBlank(this.thumbMediaID))
			result = result + "<ThumbMediaId>"+getThumbMediaID()+"</ThumbMediaId></Music></xml>";
		return result;
	}

	/**
	 * {@link AbstractDataPackage#decodeFromXML(String)}
	 * @param xmlContent
	 * @return
	 */
	public Document decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes()));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			int createTime = Integer.parseInt(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setMsgType(root.element("MsgType").getText());
			Element element = root.element("Music");
			if(element.element("Title") != null)
				this.setTitle(element.element("Title").getText());
			if(element.element("Description") != null)
				this.setDescription(element.element("Description").getText());
			if(element.element("MusicUrl") != null)
				this.setMusicUrl(element.element("MusicUrl").getText());
			if(element.element("HQMusicUrl") != null)
				this.setHqMusicUrl(element.element("HQMusicUrl").getText());
			if(element.element("ThumbMediaId") != null)
				this.setThumbMediaID(root.element("ThumbMediaId").getText());
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public String getThumbMediaID() {
		return thumbMediaID;
	}

	public void setThumbMediaID(String thumbMediaID) {
		this.thumbMediaID = thumbMediaID;
	}
	
}
