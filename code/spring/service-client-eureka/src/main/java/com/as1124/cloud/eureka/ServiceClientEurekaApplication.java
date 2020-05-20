package com.as1124.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 启用熔断器功能，启用 Eureka-Client 功能
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@EnableHystrix
@EnableHystrixDashboard
//@EnableDiscoveryClient
@SpringBootApplication
public class ServiceClientEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceClientEurekaApplication.class, args);
	}

}
