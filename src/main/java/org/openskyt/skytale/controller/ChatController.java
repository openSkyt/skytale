package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.service.ChatroomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class ChatController {

    ChatroomService chatroomService;
    @PostMapping("/{idOfChatroom}/addMessage")
    public String addMessage(String message, @PathVariable Long idOfChatroom) {
        // TODO get id of loggedInUser from service
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String currentPrincipalName = authentication.getName();

        // TODO save new message with inputs (idOfChatroom, idOfUser, messageText)
        //Message m = new Message(message);
        //m.setChatroom(chatroomRepository.findByName("xChat").orElseThrow());
        //m.setUser(userRepository.findByName(currentPrincipalName).orElseThrow());
        //messageRepository.save(m);

        return "redirect:/";
    }
}
