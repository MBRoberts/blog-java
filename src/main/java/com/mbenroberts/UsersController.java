package com.mbenroberts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    public Users usersDao;

    @GetMapping
    @SuppressWarnings("unchecked")
    public String index(Model model){

        List users = new ArrayList((Collection) usersDao.findAll());

        model.addAttribute("users", users);

        return "users/index";
    }

    @GetMapping("/show/{id}")
    public String showUser(@PathVariable Long id, Model model){
        model.addAttribute("post", usersDao.findOne(id));
        return "posts/show";
    }
}
