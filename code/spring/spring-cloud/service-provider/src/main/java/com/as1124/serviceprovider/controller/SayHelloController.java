package com.as1124.serviceprovider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author As-1124
 * @version 1.0
 * @since 2020/8/20 20:15
 */
@RestController
public class SayHelloController {

    @GetMapping("/hello")
    public String getEurekaServer() {
        return "Hey, the beautiful and elegant girl, so what's your name !";
    }


}
