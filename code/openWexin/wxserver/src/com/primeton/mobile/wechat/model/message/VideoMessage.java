package com.primeton.mobile.wechat.model.message;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.primeton.mobile.wechat.IWechatConstants;
import com.primeton.mobile.wechat.model.AbstractDataPackage;

/**
 * 视频消息
 * @author huangjw(mailto:huangjw@primeton.com)
 * @msgType <code>vedio、shortvideo</code>
 */
public class VideoMessage extends AbstractCommonMessage {

	/**
	 * 视频消息的标题，发送视频消息时指定
	 */
	private String title;
	
	/**
	 * 视频消息的描述，发送视频消息时指定
	 */
	private String description;
	
	/**
	 * 视频消息媒体id，可以调用多媒体素材接口拉取数据。
	 */
	protected String mediaID;
	
	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	protected String thumbMediaID;
	
	public VideoMessage() {
		super();
	}

	@Override
	public String toSendText() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
		 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+getCreateTime()+"</CreateTime>"
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
		 +"<Video><MediaId><![CDATA["+getMediaID()+"]]></MediaId>"
		 +"<Title><![CDATA["+getTitle()+"]]></Title>"
		 +"<Description><![CDATA["+getDescription()+"]]></Description></Video></xml>";
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
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(IWechatConstants.DEFAULT_CHARSET)));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setMsgType(root.element("MsgType").getText());
			Element mediaIDElement = root.element("MediaId");
			if(mediaIDElement != null){
				this.setMediaID(mediaIDElement.getText());
			}
//			Element videoElement = root.element("Video");
//			if(videoElement != null){
//				this.setMediaID(videoElement.element("MediaId").getText());
//				if(videoElement.element("Title") != null) {
//					this.setTitle(videoElement.element("Title").getText());
//				}
//				if(videoElement.element("Description") != null) {
//					this.setDescription(videoElement.element("Description").getText());
//				}
//			}
			Element thumbElement = root.element("ThumbMediaId");
			if(thumbElement != null){
				this.setThumbMediaID(thumbElement.getText());
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

	/**
	 * @see #thumbMediaID
	 * @return
	 */
	public String getThumbMediaID() {
		return thumbMediaID;
	}

	/**
	 * @see #thumbMediaID
	 * @param thumbMediaID
	 */
	public void setThumbMediaID(String thumbMediaID) {
		this.thumbMediaID = thumbMediaID;
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

}
