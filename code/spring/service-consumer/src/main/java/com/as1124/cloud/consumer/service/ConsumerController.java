package com.as1124.cloud.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.as1124.cloud.feign.client.IHelloFeignService;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RestController
@RequestMapping
public class ConsumerController {

	@Autowired
	private IHelloFeignService helloFeign;

	@GetMapping(value = "/hello")
	public String searchGithub(@RequestParam("name") String str) {
		return helloFeign.searchRepository(str);
	}
}