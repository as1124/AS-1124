package com.as1124.consumer.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.as1124.consumer.feign.client")
@SpringBootApplication
public class ServiceConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerFeignApplication.class, args);
    }

}
