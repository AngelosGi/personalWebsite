package io.anggi.personalwebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

    @GetMapping({"/", "/resume", "/resume/"})
    public String forwardToResume() {
        return "forward:/resume/1";
    }

}
