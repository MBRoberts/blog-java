package com.mbenroberts;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController extends BaseController{
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loggedInUser", loggedInUser());
        model.addAttribute("isLoggedIn", isLoggedIn());
        return "users/login";
    }
}
