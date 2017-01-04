package com.mbenroberts;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/hello/{name}")
    @ResponseBody
    public String hello(@PathVariable String name) {
        return "Hello, " + name + "! ...from Spring!";
    }

    @GetMapping("/increment/{number}")
    @ResponseBody
    public String increment(@PathVariable int number) {
        return number + " plus one is " + (number + 1);
    }
}
