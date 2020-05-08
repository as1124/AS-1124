package com.as1124.spring5.message;

import com.as1124.spring5.data.model.Author;
import com.as1124.spring5.data.model.Book;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import java.util.HashMap;

/**
 * 提供MQ使用的一些通用Bean组件
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
@Configuration
public class MQCommonRepository {

    @Bean
    public static MessageConverter createJMSConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setEncoding("UTF-8");
        converter.setTypeIdPropertyName("_type");
        HashMap<String, Class<?>> mapping = new HashMap<>();
        mapping.putIfAbsent("Author", Author.class);
        mapping.putIfAbsent("Book", Book.class);
        converter.setTypeIdMappings(mapping);
        return converter;
    }

    @Bean
    public static org.springframework.amqp.support.converter.MessageConverter createAMQPConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setDefaultCharset("UTF-8");
        return converter;
    }
}
