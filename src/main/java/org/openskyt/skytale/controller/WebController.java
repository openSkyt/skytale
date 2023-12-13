package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.dto.ErrorDTO;
import org.openskyt.skytale.dto.MessageDto;
import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.models.Message;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.repositories.ChatroomRepository;
import org.openskyt.skytale.repositories.MessageRepository;
import org.openskyt.skytale.repositories.UserRepository;
import org.openskyt.skytale.service.SseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class WebController {

    private ChatroomRepository chatroomRepository;
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private SseService sseService;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String homePage(Model model) {
        Optional<Chatroom> chatroomOpt = chatroomRepository.findByName("xChat");
        Chatroom chatroom = chatroomOpt.orElseThrow();

        List<Message> messages = chatroom.getMessagesInRoom();
        model.addAttribute("messages", messages.reversed());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        model.addAttribute("loggedUserName", currentPrincipalName);

        return "chat";
    }

    @GetMapping("/login")
    public String login(Model m, @RequestParam() Map<String, String> param) {
        m.addAttribute("param", param);
        if (param.containsKey("error")){
            m.addAttribute("error", "Error: Invalid credentials.");
        }
        return "login";
    }

    @PostMapping("/addMessage")
    public String addMessage(String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Message m = new Message(message);
        m.setChatroom(chatroomRepository.findByName("xChat").orElseThrow());
        m.setAuthor(userRepository.findByName(currentPrincipalName).orElseThrow());

        messageRepository.save(m);
        sseService.sendMessageEvent(new MessageDto(m.getAuthor().getName(), m.getText()));

        return "redirect:/";
    }

    @GetMapping("/register")
    public String newUser() {
        return "register";
    }

    @PostMapping("/register")
    public String newUserPOST(Model m, Optional<String> username, Optional<String> password) {
        List<ErrorDTO> errorDTOs = Stream.of(
                        username.filter(u -> userRepository.findByName(u)
                                        .map(User::getName)
                                        .orElse("")
                                        .contains(u))
                                .map(u -> new ErrorDTO("Error: Username already in use")),
                        username.filter(String::isEmpty)
                                .map(u -> new ErrorDTO("Error: Missing username")),
                        password.filter(String::isEmpty)
                                .map(p -> new ErrorDTO("Error: Missing password"))
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();



        List<String> errors = new ArrayList<>();
        for (ErrorDTO ed : errorDTOs) {
            errors.add(ed.error());
        }


        if (!errors.isEmpty()) {
            m.addAttribute("errors", errors);
            return "register";
        }

        userRepository.save(new User(username.get(), passwordEncoder.encode(password.get())));
        return "redirect:/login";
    }
}
