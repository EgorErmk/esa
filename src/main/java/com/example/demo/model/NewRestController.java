package com.example.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;


@RestController
public class NewRestController {
    @Autowired
    private MySessionFactory mySessionFactory;
    @GetMapping("/{name}")
    public String elementView(@PathVariable String name) throws JsonProcessingException {
        Session session = mySessionFactory.sessionFactory.openSession();

        Query query = session.createQuery("FROM FruitsHibernateEntity WHERE name = :name");
        query.setParameter("name", name);
        FruitsHibernateEntity fruitsHibernateEntity = (FruitsHibernateEntity) query.uniqueResult();

        ObjectMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(fruitsHibernateEntity);

        Source xmlSource = new StreamSource(new ByteArrayInputStream(xml.getBytes()));
        Source xsltSource = new StreamSource(new File("src/main/resources/templates/fruit.xsl"));

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        String html;
        try {
            transformer = factory.newTransformer(xsltSource);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        try {
            StringWriter writer = new StringWriter();
            transformer.transform(xmlSource, new StreamResult(writer));
            html = writer.toString();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("xmlSource", source);

        return html;

    }
}
