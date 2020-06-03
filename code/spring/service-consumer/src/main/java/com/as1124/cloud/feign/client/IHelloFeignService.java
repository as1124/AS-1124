package com.as1124.cloud.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hello-feign", url = "http://localhost:8081", configuration = FeignClienConfiguration.class)
public interface IHelloFeignService {

	@GetMapping(path = "/hello")
	String searchRepository(@RequestParam("name") String str);
}
