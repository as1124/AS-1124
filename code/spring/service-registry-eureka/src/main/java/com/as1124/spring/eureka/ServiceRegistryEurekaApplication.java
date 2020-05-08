package com.as1124.spring.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 使用Netflix Eureka作为微服务的注册中心；
 * 可以启动多个debugConfiguration => 设置不同的port启动模拟多注册中心
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
@EnableEurekaServer
@SpringBootApplication
public class ServiceRegistryEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryEurekaApplication.class, args);
    }

}
