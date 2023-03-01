package com.example.demo.model;


import com.example.demo.model.JMS.LogsHibernateEntity;
import com.example.demo.model.JMS.MessageProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.List;

@Controller
public class HibernateController {

    @Autowired
    private MySessionFactory mySessionFactory;
    @Autowired
    private MessageProducer messageProducer;
    @GetMapping("/")
    public String home(Model model) {
        Session session = mySessionFactory.sessionFactory.openSession();
        String hql = "from FruitsHibernateEntity";
        List<FruitsHibernateEntity> data = session.createQuery(hql, FruitsHibernateEntity.class).getResultList();
        session.close();
        model.addAttribute("data", data);
        return "home";
    }
    @PostMapping("/")
    public String buttons(@RequestParam("button") String button)
    {
        if ("add".equals(button))
        {
            return "redirect:/addfruit";
        }
        else if ("remove".equals(button))
        {
            return "redirect:/removefruit";
        }
        return "home";
    }
    @GetMapping("/addfruit")
    public String addFruitPage(Model model)
    {
        model.addAttribute("fruitsHibernateEntity",new FruitsHibernateEntity());
        return "addfruit";
    }
    @PostMapping("/addfruit")
    public String addFruit(@ModelAttribute FruitsHibernateEntity fruitsHibernateEntity, Model model) throws JsonProcessingException {

        Session session = mySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(fruitsHibernateEntity);

        LogsHibernateEntity logsHibernateEntity = new LogsHibernateEntity();
        logsHibernateEntity.setChangedEntity("FruitsHibernateEntity");
        logsHibernateEntity.setChangeType("insert");
        ObjectMapper objectMapper = new ObjectMapper();
        logsHibernateEntity.setChangedValues(objectMapper.writeValueAsString(fruitsHibernateEntity));
        logsHibernateEntity.setDateOfChange(new Date(java.time.Clock.systemUTC().millis()));

        session.save(logsHibernateEntity);
        transaction.commit();

        messageProducer.sendMessage(logsHibernateEntity);
        session.close();
        return "redirect:/";
    }
    @GetMapping("/removefruit")
    public String removeFruitPage(Model model)
    {
        return "removefruit";
    }
    @PostMapping("/removefruit")
    public String removeFruit(@RequestParam("fruitName") String name) throws JsonProcessingException {
        Session session = mySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM FruitsHibernateEntity WHERE name = :name");
        query.setParameter("name", name);
        FruitsHibernateEntity fruitsHibernateEntity = (FruitsHibernateEntity) query.uniqueResult();

        session.delete(fruitsHibernateEntity);

        LogsHibernateEntity logsHibernateEntity = new LogsHibernateEntity();
        logsHibernateEntity.setChangedEntity("FruitsHibernateEntity");
        logsHibernateEntity.setChangeType("deleted");
        ObjectMapper objectMapper = new ObjectMapper();
        logsHibernateEntity.setChangedValues(objectMapper.writeValueAsString(fruitsHibernateEntity));
        logsHibernateEntity.setDateOfChange(new Date(java.time.Clock.systemUTC().millis()));

        session.save(logsHibernateEntity);
        transaction.commit();

        messageProducer.sendMessage(logsHibernateEntity);
        session.close();
        return "redirect:/";
    }
}
