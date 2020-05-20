package com.as1124.cloud.eureka.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RestController
@RequestMapping(path = "/hystrix", produces = "application/json;charset=utf-8")
public class HystrixService {

	/**
	 * 启用熔断处理技术，设置方法处理超时时间为2秒
	 * 
	 * @param uid
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "getUserInfoFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000") })
	@GetMapping(path = "/user/{userid}")
	public Object getUserInfo(@PathVariable("userid") Integer uid) {
		RestTemplate template = new RestTemplate();
		ResponseEntity<Object> response = template.exchange("http://localhost:8080/", HttpMethod.GET, HttpEntity.EMPTY,
			Object.class, uid);
		return response.getBody();
	}

	public Object getUserInfoFallback(Integer uid) {
		Map<String, String> userData = new HashMap<>();
		userData.put("name", "Hystrix熔断");
		userData.put("userID", String.valueOf(uid));

		return userData;
	}

}
