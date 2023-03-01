package com.example.demo.model.JMS;

import com.example.demo.model.FruitsHibernateEntity;
import com.example.demo.model.MySessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MySessionFactory mySessionFactory;
    @JmsListener(destination = "myQueue")
    public void receiveMessage(LogsHibernateEntity logsHibernateEntity) {

        Session session = mySessionFactory.sessionFactory.openSession();

        Query query = session.createQuery("FROM EmailHibernateEntity WHERE  typeOfOperation = :optype");
        query.setParameter("optype", logsHibernateEntity.getChangeType());
        EmailHibernateEntity emailHibernateEntity = (EmailHibernateEntity) query.uniqueResult();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("ermk.egor@yandex.ru");
        email.setTo(emailHibernateEntity.getEmail());
        email.setSubject("sample");
        email.setText(logsHibernateEntity.getChangeType() + " " + logsHibernateEntity.getChangedEntity() +" "+ logsHibernateEntity.getChangedValues() + " " + logsHibernateEntity.getDateOfChange().toString());

        mailSender.send(email);
    }
}