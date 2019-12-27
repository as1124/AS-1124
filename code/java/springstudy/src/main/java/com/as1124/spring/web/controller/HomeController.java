package com.as1124.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * {@link GetMapping} 等价于 {@link RequestMapping} 的GET调用
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Controller
public class HomeController {

	/**
	 *  处理路径 <code>/</code> 的GET请求，返回视图名为 <code>home</code>
	 * @return
	 */
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public String gotoHome() {
		// 返回视图名称，翻译后 => WEB-INF/views/home/home.jsp
		return "home";
	}

	@GetMapping(path = "/")
	public String defaultPage() {
		return "index";
	}
}
