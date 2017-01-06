package com.primeton.mobile.thirdparty.wechat.model.events;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.primeton.mobile.thirdparty.wechat.IWechatConstants;

/**
 * 微信连WIFI事件
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WIFIConnectedEvent extends AbstractWechatEvent {

	/**
	 * 连网时间
	 */
	private int cnnectTime;
	
	/**
	 * 系统保留字段，固定值
	 */
	private String expireTime;
	
	/**
	 * 系统保留字段，固定值
	 */
	private String vendorId;
	
	/**
	 * 门店ID，即shop_id
	 */
	private String shopID;
	
	/**
	 * 连网的设备无线mac地址，对应bssid
	 */
	private String DeviceNo;

	@Override
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
			
			String strConnectTime = root.element("ConnectTime").getText();
			if(StringUtils.isNotBlank(strConnectTime))
				this.setCnnectTime(Integer.parseInt(strConnectTime));
			this.setExpireTime(root.element("ExpireTime").getText());
			this.setVendorId(root.element("VendorId").getText());
			this.setShopID(root.element("ShopId").getText());
			this.setDeviceNo(root.element("DeviceNo").getText());
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @return the {@link #cnnectTime}
	 */
	public int getCnnectTime() {
		return cnnectTime;
	}

	/**
	 * @param cnnectTime the {@link #cnnectTime} to set
	 */
	public void setCnnectTime(int cnnectTime) {
		this.cnnectTime = cnnectTime;
	}

	/**
	 * @return the {@link #expireTime}
	 */
	public String getExpireTime() {
		return expireTime;
	}

	/**
	 * @param expireTime the {@link #expireTime} to set
	 */
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * @return the {@link #vendorId}
	 */
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the {@link #vendorId} to set
	 */
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * @return the {@link #shopID}
	 */
	public String getShopID() {
		return shopID;
	}

	/**
	 * @param shopID the {@link #shopID} to set
	 */
	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	/**
	 * @return the {@link #deviceNo}
	 */
	public String getDeviceNo() {
		return DeviceNo;
	}

	/**
	 * @param deviceNo the {@link #deviceNo} to set
	 */
	public void setDeviceNo(String deviceNo) {
		DeviceNo = deviceNo;
	}

	
}
