package com.as1124.wxsapp.access;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 程序常量
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public final class SignAppConstants {

	private SignAppConstants() {
		// default constructor
	}

	public static final String DB_ENVIRONMENT = "development";

	public static final String WXSAPP_ID = "";

	public static final String WX_APPID = "";

	public static final String WX_SECRET = "";

	private static Properties pros = new Properties();

	public static final String getValue(String entryKey, String defaultValue) {
		String value = pros.getProperty(entryKey);
		if (StringUtils.isBlank(entryKey)) {
			return defaultValue;
		} else {
			return value;
		}
	}

	protected static final void reloadFile() throws IOException {
		synchronized (pros) {
			pros.clear();
			try (InputStream inStream = SignAppConstants.class
					.getResourceAsStream("config/wechat/signsapp.properties");) {
				pros.load(inStream);
			} catch (IOException e) {
				// ATTENTION 记录日志
				throw e;
			}
		}
	}
}
