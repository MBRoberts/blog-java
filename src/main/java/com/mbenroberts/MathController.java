package com.mbenroberts;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {

    @GetMapping("/add/{x}/and/{y}")
    @ResponseBody
    public String add(@PathVariable int x, @PathVariable int y){
        return "<h1>" + x + " + " + y + " = " + (x + y) + "</h1>";
    }

    @GetMapping("/subtract/{x}/from/{y}")
    @ResponseBody
    public String subtract(@PathVariable int x, @PathVariable int y) {
        return "<h1>" + x + " - " + y + " = " + (x - y) + "</h1>";
    }

    @GetMapping("/multiply/{x}/and/{y}")
    @ResponseBody
    public String multiply(@PathVariable int x, @PathVariable int y){
        return "<h1>" + x + " * " + y + " = " + (x * y) + "</h1>";
    }

    @GetMapping("/divide/{x}/by/{y}")
    @ResponseBody
    public String divide(@PathVariable int x, @PathVariable int y){
        return  "<h1>" + x + " / " + y + " = " + (x / y) + "</h1>";
    }
}
