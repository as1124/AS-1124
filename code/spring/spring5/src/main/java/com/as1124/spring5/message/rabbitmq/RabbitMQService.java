package com.as1124.spring5.message.rabbitmq;

import com.as1124.spring5.message.AsyncMessageConstants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("rabbit")
@Service
public class RabbitMQService {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQService(RabbitTemplate template) {
        this.rabbitTemplate = template;
    }

    public void sendMessage(Object data) {
        MessageConverter converter = this.rabbitTemplate.getMessageConverter();
        MessageProperties properties = new MessageProperties();
        Message message = converter.toMessage(data, properties);
        rabbitTemplate.send(AsyncMessageConstants.ROUTING_KEY, message);
    }
}
