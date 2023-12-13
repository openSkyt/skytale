package org.openskyt.skytale.controller;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.dto.MessageDto;
import org.openskyt.skytale.dto.MessageRequestDto;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.security.SecurityService;
import org.openskyt.skytale.service.ChatroomService;
import org.openskyt.skytale.service.MessageService;
import org.openskyt.skytale.service.SseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class ChatController {

    private SecurityService securityService;
    private MessageService messageService;
    private SseService sseService;

    @PostMapping("/{idOfChatroom}/addMessage")
    public String addMessage(String message, @PathVariable Long idOfChatroom) {
        User user = securityService.getLoggedInUser();
        messageService.createMessage(new MessageRequestDto(user.getId(), idOfChatroom, message));
        sseService.sendMessageEvent(new MessageDto(user.getName(), message));
        return "redirect:/{idOfChatroom}";
    }
}
