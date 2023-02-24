package com.example.demo.model;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class HomeController {

    private final MyDao dao;

    {
        try {
            dao = new MyDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/test")
    public String home(Model model) {

        ArrayList<fruit> data = new ArrayList<>();
        try
        {
            ResultSet rs = dao.selectData("* from fruits");
            while (rs.next())
            {
                fruit afruit = new fruit();
                data.add(afruit);
                afruit.setName(rs.getString("name"));
                afruit.setCalories(rs.getInt("calories"));
                afruit.setSugar(rs.getInt("shugar"));
            }
        }
        catch (SQLException e)
        {
            return "error";
        }
        model.addAttribute("data", data);
        return "home";
    }
}
