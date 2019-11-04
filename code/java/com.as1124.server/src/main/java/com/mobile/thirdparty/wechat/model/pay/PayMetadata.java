package com.mobile.thirdparty.wechat.model.pay;

import org.apache.commons.lang3.StringUtils;

/**
 * 微信支付
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 * 
 */
public class PayMetadata {

	/**
	 * 支付密钥
	 */
	public static final String KEY_PAY_SECRET = "WECHAT_PAY_SECRET";

	/**
	 * server 回调URL
	 */
	public static final String KEY_CALLBACK_URL = "CALLBACK_URL";

	/**
	 * 商户ID
	 */
	public static final String KEY_MCH_ID = "WECHAT_PAY_MCHID";

	/**
	 * 应用ID
	 */
	public static final String KEY_APP_ID = "WECHAT_PAY_APP_ID";

	private String paySecret;

	private String mchID;

	private String appID;

	private String callbackURL;

	/**
	 * @param appid 支付的应用ID, 不能空字符
	 * @param mchid 商户号, 不能为空字符
	 * @param paySecret 商户支付密钥, 不能空字符
	 * @param callbackURL 支付回调URL
	 */
	public PayMetadata(String appid, String mchid, String paySecret,
			String callbackURL) {
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

	public String getPaySecret() {
		return paySecret;
	}

	public void setPaySecret(String paySecret) {
		this.paySecret = paySecret;
	}

	public String getMchID() {
		return mchID;
	}

	public void setMchID(String mchID) {
		this.mchID = mchID;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getCallbackURL() {
		return callbackURL;
	}

	public void setCallbackURL(String callbackURL) {
		this.callbackURL = callbackURL;
	}

}
