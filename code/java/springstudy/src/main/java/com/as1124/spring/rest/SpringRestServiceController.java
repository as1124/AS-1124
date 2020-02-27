package com.as1124.spring.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import com.as1124.spring.rest.pojo.AuthorXMLPojo;
import com.as1124.spring.rest.pojo.AuthorXMLPojoList;
import com.as1124.spring.rest.pojo.BookXMLPojo;
import com.as1124.spring.web.model.UserInfo;

/**
 * 一、配置REST有两种：
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
 * 二、Spring实现 REST 常用注解、类
 * <ul>
 * <li>{@link ExceptionHandler}
 * <li>{@link ResponseEntity}
 * </ul>
 * 三、Spring Client调用 REST 接口
 * <ul>
 * <li>{@link RestTemplate}
 * 
 * </ul>
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

	/**
	 * 获取当前Spring-Context中所有注册的 HttpMessageConverter
	 * 
	 * @return
	 */
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

	/**
	 * 测试JSON数据序列化
	 * 
	 * @param uid
	 * @return
	 */
	@GetMapping(value = "/user/{uid}", produces = { "application/json;charset=UTF-8" })
	public List<UserInfo> findUser(@PathVariable("uid") int uid) {
		List<UserInfo> list = new ArrayList<>();
		list.add(new UserInfo(uid, "Spring rest 用户"));
		list.add(new UserInfo(23, "Spring REST List"));
		return list;
	}

	/**
	 * 测试XML数据序列化
	 * 
	 * @param uid
	 * @return
	 */
	@GetMapping(value = "/userxml/one", produces = { MediaType.APPLICATION_XML_VALUE })
	public AuthorXMLPojo findXMLUser(@RequestParam(name = "uid", required = false, defaultValue = "10") int uid) {
		AuthorXMLPojo user = new AuthorXMLPojo("Spring rest 用户的XML表述", "你好, JAXB");
		user.setAge(uid);
		List<BookXMLPojo> bookList = new ArrayList<>();
		bookList.add(new BookXMLPojo("唐诗三百首", "779"));
		bookList.add(new BookXMLPojo("梦里花落知多少", "2001/10/21"));
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

	/**
	 * 测试 ResponseEntity 的使用：支持HTTP状态码
	 * 
	 * @return
	 */
	@GetMapping(path = "/god", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> findGod(@RequestParam("type") String type) {
		if (StringUtils.isEmpty(type)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.ok(new UserInfo("酒肉穿肠过，佛祖心中留", "In my heart!"));
		}
	}

	/**
	 * Spring 异常捕获处理机制, 当有异常发生时会查找对应当 Handler 然后 invoker 到对应到方法上做处理
	 * 
	 */
	@GetMapping(path = "/exception", produces = { "application/json;charset=UTF-8" })
	public String testSpringException() {
		throw new RuntimeException("测试 Spring HTTP 异常捕获");
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> handleException(Exception ex) {
		return ResponseEntity.status(777)
				.body(SpringRestServiceController.class.getName() + " 发生异常 == " + ex.getMessage());
	}

	@PostMapping(value = "/user", produces = { "application/json;charset=UTF-8" })
	public String saveUser(@RequestBody AuthorXMLPojo user) {
		return "用户信息保存成功，name = " + user.getAuthorName();
	}

	@DeleteMapping("/user")
	public ResponseEntity<Boolean> deleteOne(@RequestParam(value = "uid", defaultValue = "0") int uid) {
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}
}
