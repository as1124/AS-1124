package com.as1124.cloud.provider.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceProviderController {

	@Autowired
	private EurekaClientConfigBean clientConfigBean;

	@GetMapping(path = "/query/eureka-server", produces = "application/json;charset=utf-8")
	public Map<String, String> getEurekaServerURL() {
		return clientConfigBean.getServiceUrl();
	}

	@GetMapping(path = "/hello")
	public String sayHello(@RequestParam("name") String str) {
		return "I got U: " + str;
	}
}
