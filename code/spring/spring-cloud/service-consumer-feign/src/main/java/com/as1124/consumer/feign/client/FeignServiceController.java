package com.as1124.consumer.feign.client;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 通过Controller接收外部请求，然后通过Feign-Client转换成三方HTTP服务的调用
 *
 * @author As-1124
 * @version 1.0
 * @since 2020/8/27 15:02
 */
@RestController
@RequestMapping(path = "/feign")
public class FeignServiceController {

    @Autowired
    IHelloFeignClient helloService;

    @Autowired
    IGithubFeignClient githubService;

    @Autowired
    IUserFeignClient userService;

    @Autowired
    IFileFeignClient fileFeignClient;

    ////////////////////////////////////// Feign Client 基础功能 ////////////////////////////////////////

    @GetMapping(path = "/hello")
    public String sayHello() {
        return helloService.sayHello();
    }

    @GetMapping(path = "/search/github")
    public String searchRepository(@RequestParam("keyword") String keyword) {
        return githubService.searchRepository(keyword);
    }

    @GetMapping(path = "/search/github/compress")
    public ResponseEntity<byte[]> searchRepository2(@RequestParam("keyword") String keyword) {
        return githubService.searchRepositoryWithCompressedData(keyword);
    }

    ////////////////////////// 结合服务注册、发现；接口调用传递 POJO 对象 ///////////////////////////////////////
    // ATTENTION 关于Feign的GET/POST 方法进行实体对象交互的测试没有进行

    @GetMapping("/user/{phoneNum}")
    public Object getUser(@PathVariable("phoneNum") String phone) {
        return userService.get(phone);
    }

    public boolean createUser() {
        return false;
    }

    public void updateUser() {

    }

    /////////////////////////// Feign Client 文件操作 ////////////////////////////////////////////
    @PostMapping(path = "/upload")
    public String uploadFile(MultipartFile file) {
        return fileFeignClient.upload(file);
    }

    public void downloadFile(String path) {

    }

}
