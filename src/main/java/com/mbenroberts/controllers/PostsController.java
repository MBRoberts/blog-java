package com.mbenroberts.controllers;

import com.mbenroberts.models.Post;
import com.mbenroberts.interfaces.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostsController extends BaseController {

    @Autowired
    private Posts postDao;

    @Value("${file-upload-path}")
    private String uploadPath;

    @GetMapping
    @SuppressWarnings("unchecked")
    public String index(Model model) {

        List<Post> posts = new ArrayList((Collection) postDao.findAll());

        Collections.reverse(posts);

        model.addAttribute("posts", posts);
        model.addAttribute("loggedInUser", loggedInUser());
        model.addAttribute("isLoggedIn", isLoggedIn());

        return "posts/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("loggedInUser", loggedInUser());
        model.addAttribute("isLoggedIn", isLoggedIn());

        return "posts/create";
    }

    @PostMapping("/create")
    public String save(@Valid Post post,
                       Errors errors,
                       Model model,
                       @RequestParam(name="image") MultipartFile uploadedFile,
                       RedirectAttributes ra){

        if (errors.hasErrors()){
            model.addAttribute("errors", errors);
            model.addAttribute("post", post);

            model.addAttribute("loggedInUser", loggedInUser());
            model.addAttribute("isLoggedIn", isLoggedIn());
            return "posts/create";
        }

        if(!uploadedFile.isEmpty()) {

            String fileName = loggedInUser().getId() + uploadedFile.getOriginalFilename();
            String filePath = Paths.get(uploadPath, fileName).toString();
            File destinationFile = new File(filePath);

            try {

                uploadedFile.transferTo(destinationFile);
                ra.addFlashAttribute("flash", "File successfully uploaded!");

            } catch (IOException e) {

                e.printStackTrace();
                ra.addFlashAttribute("flash", "Oops! Something went wrong! " + e);

            }

            post.setImageURL(fileName);
        } else {
            post.setImageURL("no-image.jpg");
        }

        post.setUser(loggedInUser());
        postDao.save(post);

        return "redirect:/posts";
    }

    @GetMapping("/show/{id}")
    public String showPost(@PathVariable Long id, Model model){

        Post post = postDao.findOne(id);

        model.addAttribute("post", post);
        model.addAttribute("loggedInUser", loggedInUser());
        model.addAttribute("isLoggedIn", isLoggedIn());
        model.addAttribute("belongsToUser", postBelongsToUser(post));

        return "posts/show";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model){

        Post post = postDao.findOne(id);
        boolean belongsToUser = postBelongsToUser(post);

        if (belongsToUser) {

            model.addAttribute("post", postDao.findOne(id));
            model.addAttribute("loggedInUser", loggedInUser());
            model.addAttribute("isLoggedIn", isLoggedIn());

            return "posts/edit";
        }

        return "redirect:/posts";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@Valid Post post,
                           @RequestParam(name = "image") MultipartFile uploadedFile,
                           @PathVariable Long id,
                           Errors errors,
                           Model model,
                           RedirectAttributes ra){

        if(errors.hasErrors()){

            model.addAttribute("errors", errors);
            model.addAttribute("post", post);
            model.addAttribute("loggedInUser", loggedInUser());
            model.addAttribute("isLoggedIn", isLoggedIn());

            return "posts/edit";
        }

        if(!uploadedFile.isEmpty()) {
            String fileName = loggedInUser().getId() + uploadedFile.getOriginalFilename();
            String filePath = Paths.get(uploadPath, fileName).toString();
            File destinationFile = new File(filePath);

            try {

                uploadedFile.transferTo(destinationFile);
                ra.addFlashAttribute("flash", "File successfully uploaded!");

            } catch (IOException e) {

                e.printStackTrace();
                ra.addFlashAttribute("flash", "Oops! Something went wrong! " + e);

            }
            post.setImageURL(fileName);
        } else {

            Post existingPost = postDao.findOne(id);
            post.setImageURL(existingPost.getImageURL());

        }

        post.setUser(loggedInUser());

        postDao.save(post);

        return "redirect:/posts";
    }
}
