package com.as1124.spring5.message;

import com.as1124.spring5.message.activemq.ArtemisMQService;
import com.as1124.spring5.message.kafka.KafkaMQService;
import com.as1124.spring5.message.rabbitmq.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/message")
public class JavaMessageServiceController {

    @Autowired(required = false)
    private ArtemisMQService artemisService;

    @Autowired(required = false)
    private RabbitMQService rabbitService;

    @Autowired(required = false)
    private KafkaMQService kafkaService;

    @GetMapping("/artemis/send")
    public ResponseEntity artemisSend() {
        artemisService.sendMessage();
        return ResponseEntity.ok().build();
    }

}
