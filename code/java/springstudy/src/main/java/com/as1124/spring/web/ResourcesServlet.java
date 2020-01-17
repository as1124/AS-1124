package com.as1124.spring.web;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.WebApplicationInitializer;

/**
 * 通过 @{@link WebServlet} 进行注册的Servlet和在Spring 的 
 * {@linkplain WebApplicationInitializer#onStartup(javax.servlet.ServletContext)} 进行注册的Servlet，
 * 在URI处理上的策略：<pre><strong>
 * 取并集，两种方法配置的  <code>servlet-mapping</code> 都能正常访问
 * </strong></pre>
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@WebServlet("/resource/test/*")
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
			writer.append("Served at: ").append(request.getRequestURI());
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
