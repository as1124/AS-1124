package com.primeton.mobile.wechat.model.message;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.primeton.mobile.wechat.model.AbstractDataPackage;

/**
 * 图片消息。支持收发。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ImageMessage extends AbstractCommonMessage {

	/**
	 * 图片链接
	 */
	protected String picUrl;
	
	/**
	 * 突变消息的素材ID，可以调用多媒体素材接口拉取数据
	 */
	protected String mediaID;
	
	public ImageMessage() {
		super();
		this.setMsgType("image");
	}
	

	@Override
	public String toSendText() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+getCreateTime()+"</CreateTime>"
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
		 +"<Image><MediaId><![CDATA["+getMediaID()+"]]></MediaId></Image></xml>";
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
			this.setToUser(root.element("ToUserName ").getText());
			this.setFromUser(root.element("FromUserName ").getText());
			long createTime = Long.parseLong(root.element("CreateTime ").getText());
			this.setCreateTime(createTime);
			Element picUrlElement = root.element("PicUrl");
			if(picUrlElement != null){
				this.setPicUrl(picUrlElement.getText());
			}
			Element mediaIDElement = root.element("MediaId");
			if(mediaIDElement != null){
				this.setMediaID(mediaIDElement.getText());
			}
//			Element imageElement = root.element("Image");
//			if(imageElement != null){
//				this.setMediaID(imageElement.element("MediaId").getText());
//			}
			Element msgIDElement = root.element("MsgId");
			if(msgIDElement != null){
				long msgID = Long.parseLong(msgIDElement.getText());
				this.setMsgID(msgID);
			}
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @see #picUrl
	 * @return
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @see #picUrl
	 * @param picUrl
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * @see #mediaID
	 * @return
	 */
	public String getMediaID() {
		return mediaID;
	}

	/**
	 * @see #mediaID
	 * @param mediaID
	 */
	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

}
