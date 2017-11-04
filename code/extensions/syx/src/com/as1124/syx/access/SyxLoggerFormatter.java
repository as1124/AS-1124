package com.as1124.syx.access;

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

public class SyxLoggerFormatter {

	static Logger logger = Logger.getLogger(SyxLoggerFormatter.class.getName());

	static boolean isInited = false;

	private SyxLoggerFormatter() {
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

			@Override
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
