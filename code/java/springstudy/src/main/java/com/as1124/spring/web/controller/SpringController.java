package com.as1124.spring.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

/**
 * {@link GetMapping} 等价于 {@link RequestMapping} 的GET调用。<br/>
 * <p>
 * 需要注意一下几点：
 * <ul>
 * <li>请求路径匹配 {@link RequestMapping}
 * <li>如何获取并处理请求参数：{@link RequestParam}，{@link PathVariable}，
 * <li>如何返回数据：{@link Model}, 或者直接返回
 * </ul>
 * </p>
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Controller
@RequestMapping(path = { "/" })
public class SpringController {

	/**
	 *  处理路径 <code>/</code> 的GET请求，返回视图名为 <code>home</code>
	 *  
	 * @param data2Return 和前端进行数据交互时的数据通道
	 * @return 请求路径对应的视图{@link View}名称
	 */
	@RequestMapping(path = "/home", method = { RequestMethod.GET })
	public String homePage(Model data2Return) {
		// 处理需要返回到前端界面的数据
		data2Return.addAttribute("names", Arrays.asList(new String[] { "Hello ", " Spring ", "World!!!!" }));

		// 返回视图名称，翻译后  =>  /views/home/home.jsp
		return "/home/home";
	}

	@GetMapping("/users")
	public List<String> getNames(@RequestParam(value = "ageFrom", defaultValue = "0") int ageFrom) {
		List<String> names = new ArrayList<>();
		names.add("Bob");
		names.add("Jack");
		return names;
	}

	@GetMapping("/users/{userid}")
	public String getAddress(@PathVariable(value = "userid") String userid) {
		return "中国上海_zh_CN";
	}

	@GetMapping(path = "/*")
	public String defaultPage() {
		return "newIndex";
	}
}
