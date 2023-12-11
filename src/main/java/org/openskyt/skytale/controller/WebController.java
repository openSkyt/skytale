package org.openskyt.skytale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @GetMapping("/")
    @ResponseBody
    public String homePage() {
        return "homePage";
    }
}
