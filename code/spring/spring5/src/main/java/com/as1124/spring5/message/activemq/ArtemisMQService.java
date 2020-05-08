package com.as1124.spring5.message.activemq;

import com.as1124.spring5.message.AsyncMessageConstants;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Profile("activemq")
@Service
public class ArtemisMQService {

    private final JmsTemplate jmsTemplate;

    private final JmsMessagingTemplate jmsMessageTemplate;

    private volatile int STATUS = -1;

    public ArtemisMQService(JmsTemplate template1, JmsMessagingTemplate template2) throws JMSException {
        this.jmsTemplate = template1;
        this.jmsMessageTemplate = template2;
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        this.jmsTemplate.setMessageConverter(converter);
        if (STATUS == -1) {
            STATUS = 1;
        }
        if (this.jmsTemplate.getDefaultDestination() == null) {
            init();
        }

//        new Thread("Artemis-MQ-Receiver") {
//
//            @Override
//            public synchronized void start() {
//                super.start();
//                receiveMessage();
//            }
//        }.start();
    }

    private void init() throws JMSException {
        try (
                Connection connection = this.jmsTemplate.getConnectionFactory().createConnection();
                Session session = connection.createSession()
        ) {
            connection.start();
            Queue queue = session.createQueue(AsyncMessageConstants.QUEUE_NAME);
            session.createTopic(AsyncMessageConstants.TOPIC_NAME);
            this.jmsTemplate.setDefaultDestination(queue);
        }
    }

    public void sendMessage() {
        jmsTemplate.send(session -> {
            MapMessage msg = session.createMapMessage();
            msg.setString("method", "MessageCreator");
            msg.setStringProperty("name", "Artemis Send");
            return msg;
        });
    }

    public void receiveMessage() {
        while (STATUS == 1) {
            Message msg = jmsTemplate.receive();
            if (msg != null) {
                try {
                    System.out.println(msg.getClass().getName() + ": " + msg.getStringProperty("name"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopMQ() {
        STATUS = 0;
    }

}
