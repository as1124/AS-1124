package com.as1124.consumer.feign.client;

import com.as1124.consumer.feign.config.OpenFeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Open Feign Client的使用; 默认是REST接口，所以不用声明为<code> RestController</code>
 *
 * @author As-1124
 * @version 1.0
 * @since 2020/8/27 11:42
 */
@FeignClient(name = "github-feign-client", url = "https://api.github.com",
        configuration = OpenFeignClientConfiguration.class)
public interface IGithubFeignClient {

    /**
     * 根据关键词查找Github上的仓库
     *
     * @param keyword 关键词
     * @return 结果
     */
    @GetMapping(path = "/search/repositories")
    String searchRepository(@RequestParam("q") String keyword);

    @GetMapping(path = "/search/repositories")
    ResponseEntity<byte[]> searchRepositoryWithCompressedData(@RequestParam("q") String keyword);
}
