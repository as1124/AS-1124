package com.as1124.syx.access;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 请求拦截器
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class OriginalFilter implements Filter {

	private Logger logger = Logger.getLogger(OriginalFilter.class.getName());

	private FilterConfig config;

	public OriginalFilter() {
		// default constructor
	}

	@Override
	public void destroy() {
		config = null;
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hRequest = (HttpServletRequest) request;
		HttpServletResponse hResponse = (HttpServletResponse) response;
		String encoding = config.getInitParameter("encoding");
		if (StringUtils.isNotBlank(encoding)) {
			hRequest.setCharacterEncoding(encoding);
			hResponse.setCharacterEncoding(encoding);
		}

		logger.log(Level.INFO, "[Filtering URI] " + hRequest.getRequestURI());

		// pass the request along the filter chain
		chain.doFilter(hRequest, hResponse);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.config = fConfig;

		Formatter msgFormatter = new Formatter() {

			@Override
			public String format(LogRecord record) {
				String result = "[" + record.getLevel().getName() + "]";
				String time = DateFormatUtils.format(record.getMillis(), "yyyy-MM-dd HH:mm:ss");
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
