package com.example.demo.model;

//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

@Controller
public class HibernateController {

    @GetMapping("/test")
    public String home(Model model) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml"); // or pass other configuration options
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
        String hql = "from fruits";
        List<FruitsHibernateEntity> data = session.createQuery(hql, FruitsHibernateEntity.class).getResultList();
        model.addAttribute("data", data);
        return "home";
    }
}
