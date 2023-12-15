package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.models.Message;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.security.SecurityService;
import org.openskyt.skytale.service.ChatroomService;
import org.openskyt.skytale.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@AllArgsConstructor
public class WebController {

    private SecurityService securityService;
    private ChatroomService chatroomService;
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        try {
            User loggedInUser = securityService.getLoggedInUser();
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("chatrooms", chatroomService.getByParticipantId(loggedInUser.getId()));
        } catch (NoSuchElementException e) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/{idOfChatroom}")
    public String chatroom(@PathVariable Long idOfChatroom, Model model) {
        Chatroom chatroom = chatroomService.getById(idOfChatroom);
        List<Message> messages = chatroom.getMessagesInRoom();
        Collections.reverse(chatroom.getMessagesInRoom());
        model.addAttribute("chatroom", chatroom);
        model.addAttribute("messages", messages);
        model.addAttribute("loggedUserName", securityService.getLoggedInUser().getName());

        return "chat";
    }

    @GetMapping("/joinchat")
    public String joinChatGET(Model m) {
        User loggedInUser = securityService.getLoggedInUser();
        List<Chatroom> AllChatrooms = new ArrayList<>(chatroomService.getAll());
        List<Chatroom> AllChatroomsPerUser = new ArrayList<>(chatroomService.getByParticipantId(loggedInUser.getId()));
        AllChatrooms.removeAll(AllChatroomsPerUser);
        //TODO JPA version

        m.addAttribute("chatrooms", AllChatrooms);
        return "joinGroupChat";
    }

    @PostMapping("/joinchat")
    public String joinChatPOST(Long[] chatroomID) {
        User loggedInUser = securityService.getLoggedInUser();

        for (Long i : chatroomID) {
            chatroomService.addParticipant(i, loggedInUser.getId());
        }

        return "redirect:/" + chatroomID[0];
    }

    @GetMapping("/newgroupchat")
    public String newGroupChatGET(Model m) {
        List<User> users = new ArrayList<>(userService.getAll());
        users.remove(securityService.getLoggedInUser());
        //above - removing currently logged user, so they don't add themselves again into the room
        //TODO - optimize and handle this inside the service pls, thx
        m.addAttribute("users", users);
        return "newGroupChat";
    }

    @PostMapping("/newgroupchat")
    public String newGroupChatPOST(String groupName, Optional<Long[]> groupUsers) {
        Long id = chatroomService.createRoom(securityService.getLoggedInUser().getId(), groupName).getId();
        chatroomService.addParticipant(id, securityService.getLoggedInUser().getId()); //adds the owner as a participant

        if (groupUsers.isPresent()) {
            for (Long i : groupUsers.get()) {
                chatroomService.addParticipant(id, i);
            }
        }

        return "redirect:/" + id;
    }


}
