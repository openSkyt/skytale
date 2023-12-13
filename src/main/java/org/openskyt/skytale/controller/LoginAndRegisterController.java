package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class LoginAndRegisterController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(String username, String password){
        // TODO change to service: userRepository.save(new User(username, passwordEncoder.encode(password)));
        return "redirect:/login";
    }
}
