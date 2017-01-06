package com.primeton.mobile.thirdparty.wechat;

/**
 * 微信公众号服务后台常量定义
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public interface IWechatConstants {

	public static final String DEFAULT_CHARSET = "UTF-8";
	
	public static final String CONTENT_TYPE_JSON = "text/json";
	
	public static final String CONTENT_TYPE_XML = "text/xml";
	
	public static final String ERROR_CODE = "errcode";
	
	public static final String ERROR_MSG = "errmsg";
	
	public static final String RETURN_CODE_SUCCESS = "0";
	
	public static final String RETURN_CODE_SYS_BUSY = "-1";
	
	public static final String RETURN_CODE_NO_MENUS = "46003";
	
	public static final String KEY_APP_ID = "appid";
	
	public static final String KEY_APP_SECRET = "secret";
	
	public static final String KEY_ACCESS_TOKEN = "access_token";
	
	public static String MSG_TAG = "[WECHAT EXCEPTION] ";
}
