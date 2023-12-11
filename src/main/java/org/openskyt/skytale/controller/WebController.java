package org.openskyt.skytale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @GetMapping("/")
    public String homePage() {
        return "chat";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/fail")
    @ResponseBody
    public String fail() {
        return "fail";
    }
}
