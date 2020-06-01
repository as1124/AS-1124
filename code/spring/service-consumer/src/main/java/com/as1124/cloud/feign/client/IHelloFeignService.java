package com.as1124.cloud.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO 此处填写 class 信息
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@FeignClient(name = "hello-feign", url = "https://api.github.com")
public interface IHelloFeignService {

	@GetMapping(value = "/search/repositories")
	String searchRepository(@RequestParam("q") String queryStr);
}
