package com.as1124.spring5.message.kafka;

import com.as1124.spring5.data.model.Book;
import com.as1124.spring5.message.AsyncMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Profile("kafka")
@Service
public class KafkaMQService {

    private KafkaTemplate<String, Book> kafkaTemplate;

    @Autowired
    public KafkaMQService(KafkaTemplate<String, Book> template) {
        this.kafkaTemplate = template;
    }

    public void sendMessage(Book data) {
        kafkaTemplate.send(AsyncMessageConstants.TOPIC_NAME, data);
    }
}
