/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年9月28日
 *******************************************************************************/

package com.java.core.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 日志记录类
 *
 * @author huangjw (mailto:as1124huang@gmail.com)
 */

public class JavaCoreLogger {

	static Logger logger = Logger.getLogger("com.java.volume2");

	static boolean isInited = false;

	private JavaCoreLogger() {
	}

	public static void log(Level serverity, String message) {
		if (!isInited) {
			init();
			isInited = true;
		}
		logger.log(serverity, message);
	}

	public static void log(Level serverity, String message, Throwable ex) {
		if (!isInited) {
			init();
			isInited = true;
		}
		logger.log(serverity, message, ex);
	}

	private static void init() {
		Formatter msgFormatter = new Formatter() {

			public String format(LogRecord record) {
				String result = "[" + record.getLevel().getName() + "]";
				Date now = new Date(record.getMillis());
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
				if (record.getThrown() != null) {
					record.getThrown().printStackTrace();
				}
				return result + "[" + time + "] " + record.getMessage() + "\r\n";
			}
		};
		Handler logHandler = new ConsoleHandler();
		logHandler.setFormatter(msgFormatter);
		logger.addHandler(logHandler);
		logger.setUseParentHandlers(false);
	}
}
