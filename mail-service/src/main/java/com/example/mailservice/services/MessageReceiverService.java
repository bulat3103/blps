package com.example.mailservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class MessageReceiverService {
    private static final Logger logger = LoggerFactory.getLogger(MessageListenerService.class);
    private static final String queueName = "mail-send-queue";
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private MessageListenerService messageListenerService;

    @Scheduled(fixedDelay = 1000)
    public void checkNewMessages() {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(messageListenerService);
            connection.start();
        } catch (JMSException jmsException) {
            logger.error(jmsException.getMessage());
        }
    }
}
