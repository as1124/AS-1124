package com.as1124.spring5.intergration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig2 {

//    @Bean
    public IntegrationFlow crateFileWriterFlow() {
        return IntegrationFlows.from(MessageChannels.direct("contentInChannel"))
                .<String, String>transform(source -> source.toUpperCase())
                .handle(Files.outboundAdapter(new File(""))
                        .fileExistsMode(FileExistsMode.APPEND).appendNewLine(true))
                .get();
    }

}
