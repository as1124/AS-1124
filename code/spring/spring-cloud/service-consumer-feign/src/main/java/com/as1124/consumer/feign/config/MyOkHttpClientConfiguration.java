package com.as1124.consumer.feign.config;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.commons.httpclient.DefaultOkHttpClientFactory;
import org.springframework.cloud.commons.httpclient.HttpClientConfiguration;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 配置自定义优化的 OkHttpClient
 *
 * @author As-1124
 * @since 2020/8/28 14:54
 */
@Configuration
@ConditionalOnClass(name = "okhttp3.OkHttpClient")
@AutoConfigureBefore({FeignAutoConfiguration.class, HttpClientConfiguration.class})
public class MyOkHttpClientConfiguration {

    static Logger logger = LoggerFactory.getLogger(MyOkHttpClientConfiguration.class);

    /**
     * 可以参考{@link HttpClientConfiguration} 中的<code>OkHttpClientConfiguration</code>
     * 中关于一些 OkHttpClient 属性的配置
     *
     * @return OkHttpClientFactory
     */
    @Bean
    public OkHttpClientFactory myOkHttpClientFactory() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .hostnameVerifier((url, sslSession) -> {
                    if (!sslSession.isValid()) {
                        logger.warn("[OkHttpClient] Certificate of this site is invalid => " + url);
                    }
                    return true;
                });
//                .sslSocketFactory();
        return new DefaultOkHttpClientFactory(builder);
    }
}
