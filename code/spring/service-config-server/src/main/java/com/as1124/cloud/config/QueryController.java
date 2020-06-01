package com.as1124.cloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RestController
@RequestMapping(path = "/query")
public class QueryController {

	@Autowired
	private EurekaClientConfigBean clientConfigBean;

	/**
	 * 获取Eureka-Server信息
	 * 
	 * @return
	 */
	@GetMapping(path = "/eureka-server", produces = "application/json;charset=utf-8")
	public Map<String, String> getEurekaServerURL() {
		return clientConfigBean.getServiceUrl();
	}
}
