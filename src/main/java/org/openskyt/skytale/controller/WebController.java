package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
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
import java.util.List;
import java.util.Optional;

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
    public String login() {
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
    public String newUser(){
        return "register";
    }

    @PostMapping("/register")
    public String newUserPOST(String username, String password){
        userRepository.save(new User(username, passwordEncoder.encode(password)));
        return "redirect:/login";
    }

}
