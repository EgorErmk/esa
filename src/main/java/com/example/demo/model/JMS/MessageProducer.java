package com.example.demo.model.JMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

@Service
public class MessageProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    public void sendMessage(LogsHibernateEntity message) {
        jmsTemplate.convertAndSend(queue, message);
    }
}
