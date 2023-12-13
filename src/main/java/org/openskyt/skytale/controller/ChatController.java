package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.security.SecurityService;
import org.openskyt.skytale.service.ChatroomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class ChatController {

    private ChatroomService chatroomService;
    private SecurityService securityService;

    @PostMapping("/{idOfChatroom}/addMessage")
    public String addMessage(String message, @PathVariable Long idOfChatroom) {
        User user = securityService.getLoggedInUser();


        // TODO save new message with inputs (idOfChatroom, idOfUser, messageText)
        //Message m = new Message(message);
        //m.setChatroom(chatroomRepository.findByName("xChat").orElseThrow());
        //m.setUser(userRepository.findByName(currentPrincipalName).orElseThrow());
        //messageRepository.save(m);

        return "redirect:/";
    }
}
