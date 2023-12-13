package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.security.SecurityService;
import org.openskyt.skytale.service.ChatroomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class WebController {

    private SecurityService securityService;
    private ChatroomService chatroomService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("loggedInUser", securityService.getLoggedInUser());
        model.addAttribute("chatrooms", chatroomService.getAll());
        return "index";
    }

    @GetMapping("/{idOfChatroom}")
    public String chatroom(@PathVariable Long idOfChatroom, Model model) {
        Chatroom chatroom = chatroomService.getById(idOfChatroom);
        model.addAttribute("chatroom", chatroom);
        model.addAttribute("messages", chatroom.getMessagesInRoom().reversed());
        model.addAttribute("loggedUserName", securityService.getLoggedInUser().getName());

        return "chat";
    }
}
