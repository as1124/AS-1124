package com.as1124.cloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 微服务中服务提供者：Service-启用Provider；熔断器功能，启用 Eureka-Client 功能
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
//@EnableHystrix
//@EnableHystrixDashboard
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderApplication.class, args);
    }

}
