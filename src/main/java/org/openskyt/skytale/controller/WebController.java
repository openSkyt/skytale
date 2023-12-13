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
    public String chatroom(@PathVariable Long idOfChatroom, Model model){
        // TODO find chatroom by ID
        Chatroom chatroom = chatroomService.getById(idOfChatroom);
        model.addAttribute("chatroom", chatroom);
        //Optional<Chatroom> chatroomOpt = chatroomRepository.findByName("xChat");
        //Chatroom chatroom = chatroomOpt.orElseThrow();

        // TODO get messages from chatroom and add them to model
        //List<Message> messages = chatroom.getMessagesInRoom();
        //model.addAttribute("messages", messages.reversed());

        // TODO get name of loggedInUser and add it to model
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String currentPrincipalName = authentication.getName();
        //model.addAttribute("loggedUserName", currentPrincipalName);

        return "chat";
    }
}
