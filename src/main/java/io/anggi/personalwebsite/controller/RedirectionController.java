package io.anggi.personalwebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectionController {

    @GetMapping("/")
    public String redirectToResume() {
        return "redirect:/resume/1";
    }
}
