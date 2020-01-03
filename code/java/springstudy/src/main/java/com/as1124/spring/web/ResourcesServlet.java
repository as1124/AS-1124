package com.as1124.spring.web;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这里通过{@link WebServlet}进行注册的Servlet和通过Spring进行注册的Servlet，
 * 在URL处理上会不会冲突呢 ATTENTION
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@WebServlet("/resource/test/**")
public class ResourcesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ResourcesServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Writer writer = response.getWriter()) {
			writer.append("Served at: ").append(request.getContextPath());
			writer.flush();
		} catch (IOException e) {
			// nothing to do 
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
