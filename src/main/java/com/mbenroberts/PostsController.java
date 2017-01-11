package com.mbenroberts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    public Posts postsDao;

    @GetMapping
    @SuppressWarnings("unchecked")
    public String index(Model model) {

        List posts = new ArrayList((Collection) postsDao.findAll());

        Collections.reverse(posts);

        model.addAttribute("posts", posts);

        return "posts/index";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("post", new Post());

        return "posts/create";
    }

    @PostMapping("/create")
    public String save(@Valid Post post, Errors errors, Model model){

        if (errors.hasErrors()){
            model.addAttribute("errors", errors);
            model.addAttribute("post", post);
            return "posts/create";
        }

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        post.setCreatedBy(loggedInUser);

        postsDao.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/show/{id}")
    public String showPost(@PathVariable Long id, Model model){
        model.addAttribute("post", postsDao.findOne(id));
        return "posts/show";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model){
        model.addAttribute("post", postsDao.findOne(id));
        return "posts/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@Valid Post post, Errors errors, Model model){

        if(errors.hasErrors()){
            model.addAttribute("errors", errors);
            model.addAttribute("post", post);
            return "posts/edit";
        }

        postsDao.save(post);
        return "redirect:/posts";
    }
}
