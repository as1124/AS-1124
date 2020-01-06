package com.as1124.spring.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.as1124.spring.web.model.UserInfo;

/**
 * {@link GetMapping} 等价于 {@link RequestMapping} 的GET调用。<br/>
 * <p>
 * 需要注意一下几点：
 * <ul>
 * <li>请求路径匹配 {@link RequestMapping}
 * <li>如何获取并处理请求参数：{@link RequestParam}，{@link PathVariable}，
 * <li>如何返回数据：{@link Model}, {@link ModelAndView} 或者直接返回;  <code>String</code> 类型的方法返回值默认作为视图名称进行解析
 * </ul>
 * </p>
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Controller
@RequestMapping(path = { "/spring" })
public class SpringController {

	/**
	 *  处理路径 <code>/</code> 的GET请求，返回视图名为 <code>home</code>
	 *  
	 * @param data2Return 和前端进行数据交互时的数据通道
	 * @return 请求路径对应的视图{@link View}名称
	 */
	@RequestMapping(path = "/home", method = { RequestMethod.GET })
	public String homePage() {
		// 返回视图名称，翻译后  =>  /views/home/home.jsp
		return "/home/home.jsp";
	}

	@GetMapping("/user")
	public List<String> queryParams(@RequestParam(value = "ageFrom", defaultValue = "0") int ageFrom) {
		// 查询参数注解
		List<String> names = new ArrayList<>();
		names.add("Bob");
		names.add("Jack");
		return names;
	}

	@GetMapping("/user/{userid}")
	public String pathParams(@PathVariable(value = "userid") String userid) {
		// 路径参数注解
		return "failure";
	}

	@PostMapping("/user/register")
	public String formParams(UserInfo spittle) {
		// 表单参数转对象
		return "success";
	}

	/**
	 * 表单校验：前提是校验对象的待校验属性、字段有相应的校验注解；<code>javax.validation.constraints</code>
	 * <br/>参考 {@link UserInfo}
	 * @param spittle
	 * @param errors 校验后的结果
	 * @return
	 */
	@PostMapping(value = "/user/registx", consumes = { "application/x-www-form-urlencoded;charset=UTF-8",
			"application/json;charset=UTF-8" })
	public String formValidation(@Valid UserInfo spittle, Errors errors) {
		//ATTENTION 为什么一直校验都是没错呢
		// 表单校验
		if (errors.hasErrors()) {
			return "fillForm";
		} else {
			// 请求重定向
			return "redirect:/spring/user/" + spittle.getId();
		}
	}

	/**
	 * 测试如何与界面进行数据交互
	 * @return
	 */
	@GetMapping("/demo1")
	public String testReturnDemo11(Model model2Return) {
		UserInfo spi = new UserInfo("ABCSS-1124", "123321");
		model2Return.addAttribute("result", spi);
		return "/home/home.jsp";
	}

	@GetMapping("/demo2")
	public ModelAndView testReturnDemo2() {
		ModelAndView data2Return = new ModelAndView();
		data2Return.setViewName("/home/home.jsp");
		data2Return.addObject("result", "This is result!!");
		return data2Return;
	}

	@GetMapping("/demo3")
	public String testReturnDemo3(Map<String, String> data2Return) {
		data2Return.put("result", "This is result2222!!");
		return "/home/home.jsp";
	}

	@GetMapping("/demo4")
	public String testReturnDemo4(HttpSession session) {
		session.setAttribute("result", "This is result3333!!");
		return "/home/home.jsp";
	}

	@GetMapping("/demo5")
	public String testReturnDemo5(HttpServletRequest request) {
		request.setAttribute("result", "This is result555555!!");
		return "/home/home.jsp";
	}

	@GetMapping(path = "/*")
	public String defaultPage() {
		return "newIndex.html";
	}
}
