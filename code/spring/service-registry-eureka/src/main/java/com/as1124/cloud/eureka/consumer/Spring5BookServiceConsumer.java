package com.as1124.cloud.eureka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/springcloud/consume", produces = "application/json;charset=utf-8")
public class Spring5BookServiceConsumer {

    @LoadBalanced
    @Autowired
    private RestTemplate template;

    @GetMapping(path = "/book/{bookID}")
    public Object consumerBookService(@PathVariable("bookID") Integer bookID) {
        template.getClientHttpRequestInitializers().add((ClientHttpRequest request) -> {
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        });
        // Eureka 注册的服务名默认都是大写的
        Object result = template.getForObject("http://SPRING5/book/{bookID}", Object.class, bookID);
        System.out.println(result);
        return result;
    }
}
