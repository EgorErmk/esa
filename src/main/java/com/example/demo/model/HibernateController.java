package com.example.demo.model;

//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.hibernate.query.Query;

import java.util.List;

@Controller
public class HibernateController {

//    private FruitsHibernateEntity fruitsHibernateEntity;
    @GetMapping("/")
    public String home(Model model) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
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
    public String addFruit(@ModelAttribute FruitsHibernateEntity fruitsHibernateEntity, Model model)
    {
//        model.addAttribute("fruitsHibernateEntity",fruitsHibernateEntity);
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(fruitsHibernateEntity);

        transaction.commit();
        session.close();
        return "redirect:/";
    }
    @GetMapping("/removefruit")
    public String removeFruitPage(Model model)
    {
//        model.addAttribute("fruitsHibernateEntity",new FruitsHibernateEntity());
        return "removefruit";
    }
    @PostMapping("/removefruit")
    public String removeFruit(@RequestParam("fruitName") String name)
    {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM FruitsHibernateEntity WHERE name = :name");
        query.setParameter("name", name);
        FruitsHibernateEntity fruitsHibernateEntity = (FruitsHibernateEntity) query.uniqueResult();
        session.delete(fruitsHibernateEntity);

        transaction.commit();
        session.close();
        return "redirect:/";
    }
}
