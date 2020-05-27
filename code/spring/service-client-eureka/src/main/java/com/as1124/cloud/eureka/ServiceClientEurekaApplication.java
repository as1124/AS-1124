package com.as1124.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启用熔断器功能，启用 Eureka-Client 功能
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
//@EnableHystrix
//@EnableHystrixDashboard
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceClientEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceClientEurekaApplication.class, args);
	}

	public static void printMax(int[] a, int[] b, int k) {
		// a代表项目成本，b代表营收额，k代表本金
		int max = 0;
		for (int i = 0; i < a.length; i++) {

			for (int j = i + 1; j < a.length; j++) {

			}
		}
	}

}
