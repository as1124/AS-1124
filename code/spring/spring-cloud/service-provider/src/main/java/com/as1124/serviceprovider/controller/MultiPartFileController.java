package com.as1124.serviceprovider.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.MediaType;

/**
 * @author As-1124
 * @version 1.0
 * @since 2020/8/20 20:15
 */
@RestController
@RequestMapping(path = "/file")
public class MultiPartFileController {

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA)
    public String getEurekaServer(MultipartFile file) {
        return file.getOriginalFilename() + "=>" + file.getSize();
    }


}
