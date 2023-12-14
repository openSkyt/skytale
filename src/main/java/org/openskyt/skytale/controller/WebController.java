package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.security.SecurityService;
import org.openskyt.skytale.service.ChatroomService;
import org.openskyt.skytale.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@AllArgsConstructor
public class WebController {

    private SecurityService securityService;
    private ChatroomService chatroomService;
    private UserService userService;

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

    @GetMapping("/newgroupchat")
    public String newGroupChatGET(Model m) {
        m.addAttribute("users", userService.getAll());
        return "newGroupChat";
    }

    @PostMapping("/newgroupchat")
    public String newGroupChatPOST(String groupName, Long[] groupUsers) {
        Long id = chatroomService.createRoom(securityService.getLoggedInUser().getId(), groupName).getId();
        chatroomService.addParticipant(id, securityService.getLoggedInUser().getId()); //adds the owner as a participant

        for (Long i:groupUsers){
            chatroomService.addParticipant(id, i);
        }

        return "redirect:/"+id;
    }

}
