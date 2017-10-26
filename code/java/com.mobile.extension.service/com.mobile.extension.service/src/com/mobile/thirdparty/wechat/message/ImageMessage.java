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
 * 图片消息。支持收发。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ImageMessage extends AbstractMessage {

	/**
	 * 图片链接
	 */
	protected String picUrl;
	
	/**
	 * 突变消息的素材ID，可以调用多媒体素材接口拉取数据
	 */
	protected String mediaID;
	
	public ImageMessage() {
		super("image");
	}
	
	public ImageMessage(String xmlContent) {
		super("image", xmlContent);
	}
	
	public String toSendText() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+this.getSentTime()+"</CreateTime>"
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
			Document document = reader.read(new ByteArrayInputStream(
					xmlContent.getBytes(IWechatConstants.DEFAULT_CHARSET)));
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
			Element msgIDElement = root.element("MsgId");
			if(msgIDElement != null){
				long msgID = Long.parseLong(msgIDElement.getText());
				this.setMsgID(msgID);
			}
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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
