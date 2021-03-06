package com.as1124.cloud.eureka.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping(path = "/eureka-server", produces = "application/json;charset=utf-8")
	public Map<String, String> getEurekaServerURL() {
		return clientConfigBean.getServiceUrl();
	}
}
