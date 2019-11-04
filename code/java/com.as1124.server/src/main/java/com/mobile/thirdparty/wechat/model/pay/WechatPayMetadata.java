package com.mobile.thirdparty.wechat.model.pay;

import org.apache.commons.lang3.StringUtils;

/**
 * 微信支付
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 * 
 */
public class WechatPayMetadata {

	/**
	 * 配置文件KEY: 支付密钥
	 */
	public static final String KEY_PAY_SECRET = "WECHAT_PAY_SECRET";

	/**
	 * 配置文件KEY: 回调URL
	 */
	public static final String KEY_CALLBACK_URL = "CALLBACK_URL";

	/**
	 * 配置文件KEY: 商户ID
	 */
	public static final String KEY_MCH_ID = "WECHAT_PAY_MCHID";

	/**
	 * 配置文件KEY: 公众号/APP等的appid
	 */
	public static final String KEY_APP_ID = "WECHAT_PAY_APP_ID";

	/**
	 * 支付密钥
	 */
	private String paySecret;

	/**
	 * 商户号
	 */
	private String mchID;

	private String appID;

	/**
	 * 支付结果回调URL
	 */
	private String callbackURL;

	/**
	 * @param appid 公众号或者app的应用ID, 不能空字符
	 * @param mchid 微信商户号, 不能为空字符
	 * @param paySecret 商户支付密钥, 不能空字符
	 * @param callbackURL 支付结果回调URL, 不能携带参数
	 */
	public WechatPayMetadata(String appid, String mchid, String paySecret, String callbackURL) {
		if (StringUtils.isBlank(appid) || StringUtils.isBlank(mchid)
				|| StringUtils.isBlank(paySecret)) {
			System.err.println("商户配置信息错误不存在，请检查数据是否为空");
		} else {
			this.paySecret = paySecret;
			this.mchID = mchid;
			this.appID = appid;
			this.callbackURL = callbackURL;
		}
	}

	/**
	 * @see #paySecret
	 * @return paySecret
	 */
	public String getPaySecret() {
		return paySecret;
	}

	/**
	 * @param paySecret 设置 <code>{@link #paySecret}</code>字段的值
	 * @see #paySecret
	 */
	public void setPaySecret(String paySecret) {
		this.paySecret = paySecret;
	}

	/**
	 * @see #mchID
	 * @return mchID
	 */
	public String getMchID() {
		return mchID;
	}

	/**
	 * @param mchID 设置 <code>{@link #mchID}</code>字段的值
	 * @see #mchID
	 */
	public void setMchID(String mchID) {
		this.mchID = mchID;
	}

	/**
	 * @see #appID
	 * @return appID
	 */
	public String getAppID() {
		return appID;
	}

	/**
	 * @param appID 设置 <code>{@link #appID}</code>字段的值
	 * @see #appID
	 */
	public void setAppID(String appID) {
		this.appID = appID;
	}

	/**
	 * @see #callbackURL
	 * @return callbackURL
	 */
	public String getCallbackURL() {
		return callbackURL;
	}

	/**
	 * @param callbackURL 设置 <code>{@link #callbackURL}</code>字段的值
	 * @see #callbackURL
	 */
	public void setCallbackURL(String callbackURL) {
		this.callbackURL = callbackURL;
	}

}