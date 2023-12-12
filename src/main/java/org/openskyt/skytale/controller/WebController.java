package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.models.Message;
import org.openskyt.skytale.repositories.ChatroomRepository;
import org.openskyt.skytale.repositories.MessageRepository;
import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class WebController {
    private ChatroomRepository chatroomRepository;
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    @GetMapping("/")
    public String homePage(Model model) {
        Optional<Chatroom> chatroomOpt = chatroomRepository.findByName("xChat");
        Chatroom chatroom = chatroomOpt.orElseThrow();

        List<Message> messages = chatroom.getMessagesInRoom();
        model.addAttribute("messages", messages);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        model.addAttribute("loggedUserName", currentPrincipalName);

        return "chat";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
