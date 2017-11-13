package com.mobile.thirdparty.wechat.events;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mobile.thirdparty.wechat.WechatConstants;

/**
 * 微信公众平台资质认证审核事件模型
 * <p>
 * 请注意：
 * <li>资质认证成功后，公众号就获得了认证相关接口权限，资质认证成功一定发生在名称认证成功之前
 * <li>名称认证成功后，公众号才在微信客户端中获得打勾认证标识
 * </p>
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class AccountVerifyEvent extends AbstractWechatEvent {

	/**
	 * 事件类型：资质认证通过
	 * @see #getEvent()
	 */
	public static final String VERIFY_SUCCESS = "qualification_verify_success";
	
	/**
	 * 事件类型：资质认证失败
	 * @see #getEvent()
	 */
	public static final String VERIFY_FAIL = "qualification_verify_fail";
	
	/**
	 * 事件类型：公众号名称审核通过
	 * @see #getEvent()
	 */
	public static final String VERIFY_NAME_SUCCESS = "naming_verify_success";
	
	/**
	 * 事件类型：公众号名称审核失败
	 * @see #getEvent()
	 */
	public static final String VERIFY_NAME_FAIL = "naming_verify_fail";
	
	/**
	 * 事件类型：年审通知
	 * @see #getEvent()
	 */
	public static final String YEAR_VERIFY_NOTICE = "annual_renew";
	
	/**
	 * 事件类型：资质过期
	 * @see #getEvent()
	 */
	public static final String VERIFY_EXPIRED = "verify_expired";
	
	/**
	 * 资质有效期（审核通过后才有）
	 */
	private long expiredTime = 0;
	
	/**
	 * 认证失败时间
	 */
	private long failTime = -1;
	
	/**
	 * 认证失败原因
	 */
	private String failReason = "";
	
	public AccountVerifyEvent(String xmlContent) {
		super("", xmlContent);
	}

	public AccountVerifyEvent(String event, String xmlContent) {
		super(event, xmlContent);
	}
	
	public Document decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(WechatConstants.CHARSET_UTF8)));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setEvent(root.element("Event").getText());
			
			String expTimeNode = root.element("ExpiredTime").getText();
			if(StringUtils.isNotBlank(expTimeNode))
				this.setExpiredTime(Long.parseLong(expTimeNode));
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

	public long getFailTime() {
		return failTime;
	}

	public void setFailTime(long failTime) {
		this.failTime = failTime;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

}
