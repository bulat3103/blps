package com.example.mailservice.services;

import com.example.mailservice.exeptions.InvalidDataException;
import com.example.mailservice.model.Mail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

@Service
public class MessageListenerService implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageListenerService.class);
    @Autowired
    private MailService mailService;
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String json = textMessage.getText();
            logger.info("Get message: " + json);
            Mail mail = objectMapper.readValue(json, Mail.class);
            mailService.sendMail(mail);
        } catch (JMSException e) {
            logger.error("Error getting message from broker");
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (InvalidDataException e) {
            logger.error("Invalid mail data");
        }
    }
}
