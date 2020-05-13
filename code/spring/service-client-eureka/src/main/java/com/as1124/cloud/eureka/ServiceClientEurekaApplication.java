package com.as1124.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceClientEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceClientEurekaApplication.class, args);
    }

}
