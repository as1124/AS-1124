package com.as1124.consumer.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.File;

/**
 * Feign Client文件操作
 *
 * @author As-1124
 * @since 2020/8/31 15:51
 */
@FeignClient(name = "http://service-provider/file")
public interface IFileFeignClient {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA, path = "/upload")
    String upload(@RequestPart("file") MultipartFile file);

    @GetMapping(path = "/download")
    File download(@QueryParam("name") String fileName);
}
