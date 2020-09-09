# Spring Open Feign

###问题
1. 重启第一次调用 => java.net.SocketException: Connection reset

### 配置Feign 发起Http请求的底层框
- 调用过程：JDK动态代理接口形成Target => 组装RequestTemplate => 提交Request给Client调用并获取结果
- 默认使用的是 URLConnection 进行Http请求的处理

- 选择Apache HttpClient作为HTTP调用的Client
    - 配置 feign.httpclient.enabled=true
    - classpath 引入 dependency(io.github.openfeign,feign-httpclient,)
    - 通过 feign.httpclient.* 属性来调优

- 选择OkHttp作为HTTP调用的Client
    - 配置 feign.okhttp.enabled=true, 同时设置feign.httpclient.enable=false 来禁用 Apache HttpClient
    - classpath 引入 dependency(io.github.openfeign,feign-okhttp,)
    - 可以通过feign.httpclient属性来调优，也可以自定义OkHttpClient设置配置参数；参考FeignAutoConfiguration.OkHttpFeignConfiguration
    
- Feign Client启用客户端负载均衡时，需要注意和Apache HTTP client的冲突问题 => httpclientConnectionManager Bean实例化获取异常问题：因为默认的
负载均衡是Ribbon通过Apache HttpClient 实现的，加载了**HttpClientRibbonConfiguration**
    - ribbon.http.client.enabled=false
    - ribbon.okhttp.enabled=true
    - spring.cloud.loadbalancer.ribbon.enabled=false
    
- **`@FeignClient`** 注解 url/name/path 
    - name()：必须有值，且唯一
    - 解析成调用url时：`url`有值则调用的最终`URL=url+path`，否则最终`URL=name+path`
    - 明确指定了url时，此时Feign在请求时并不会进行负载均衡处理 => `FeignClientFactoryBean.getTarget()` 的逻辑
    - 鉴于name唯一性和负载均衡指定url问题解决办法：`name={微服务名称}/{拦截path}`