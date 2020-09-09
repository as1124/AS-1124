package com.as1124.consumer.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @author As-1124
 * @since 2020/8/28 17:03
 */
@FeignClient(name = "user-feign-client", url = "http://service-provider", path = "/user")
public interface IUserFeignClient {

    @GetMapping("/info/{phoneNum}")
    Map<String, String> get(@PathVariable("phoneNum") String phoneNum);

    @PostMapping("/add")
    boolean createUser(Map<String, String> account);

    @PostMapping("/update")
    Map<String, String> updateUser(Map<String, String> newAccountInfo);
}
