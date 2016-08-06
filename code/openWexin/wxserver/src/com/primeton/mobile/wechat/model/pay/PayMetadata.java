package com.primeton.mobile.wechat.model.pay;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


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
	
	protected static PayMetadata instatnce = new PayMetadata();
	
	protected PayMetadata() {
		paySecret = loadValue(KEY_PAY_SECRET);
		mchID = loadValue(KEY_MCH_ID);
		appID = loadValue(KEY_APP_ID);
		callbackURL = loadValue(KEY_CALLBACK_URL);
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

	/**
	 * 获取微信支付元数据配置
	 * 
	 * @return
	 */
	public static PayMetadata getMetadata(){
		if(instatnce == null)
			instatnce = new PayMetadata();
		return instatnce;
	}
	
	/**
	 * 获取配置的key值
	 * 
	 * @return
	 */
	public String loadValue(String key) {
		InputStream in = null;
		//eos-server-runtime.jar的路径
		String path = "";//RuntimeCoreHelper.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String webInfoPath = (new File(path)).getParentFile().getParent();
		File configFile = new File(webInfoPath+"/wechatConfig.properties");
		if(configFile.exists() == false) {
			System.err.println("商户配置信息文件不存在，请检查");
			return "";
		}
		
		Properties wechatConfig = new Properties();
		try {
			in = new FileInputStream(configFile);
			wechatConfig.load(in);
			if(in != null)
				in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return wechatConfig.getProperty(key);
	}
}
