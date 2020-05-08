package com.as1124.spring5.intergration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {

    @Bean
    @Transformer(inputChannel = "contextInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> uppercaseTransformer() {
        return (source -> source.toUpperCase());
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/Users/huang/work/mq/"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);

        return handler;
    }

    @Bean("contextInChannel")
    public MessageChannel contentInChannel() {
        // 通道可以不用显示声明, 没有会自动创建
        return new DirectChannel();
    }

    @Bean("fileWriterChannel")
    public MessageChannel writeOutChannel() {
        // 通道可以不用显示声明
        return new DirectChannel();
    }
}
