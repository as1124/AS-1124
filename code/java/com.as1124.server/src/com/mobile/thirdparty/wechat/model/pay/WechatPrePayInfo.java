package com.mobile.thirdparty.wechat.model.pay;


/**
 * 微信支付，预订单信息模型
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatPrePayInfo extends AbstractWechatPayModel{
	
	/**
	 * 设备号
	 */
	String device_info;
	
	/**
	 * 随机字符串
	 */
	String nonce_str;

	/**
	 * 交易类型, <code>JSAPI | NATIVE | APP</code>
	 */
	String trade_type;
	
	/**
	 * 预支付订单号
	 */
	String prepay_id;
	
	String code_url;
	
	public WechatPrePayInfo() {
		
	}
	
	/**
	 * @see #device_info
	 * @return device_info
	 */
	public String getDevice_info() {
		return device_info;
	}

	/**
	 * @param device_info 设置 <code>{@link #device_info}</code>字段的值
	 * @see #device_info
	 */
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	/**
	 * @see #nonce_str
	 * @return nonce_str
	 */
	public String getNonce_str() {
		return nonce_str;
	}

	/**
	 * @param nonce_str 设置 <code>{@link #nonce_str}</code>字段的值
	 * @see #nonce_str
	 */
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	/**
	 * @see #trade_type
	 * @return trade_type
	 */
	public String getTrade_type() {
		return trade_type;
	}

	/**
	 * @param trade_type 设置 <code>{@link #trade_type}</code>字段的值
	 * @see #trade_type
	 */
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	/**
	 * @see #prepay_id
	 * @return prepay_id
	 */
	public String getPrepay_id() {
		return prepay_id;
	}

	/**
	 * @param prepay_id 设置 <code>{@link #prepay_id}</code>字段的值
	 * @see #prepay_id
	 */
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	/**
	 * @see #code_url
	 * @return code_url
	 */
	public String getCode_url() {
		return code_url;
	}

	/**
	 * @param code_url 设置 <code>{@link #code_url}</code>字段的值
	 * @see #code_url
	 */
	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}
	
}
