package com.as1124.consumer.feign.config;

import com.as1124.consumer.feign.FeignRequestInterceptor;
import feign.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 自定义Open Feign的配置
 *
 * @author As-1124
 * @version 1.0
 * @since 2020/8/27 14:25
 */
@Configuration
public class OpenFeignClientConfiguration {

    @Autowired(required = false)
    SpringClientFactory clientFactory;

    /**
     * Logger.Level 的具体级别如下：
     * NONE：不记录任何信息
     * BASIC：仅记录请求方法、URL以及响应状态码和执行时间
     * HEADERS：除了记录 BASIC级别的信息外，还会记录请求和响应的头信息
     * FULL：记录所有请求与响应的明细，包括头信息、请求体、元数据
     *
     * @return Feign日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public FeignRequestInterceptor charlesRequestInterceptor() {
//        return new FeignRequestInterceptor();
//    }
}
