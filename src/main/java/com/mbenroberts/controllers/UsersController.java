package com.mbenroberts.controllers;

import com.mbenroberts.interfaces.Posts;
import com.mbenroberts.models.Post;
import com.mbenroberts.models.User;
import com.mbenroberts.interfaces.Users;
import com.mbenroberts.utilities.NoProfileImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/users")
public class UsersController extends BaseController {

    @Autowired
    private Users usersDao;

    @Autowired
    private Posts postsDao;

    @Autowired
    private PasswordEncoder encoder;

    @Value("${file-upload-path}")
    private String uploadPath;

    @GetMapping
    @SuppressWarnings("unchecked")
    public String index(Model model,
                        RedirectAttributes ra) {

        if(!isLoggedIn()){
            ra.addFlashAttribute("flash", "You need to be logged in to view this page");
            return "redirect:/posts";
        }

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
                       Model model,
                       RedirectAttributes ra,
                       @RequestParam(name="image") MultipartFile uploadedFile){

        if (errors.hasErrors()){
            model.addAttribute("errors", errors);
            model.addAttribute("user", user);
            model.addAttribute("loggedInUser", loggedInUser());
            model.addAttribute("isLoggedIn", isLoggedIn());
            return "users/register";
        }

        if(!uploadedFile.isEmpty()) {

            String fileName = uploadedFile.getOriginalFilename();
            String filePath = Paths.get(uploadPath, fileName).toString();
            File destinationFile = new File(filePath);

            try {

                uploadedFile.transferTo(destinationFile);
                ra.addFlashAttribute("flash", "File successfully uploaded!");

            } catch (IOException e) {

                e.printStackTrace();
                ra.addFlashAttribute("flash", "Oops! Something went wrong! " + e);

            }

            user.setProfileImage(fileName);
        } else {
            user.setProfileImage("profile/" + NoProfileImage.getRandomPlaceholder());
        }

        user.setPassword(encoder.encode(user.getPassword()));
        ra.addFlashAttribute("flash", "User Created Successfully");

        usersDao.save(user);

        return "redirect:/users";
    }

    @GetMapping("/show/{id}")
    public String showUser(@PathVariable Long id,
                           Model model,
                           RedirectAttributes ra){

        if(!isLoggedIn()){
            ra.addFlashAttribute("flash", "You need to be logged in first");
            return "redirect:/posts";
        }

        User loggedInUser = loggedInUser();
        boolean belongsToUser = false;

        if(isLoggedIn()){
            belongsToUser = id.equals(loggedInUser.getId());
        }

        List<Post> posts = postsDao.findByUserId(id);

        Collections.reverse(posts);

        model.addAttribute("user", usersDao.findOne(id));
        model.addAttribute("posts", posts);
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
    public String editUser(Model model,
                           RedirectAttributes ra,
                           @RequestParam(name = "username") String username,
                           @RequestParam(name = "email") String email,
                           @RequestParam(name = "image") MultipartFile uploadedFile,
                           @PathVariable Long id){

        User loggedInUser = loggedInUser();
        User user = usersDao.findOne(id);
        boolean usernameIsEmpty = username.isEmpty();
        boolean emailIsEmpty = email.isEmpty();

        if(usernameIsEmpty || emailIsEmpty) {

            ra.addFlashAttribute("flash", "Please fill out both Username and Email");
            model.addAttribute("user", loggedInUser);
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("isLoggedIn", isLoggedIn());

            return "users/edit";
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
            user.setProfileImage(fileName);
        } else {

            user.setProfileImage(user.getProfileImage());

        }
        user.setUsername(username);
        user.setEmail(email);

        usersDao.save(user);
        ra.addFlashAttribute("flash", "User Edited Successfully");


        return "redirect:/users";
    }
}
