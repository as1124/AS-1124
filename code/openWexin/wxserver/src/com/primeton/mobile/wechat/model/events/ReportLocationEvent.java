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
 * <strong>上报地理位置事件</strong><br>
 * 用户同意上报地理位置后，每次进入公众号会话时，都会在<strong>进入时</strong>上报地理位置，或在<strong>进入会话后每5秒上报</strong>一次地理位置，
 * 公众号可以在公众平台网站中修改以上设置。上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL。
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ReportLocationEvent extends AbstractWechatEvent {

	/**
	 * 地理位置纬度
	 */
	protected float latitude;
	
	/**
	 * 地理位置经度
	 */
	protected float longitude;
	
	/**
	 * 地理位置精度
	 */
	protected float precision;
	
	public ReportLocationEvent() {
		super();
		this.setEvent("LOCATION");
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
			this.setEvent(root.element("Event").getText());
			float latitude = Float.parseFloat(root.element("Latitude").getText());
			float longitude = Float.parseFloat(root.element("Longitude").getText());
			float precision = Float.parseFloat(root.element("Precision").getText());
			this.setLatitude(latitude);
			this.setLongitude(longitude);
			this.setPrecision(precision);
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getPrecision() {
		return precision;
	}

	public void setPrecision(float precision) {
		this.precision = precision;
	}
	
}
