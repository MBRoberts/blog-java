package com.mbenroberts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    public Users userDao;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "auth/register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid User user, Errors errors, Model model){

        if(errors.hasErrors()){
            model.addAttribute("errors", errors);
            model.addAttribute("user", user);
            return "auth/register";
        }

        userDao.save(user);

        return "redirect:/posts";
    }
}
