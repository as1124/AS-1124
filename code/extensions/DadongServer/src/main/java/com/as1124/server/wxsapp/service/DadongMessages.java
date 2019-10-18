package com.as1124.server.wxsapp.service;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 提示信息及错误码
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class DadongMessages {

	private static final String BUNDLE_NAME = DadongMessages.class.getName();

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private DadongMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/******************************KEY_ENTRY********************************/

	public static final String USER_1001 = "1001";
	public static final String USER_1002 = "1002";
	public static final String USER_1003 = "1003";
	public static final String USER_1004 = "1004";
	public static final String USER_1005 = "1005";

	public static final String USER_ADDRESS_1101 = "1101";
	public static final String USER_ADDRESS_1102 = "1102";
	public static final String USER_ADDRESS_1103 = "1103";
	public static final String USER_ADDRESS_1104 = "1104";
	public static final String USER_ADDRESS_1105 = "1105";

	public static final String GOODS_2001 = "2001";
	public static final String GOODS_2002 = "2002";
	public static final String GOODS_2003 = "2003";
	public static final String GOODS_2004 = "2004";
	public static final String GOODS_2005 = "2005";

	public static final String GOODS_ORDER_2101 = "2101";
	public static final String GOODS_ORDER_2102 = "2102";
	public static final String GOODS_ORDER_2103 = "2103";
	public static final String GOODS_ORDER_2104 = "2104";
	public static final String GOODS_ORDER_2105 = "2105";

	public static final String SQL_ERROR = "9999";
}
