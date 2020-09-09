package com.as1124.register.eureka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/appInfo")
public class ApplicationInfoController {

    @Autowired
    private EurekaClientConfigBean clientConfig;

    @GetMapping("/eureka")
    public Map<String, String> getEurekaServer() {
        return clientConfig.getServiceUrl();
    }


}
