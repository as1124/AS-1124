package com.mobile.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 日志记录类
 *
 * @author huangjw(mailto:as1124huang@gmail.com)
 */
public class LoggerUtil {

	static boolean isInited = false;

	private LoggerUtil() {
	}

	public static Logger getLogger(Class<?> clazz) {
		Logger logger = Logger.getLogger(clazz.getName());
		init(logger);

		return logger;
	}

	private static void init(Logger logger) {
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
