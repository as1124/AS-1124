package com.as1124.spring5.message.kafka;

import com.as1124.spring5.data.model.Book;
import com.as1124.spring5.message.AsyncMessageConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Profile("kafka")
@Component
public class KafkaMQReceiver {

    @KafkaListener(topics = AsyncMessageConstants.TOPIC_NAME)
    public void receiveBookMessage(Book book) {
        System.out.println(book);
    }

    @KafkaListener(topics = AsyncMessageConstants.TOPIC_NAME)
    public void receiveMessage(ConsumerRecord<String, Book> metadataMessage) {
        System.out.println(metadataMessage.key() + " = " + metadataMessage.value());
    }
}
