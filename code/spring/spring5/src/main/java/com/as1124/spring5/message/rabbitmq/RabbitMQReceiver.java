package com.as1124.spring5.message.rabbitmq;

import com.as1124.spring5.message.AsyncMessageConstants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Profile("rabbit")
@Component
public class RabbitMQReceiver {

    private RabbitTemplate rabbitTemplate;

    private MessageConverter converter;

    @Autowired
    public RabbitMQReceiver(RabbitTemplate template) {
        this.rabbitTemplate = template;
        this.converter = template.getMessageConverter();
    }

    public Message receiveMessage() {
        return rabbitTemplate.receive(AsyncMessageConstants.QUEUE_NAME);
    }

    public Object receiveAndConvertMessage() {
        return rabbitTemplate.receiveAndConvert(AsyncMessageConstants.QUEUE_NAME);
    }

    public <T> T receiveAndConvertMessage(ParameterizedTypeReference<T> typeReference) {
        T result = rabbitTemplate.receiveAndConvert(AsyncMessageConstants.QUEUE_NAME, typeReference);
        return result != null ? result : null;
    }

    @RabbitListener(queues = AsyncMessageConstants.QUEUE_NAME)
    public void listenAndReceiveMessage(Message message) {
        Object data = converter.fromMessage(message);
        System.out.println(data);
    }
}
