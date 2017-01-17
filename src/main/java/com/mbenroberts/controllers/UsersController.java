package com.mbenroberts.controllers;

import com.mbenroberts.models.User;
import com.mbenroberts.interfaces.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController extends BaseController {

    @Autowired
    private Users usersDao;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    @SuppressWarnings("unchecked")
    public String index(Model model) {

        List<User> users = new ArrayList((Collection) usersDao.findAll());

        Collections.reverse(users);

        model.addAttribute("users", users);
        model.addAttribute("loggedInUser", loggedInUser());
        model.addAttribute("isLoggedIn", isLoggedIn());

        return "users/index";
    }

    @GetMapping("/register")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("loggedInUser", loggedInUser());
        model.addAttribute("isLoggedIn", isLoggedIn());
        return "users/register";
    }

    @PostMapping("/register")
    public String save(@Valid User user,
                       Errors errors,
                       Model model, RedirectAttributes ra){

        if (errors.hasErrors()){
            model.addAttribute("errors", errors);
            model.addAttribute("user", user);
            model.addAttribute("loggedInUser", loggedInUser());
            model.addAttribute("isLoggedIn", isLoggedIn());
            return "users/register";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        ra.addFlashAttribute("flash", "User Created Successfully");

        usersDao.save(user);

        return "redirect:/users";
    }

    @GetMapping("/show/{id}")
    public String showUser(@PathVariable Long id,
                           Model model){

        User loggedInUser = loggedInUser();
        boolean belongsToUser = false;

        if(isLoggedIn()){
            belongsToUser = id.equals(loggedInUser.getId());
        }

        model.addAttribute("user", usersDao.findOne(id));
        model.addAttribute("loggedInUser", loggedInUser());
        model.addAttribute("isLoggedIn", isLoggedIn());
        model.addAttribute("belongsToUser", belongsToUser);

        return "users/show";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                           RedirectAttributes ra,
                           Model model){

        User loggedInUser;

        if(isLoggedIn()){
            loggedInUser = loggedInUser();
            User user = usersDao.findOne(id);

            if (user.getId() == loggedInUser.getId()) {

                model.addAttribute("user", user);
                model.addAttribute("loggedInUser", loggedInUser);
                model.addAttribute("isLoggedIn", isLoggedIn());

                return "users/edit";
            } else {
                ra.addFlashAttribute("flash", "You do not have the right permissions");

                return "redirect:/posts";
            }
        } else {
            ra.addFlashAttribute("flash", "Please login first");
            return "redirect:/posts";
        }


    }

    @PostMapping("/edit/{id}")
    public String editUser(@Valid User user,
                           Errors errors,
                           Model model,
                           RedirectAttributes ra){

        User loggedInUser = loggedInUser();

        if(errors.hasErrors()){
            model.addAttribute("errors", errors);
            model.addAttribute("user", user);
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("isLoggedIn", isLoggedIn());
            return "users/edit";
        }


            usersDao.save(user);
            ra.addFlashAttribute("flash", "User Edited Successfully");


        return "redirect:/users";
    }
}
