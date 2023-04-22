package com.example.blps.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.TextMessage;

@Service
public class MessageSenderService {
    private final ConnectionFactory connectionFactory;

    public MessageSenderService(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void sendMessageToBroker(Object objectToSend) throws Exception {
        Connection clientConnection = connectionFactory.createConnection();
        clientConnection.start();
        String message = toJson(objectToSend);
        JmsTemplate tpl = new JmsTemplate(connectionFactory);
        tpl.send("test-create-queue", session -> {
            TextMessage msg = session.createTextMessage(message);
            msg.setJMSCorrelationID(message);
            return msg;
        });
    }

    private String toJson(Object object) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}
