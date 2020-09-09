package com.as1124.consumer.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 不设置 {@link FeignClient#url()}, 看一下默认调用的
 * URL是如何组装的
 * <p>
 * <li>如果FeignClient 没有明确指定了host信息，则使用（通过url或者path进行指定）</li>
 * <li>如果没有指定明确的host信息，则使用name作为服务名称到注册中心进行服务发现，然后调用微服务暴露的HTTP接口</li>
 * </p>
 *
 * @author As-1124
 * @version 1.0
 * @since 2020/8/31 10:00
 */
@FeignClient(name = "service-provider")
public interface IHelloFeignClient {

    @GetMapping(path = "/hello")
    String sayHello();
}
