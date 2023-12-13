package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.dto.ErrorDto;
import org.openskyt.skytale.dto.RegistrationRequestDto;
import org.openskyt.skytale.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@AllArgsConstructor
public class LoginAndRegisterController {

    private UserService userService;

    @GetMapping("/login")
    public String login(Model m, @RequestParam() Map<String, String> param) {
        m.addAttribute("param", param);
        if (param.containsKey("error")) {
            m.addAttribute("error", "Error: Invalid credentials.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(Model m, Optional<String> username, Optional<String> password) {
        List<ErrorDto> errorDTOs = Stream.of(
                        username.filter(u -> userService.getByUsername(u).getName()
                                        .contains(u))
                                .map(u -> new ErrorDto("Error: Username already in use")),
                        username.filter(String::isEmpty)
                                .map(u -> new ErrorDto("Error: Missing username")),
                        password.filter(String::isEmpty)
                                .map(p -> new ErrorDto("Error: Missing password"))
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        List<String> errors = new ArrayList<>();
        for (ErrorDto ed : errorDTOs) {
            errors.add(ed.error());
        }
        if (!errors.isEmpty()) {
            m.addAttribute("errors", errors);
            return "register";
        }
        // TODO refactor this method!
        userService.createUser(new RegistrationRequestDto(username.get(), password.get()));
        return "redirect:/login";
    }
}
