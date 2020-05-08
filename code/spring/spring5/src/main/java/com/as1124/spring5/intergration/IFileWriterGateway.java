package com.as1124.spring5.intergration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultReplyChannel = "contextInChannel")
public interface IFileWriterGateway {

    void write2File(@Header(FileHeaders.FILENAME) String fileName, String context);
}
