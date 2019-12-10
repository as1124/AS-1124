package com.as1124.api.wechat.model.message;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.as1124.api.wechat.WechatConstants;
import com.as1124.api.wechat.model.AbstractDataPackage;

/**
 * 地理位置消息
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class LocationMessage extends AbstractMessage {

	/**
	 * 地理位置纬度
	 */
	protected float locationX;
	
	/**
	 * 地理位置经度
	 */
	protected float locationY;
	
	/**
	 * 地图缩放大小
	 */
	protected int scale;
	
	/**
	 * 地理位置信息
	 */
	protected String label;
	
	public LocationMessage(String xmlContent) {
		super("location", xmlContent);
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
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(WechatConstants.CHARSET_UTF8)));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			float locationX = Float.parseFloat(root.element("Location_X").getText());
			this.setLocationX(locationX);
			float locationY = Float.parseFloat(root.element("Location_Y").getText());
			this.setLocationY(locationY);
			int scale = Integer.parseInt(root.element("Scale").getText());
			this.setScale(scale);
			this.setLabel(root.element("Label").getText());
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
	 * @see #locationX
	 * @return
	 */
	public float getLocationX() {
		return locationX;
	}
	
	/**
	 * @see #locationX
	 * @param locationX
	 */
	public void setLocationX(float locationX) {
		this.locationX = locationX;
	}
	
	/**
	 * @see #locationY
	 * @return
	 */
	public float getLocationY() {
		return locationY;
	}
	
	/**
	 * @see #locationY
	 * @param locationY
	 */
	public void setLocationY(float locationY) {
		this.locationY = locationY;
	}
	
	/**
	 * @see #scale
	 * @return
	 */
	public int getScale() {
		return scale;
	}
	
	/**
	 * @see #scale
	 * @param scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	/**
	 * @see #label
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * @see #label
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

}
