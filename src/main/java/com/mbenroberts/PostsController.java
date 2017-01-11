package com.mbenroberts;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostsController {

    @GetMapping("/{page}")
    @SuppressWarnings("unchecked")
    public String index(@PathVariable int page, Model model) {

        List<Post> posts = new ArrayList<>(DaoFactory.getPostsDao().all(page));

        Collections.reverse(posts);

        Long count = DaoFactory.getPostsDao().getPostsCount();
        Long pages = (count % 2 == 0) ? count / 2 : (count / 2) + 1;

        model.addAttribute("pages", pages);
        model.addAttribute("posts", posts);
        model.addAttribute("pageNum", page);
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

        DaoFactory.getPostsDao().save(post);
        return "redirect:/posts/1";
    }

    @GetMapping("/show/{id}")
    public String showPost(@PathVariable Long id, Model model){
        model.addAttribute("post", DaoFactory.getPostsDao().getPostById(id));
        return "posts/show";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model){
        model.addAttribute("post", DaoFactory.getPostsDao().getPostById(id));
        return "posts/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@Valid Post post, Errors errors, Model model){

        if(errors.hasErrors()){
            model.addAttribute("errors", errors);
            model.addAttribute("post", post);
            return "posts/edit";
        }

        DaoFactory.getPostsDao().update(post);
        return "redirect:/posts/1";
    }
}
