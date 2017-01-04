package com.mbenroberts;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    public String rollDice(Model model){

        model.addAttribute("message", "Choose a number");
        return "roll-dice";

    }

    @GetMapping("/roll-dice/{number}")
    public String rollDice(@PathVariable int number, Model model){

        String message = "Wrong, Try Again";


        Random random = new Random();
        int randNum = random.nextInt(6 - 1) + 1;

        model.addAttribute("randNumber", randNum);

        if (number == randNum) {
            message = "You Got it!";
        }



        model.addAttribute("message", message);
        return "roll-dice";

    }

}
