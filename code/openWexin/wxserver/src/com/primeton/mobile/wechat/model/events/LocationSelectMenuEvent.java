package com.primeton.mobile.wechat.model.events;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.primeton.mobile.wechat.IWechatConstants;
import com.primeton.mobile.wechat.model.AbstractDataPackage;

/**
 * 弹出地理位置选择器的事件推送（公众号<code>location_select</code>菜单）。
 *
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class LocationSelectMenuEvent extends AbstractWechatMenuEvent {

	/**
	 * 地理位置纬度, X坐标
	 */
	protected float locationX;
	
	/**
	 * 地理位置经度, Y坐标
	 */
	protected float locationY;
	
	/**
	 * 精度，可理解为精度或者比例尺、越精细的话 scale越高 
	 */
	protected int scale;
	
	/**
	 * 地理位置信息
	 */
	protected String label;
	
	/**
	 * 朋友圈POI的名字，可能为空
	 */
	protected String poiname;
	
	public LocationSelectMenuEvent(String xmlContent) {
		super(xmlContent);
		this.setEvent("location_select");
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
			this.setEventKey(root.element("EventKey").getText());
			Element info = root.element("SendLocationInfo");
			float locationX = Float.parseFloat(info.element("Location_X").getText());
			this.setLocationX(locationX);
			float locationY = Float.parseFloat(info.element("Location_Y").getText());
			this.setLocationY(locationY);
			int scale = Integer.parseInt(info.element("Scale").getText());
			this.setScale(scale);
			this.setLabel(root.element("Label").getText());
			Element poiElement = info.element("Poiname");
			if(poiElement != null)
				this.setPoiname(poiElement.getText());
			
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

	public String getPoiname() {
		return poiname;
	}

	public void setPoiname(String poiname) {
		this.poiname = poiname;
	}


}
