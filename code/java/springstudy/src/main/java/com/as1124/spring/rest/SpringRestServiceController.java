package com.as1124.spring.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import com.as1124.spring.web.model.UserInfo;

/**
 * 配置REST有两种：
 * <ul>
 * <li>内容协商机制：{@link ContentNegotiationConfigurer}、{@link ContentNegotiationManager} 来配置 {@link ContentNegotiatingViewResolver}；
 * <li>使用HTTP信息转换器注解: {@link RestController}
 * <ol>
 * <li>{@link ResponseBody} 是一个标志性注解，表明 <code>Controller</code> 处理应当跳过视图层处理，
 * 直接返回数据
 * <li>{@link RequestBody} 用于标识请求Body直接序列化成对象
 * <li>{@link RequestMapping} 注解中的 <code>consumes、produces</code> 用于控制HTTP请求的 ContentType
 * <li>{@link HttpMessageConverter} 用于将不同格式数据在框架规则下序列化成Java对象：JSON（Jackson）、XML（JAXB）；默认MVC框架会通过类路径
 * 法相可用的 HttpMessageConverter 并注册；可以通过{@link WebMvcConfigurer#configureMessageConverters(List)} 覆盖默认行为。
 * 默认的转换器参考 {@link WebMvcConfigurationSupport#ddDefaultHttpMessageConverters}
 * <li>XML交互数据最好设置 <code>Accept</code> 消息头，一面因为其他因素干扰
 * <li>！！！两种方式不能同时使用
 * </ol>
 * </ul>
 * <p>
 * Spring实现 REST HTTP接口示例；
 * {@link RequestMapping#consumes()} 注解中的属性用于控制 Http 请求 input/output 的 Content-Type
 * </p>
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RestController
@RequestMapping(path = "/rest", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class SpringRestServiceController {

	@Autowired(required = false)
	public RequestMappingHandlerAdapter mappingHandler;

	public SpringRestServiceController() {
		// default constructor
	}

	@GetMapping("/converter")
	public List<String> allMessageConverter() {
		List<String> converters = new ArrayList<>();
		if (mappingHandler != null) {
			List<HttpMessageConverter<?>> regiseredConverters = mappingHandler.getMessageConverters();
			for (HttpMessageConverter<?> converter : regiseredConverters) {
				converters.add(converter.getClass().getSimpleName());
			}
		}
		return converters;
	}

	@GetMapping(value = "/user/{uid}", produces = { "application/json;charset=UTF-8" })
	public List<UserInfo> findUser(@PathVariable("uid") int uid) {
		List<UserInfo> list = new ArrayList<>();
		list.add(new UserInfo(uid, "Spring rest 用户"));
		list.add(new UserInfo(23, "Spring REST List"));
		return list;
	}

	@GetMapping(value = "/userxml/one", produces = { MediaType.APPLICATION_XML_VALUE })
	public AuthorXMLPojo findXMLUser(@RequestParam(name = "uid", required = false, defaultValue = "10") int uid) {
		AuthorXMLPojo user = new AuthorXMLPojo("Spring rest 用户的XML表述", "你好, JAXB");
		user.setAge(uid);
		List<BookXMLPojo> bookList = new ArrayList<>();
		bookList.add(new BookXMLPojo("唐诗三百首", "779"));
		bookList.add(new BookXMLPojo("梦里花落知多少", "2001/10-21"));
		user.setBooks(bookList);
		return user;
	}

	/**
	 * XML 序列化 List 的时候需要做特殊处理
	 * 
	 * @return
	 */
	@GetMapping(value = "/userxml/all", produces = { MediaType.APPLICATION_XML_VALUE })
	public AuthorXMLPojoList<AuthorXMLPojo> allXMLUser() {
		List<BookXMLPojo> bookList = new ArrayList<>();
		bookList.add(new BookXMLPojo("唐诗三百首", "779"));
		bookList.add(new BookXMLPojo("梦里花落知多少", "2001/10-21"));
		List<AuthorXMLPojo> list = new ArrayList<>();
		AuthorXMLPojo author = new AuthorXMLPojo("Spring rest 用户的XML表述", "你好, JAXB");
		author.setAge(1234);
		author.setBooks(bookList);
		list.add(author);

		list.add(new AuthorXMLPojo("测试XML传递", "傻狗哈哈哈哈---"));

		return new AuthorXMLPojoList<>(list);
	}

	@PostMapping(value = "/user", produces = { "application/json;charset=UTF-8" })
	public String saveUser(@RequestBody AuthorXMLPojo user) {
		return "用户信息保存成功，name = " + user.getAuthorName();
	}

	@DeleteMapping("/user")
	public boolean deleteOne(@RequestParam(value = "uid", defaultValue = "0") int uid) {
		return true;
	}
}
